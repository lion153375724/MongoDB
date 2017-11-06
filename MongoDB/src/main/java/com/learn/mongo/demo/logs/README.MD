使用 MongoDB 存储日志数据

线上运行的服务会产生大量的运行及访问日志，日志里会包含一些错误、警告、及用户行为等信息，通常服务会以文本的形式记录日志信息，这样可读性强，方便于日常定位问题，但当产生大量的日志之后，要想从大量日志里挖掘出有价值的内容，则需要对数据进行进一步的存储和分析。

本文以存储 web 服务的访问日志为例，介绍如何使用 MongoDB 来存储、分析日志数据，让日志数据发挥最大的价值，本文的内容同样使用其他的日志存储型应用。

模式设计
一个典型的web服务器的访问日志类似如下，包含访问来源、用户、访问的资源地址、访问结果、用户使用的系统及浏览器类型等

127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326 "[http://www.example.com/start.html](http://www.example.com/start.html)" "Mozilla/4.08 [en] (Win98; I ;Nav)"
最简单存储这些日志的方法是，将每行日志存储在一个单独的文档里，每行日志在MongoDB里类似

{
    _id: ObjectId('4f442120eb03305789000000'),
    line: '127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326 "[http://www.example.com/start.html](http://www.example.com/start.html)" "Mozilla/4.08 [en] (Win98; I ;Nav)"'
}
上述模式虽然能解决日志存储的问题，但使得这些数据分析起来比较麻烦，因为文本分析并不是MongoDB所擅长的，更好的办法时，在把一行日志存储到MongoDB的文档里前，先提取出各个字段的值，如下所示，上述的日志被转换为一个包含很多个字段的文档。

{
     _id: ObjectId('4f442120eb03305789000000'),
     host: "127.0.0.1",
     logname: null,
     user: 'frank',
     time: ISODate("2000-10-10T20:55:36Z"),
     path: "/apache_pb.gif",
     request: "GET /apache_pb.gif HTTP/1.0",
     status: 200,
     response_size: 2326,
     referrer: "[http://www.example.com/start.html](http://www.example.com/start.html)",
     user_agent: "Mozilla/4.08 [en] (Win98; I ;Nav)"
}
同时，在这个过程中，如果你觉得有些字段对数据分析没有任何帮助，则可以直接过滤掉，以减少存储上的消耗，比如

数据分析不会关心user信息、request、status信息，这几个字段没必要存储
ObjectId里本身包含了时间信息，没必要再单独存储一个time字段 (当然带上time也有好处，time更能代表请求产生的时间，而且查询语句写起来更方便，尽量选择存储空间占用小的数据类型）
基于上述考虑，一行日志最终存储的内容可能类似如下

{
    _id: ObjectId('4f442120eb03305789000000'),
    host: "127.0.0.1",
    time: ISODate("2000-10-10T20:55:36Z"),
    path: "/apache_pb.gif",
    referer: "[http://www.example.com/start.html](http://www.example.com/start.html)",
    user_agent: "Mozilla/4.08 [en] (Win98; I ;Nav)"
}
写日志
日志存储服务需要能同时支持大量的日志写入，用户可以定制 writeConcern 来控制日志写入能力,猛击这里详细了解writeConcern

db.events.insert({
        host: "127.0.0.1",
        time: ISODate("2000-10-10T20:55:36Z"),
        path: "/apache_pb.gif",
        referer: "[http://www.example.com/start.html](http://www.example.com/start.html)",
        user_agent: "Mozilla/4.08 [en] (Win98; I ;Nav)"
    }
)
如果要想达到最高的写入吞吐，可以指定 writeConcern 为 {w: 0}
而如果日志的重要性比较高（比如需要用日志来作为计费凭证），则可以使用更安全的writeConcern级别，比如 {w: 1} 或 {w: "majority"}
同时，为了达到最优的写入效率，用户还可以考虑批量的写入方式，一次网络请求写入多条日志。

db.events.insert([doc1, doc2, ...])
查询日志
当日志按上述方式存储到 MongoDB 后，就可以满足各种查询需求

查询所有访问 /apache_pb.gif 的请求
q_events = db.events.find({'path': '/apache_pb.gif'})
如果这种查询非常频繁，可以针对path字段建立索引，以高效的服务这类查询

db.events.createIndex({path: 1})
查询某一天的所有请求
q_events = db.events.find({'time': { '$gte': ISODate("2016-12-19T00:00:00.00Z"),'$lt': ISODate("2016-12-20T00:00:00.00Z")}})
通过对time字段建立索引，可加速这类查询

db.events.createIndex({time: 1})
查询某台主机一段时间内的所有请求
 q_events = db.events.find({
    'host': '127.0.0.1',
    'time': {'$gte': ISODate("2016-12-19T00:00:00.00Z"),'$lt': ISODate("2016-12-20T00:00:00.00Z" }
})

通过对host、time建立复合索引可以加速这类查询

db.events.createIndex({host: 1, time: 1})
同样，用户还可以使用MongoDB的aggregation、mapreduce框架来做一些更复杂的查询分析，在使用时应该尽量建立合理的索引以提升查询效率。

数据分片
当写日志的服务节点越来越多时，日志存储的服务需要保证可扩展的日志写入能力以及海量的日志存储能力，这时就需要使用MongoDB sharding来扩展，将日志数据分散存储到多个shard，关键的问题就是shard key的选择。

按时间戳字段分片
一种简单的方式是使用时间戳来进行分片（如ObjectId类型的_id，或者time字段），这种分片方式存在如下问题

因为时间戳一直顺序增长的特性，新的写入都会分到同一个shard，并不能扩展日志写入能力
很多日志查询是针对最新的数据，而最新的数据通常只分散在部分shard上，这样导致查询也只会落到部分shard
按随机字段分片
按照_id字段来进行hash分片，能将数据以及写入都均匀都分散到各个shard，写入能力会随shard数量线性增长，但该方案的问题时，数据分散毫无规律，所有的范围查询（数据分析经常需要用到）都需要在所有的shard上进行查找然后合并查询结果，影响查询效率。

按均匀分布的key分片
假设上述场景里 path 字段的分布是比较均匀的，而且很多查询都是按path维度去划分的，那么可以考虑按照path字段对日志数据进行分片，好处是

写请求会被均分到各个shard
针对path的查询请求会集中落到某个（或多个）shard，查询效率高
不足的地方是

如果某个path访问特别多，会导致单个chunk特别大，只能存储到单个shard，容易出现访问热点
如果path的取值很少，也会导致数据不能很好的分布到各个shard
当然上述不足的地方也有办法改进，方法是给分片key里引入一个额外的因子,比如原来的shard key是 {path: 1}，引入额外的因子后变成

{path: 1, ssk: 1} 其中ssk可以是一个随机值，比如_id的hash值，或是时间戳，这样相同的path还是根据时间排序的
这样做的效果是分片key的取值分布丰富，并且不会出现单个值特别多的情况。

上述几种分片方式各有优劣，用户可以根据实际需求来选择方案。

应对数据增长
分片的方案能提供海量的数据存储支持，但随着数据越来越多，存储的成本会不断的上升，而通常很多日志数据有个特性，日志数据的价值随时间递减，比如1年前、甚至3个月前的历史数据完全没有分析价值，这部分可以不用存储，以降低存储成本，而在MongoDB里有很多方法支持这一需求。

TTL 索引
MongoDB 的TTL索引 可以支持文档在一定时间之后自动过期删除，例如上述日志time字段代表了请求产生的时间，针对该字段建立一个TTL索引，则文档会在30小时后自动被删除。

db.events.createIndex( { time: 1 }, { expireAfterSeconds: 108000 } )
TTL 索引目前是后台单线程来定期（默认60s一次）去删除已过期的文档，如果写入很多，导致积累了大量待过期的文档，则会导致文档过期一直跟不上而一直占用着存储空间。

使用Capped集合
如果对日志保存的时间没有特别严格的要求，只是在总的存储空间上有限制，则可以考虑使用capped collection来存储日志数据，指定一个最大的存储空间或文档数量，当达到阈值时，MongoDB会自动删除capped collection里最老的文档。

db.createCollection("event", {capped: true, size: 104857600000}
定期按集合或DB归档
比如每到月底就将events集合进行重命名，名字里带上当前的月份，然后创建新的events集合用于写入，比如2016年的日志最终会被存储在如下12个集合里

 events-201601
 events-201602
 events-201603
 events-201604
 ....
 events-201612
当需要清理历史数据时，直接将对应的集合删除掉

 db["events-201601"].drop()
 db["events-201602"].drop()
不足到时候，如果要查询多个月份的数据，查询的语句会稍微复杂些，需要从多个集合里查询结果来合并