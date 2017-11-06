摘要： 电商业务一个基本的功能模块就是存储品类丰富的商品信息，各种商品特性、参数各异，MongoDB 灵活的文档模型非常适合于这类业务，本文主要介绍如何使用 MongoDB 来存储商品分类信息，内容翻译自User case - Product Catalog 关系型数据库解决方案 上述问题使用传统的关系型数据库也可以解决，比如以下几种方案 针对不同商品，创建不同的表 比如音乐专辑、电影这2种商品，有一部分共同的属性，但也有很多自身特有的属性，可以创建2个不同的表，拥有不同的schema。

电商业务一个基本的功能模块就是存储品类丰富的商品信息，各种商品特性、参数各异，MongoDB 灵活的文档模型非常适合于这类业务，本文主要介绍如何使用 MongoDB 来存储商品分类信息，内容翻译自User case - Product Catalog

关系型数据库解决方案
上述问题使用传统的关系型数据库也可以解决，比如以下几种方案

针对不同商品，创建不同的表
比如音乐专辑、电影这2种商品，有一部分共同的属性，但也有很多自身特有的属性，可以创建2个不同的表，拥有不同的schema。

CREATE TABLE `product_audio_album` (
    `sku` char(8) NOT NULL,
    ...
    `artist` varchar(255) DEFAULT NULL,
    `genre_0` varchar(255) DEFAULT NULL,
    `genre_1` varchar(255) DEFAULT NULL,
    ...,
    PRIMARY KEY(`sku`))
...
CREATE TABLE `product_film` (
    `sku` char(8) NOT NULL,
    ...
    `title` varchar(255) DEFAULT NULL,
    `rating` char(8) DEFAULT NULL,
    ...,
    PRIMARY KEY(`sku`))
...
这种做法的主要问题在于

针对每个新的商品分类，都需要创建新的表
应用程序开发者必须显式的将请求分发到对应的表上来查询，一次查询多种商品实现起来比较麻烦
所有商品存储到单张表
CREATE TABLE `product` (
    `sku` char(8) NOT NULL,
    ...
    `artist` varchar(255) DEFAULT NULL,
    `genre_0` varchar(255) DEFAULT NULL,
    `genre_1` varchar(255) DEFAULT NULL,
    ...
    `title` varchar(255) DEFAULT NULL,
    `rating` char(8) DEFAULT NULL,
    ...,
    PRIMARY KEY(`sku`))
将所有的商品存储到一张表，这张表包含所有商品需要的属性，不同的商品根据需要设置不同的属性，这种方法使得商品查询比较简单，并且允许一个查询跨多种商品，但缺点是浪费的空间比较多。

提取公共属性，多表继承
CREATE TABLE `product` (
    `sku` char(8) NOT NULL,
    `title` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `price`, ...
    PRIMARY KEY(`sku`))

CREATE TABLE `product_audio_album` (
    `sku` char(8) NOT NULL,
    ...
    `artist` varchar(255) DEFAULT NULL,
    `genre_0` varchar(255) DEFAULT NULL,
    `genre_1` varchar(255) DEFAULT NULL,
    ...,
    PRIMARY KEY(`sku`),
    FOREIGN KEY(`sku`) REFERENCES `product`(`sku`))
...
CREATE TABLE `product_film` (
    `sku` char(8) NOT NULL,
    ...
    `title` varchar(255) DEFAULT NULL,
    `rating` char(8) DEFAULT NULL,
    ...,
    PRIMARY KEY(`sku`),
    FOREIGN KEY(`sku`) REFERENCES `product`(`sku`))
...
上述方案将所有商品公共的属性提取出来，将公共属性存储到一张表里，每种商品根据自身的需要创建新的表，新表里只存储该商品特有的信息。

Entity Attribute Values 形式存储
所有的数据按照<商品SKU， 属性、值> 的3元组的形式存储，这个方案实际上是把关系型数据库当KV存储使用，模型简单，但应对复杂的查询不是很方便。

Entity	Attribute	Values
sku_00e8da9b	type	Audio Album
sku_00e8da9b	title	A Love Supreme
sku_00e8da9b	...	...
sku_00e8da9b	artist	John Coltrane
sku_00e8da9b	genre	Jazz
sku_00e8da9b	genre	General
...	...	...
MongoDB 解决方案
MognoDB 与关系型数据库不同，其无schema，文档内容可以非常灵活的定制，能很好的使用上述商品分类存储的需求; 将商品信息存储在一个集合里，集合里不同的商品可以自定义文档内容。

比如一个音乐专辑可以类似如下的文档结构

{
  sku: "00e8da9b",
  type: "Audio Album",
  title: "A Love Supreme",
  description: "by John Coltrane",
  asin: "B0000A118M",

  shipping: {
    weight: 6,
    dimensions: {
      width: 10,
      height: 10,
      depth: 1
    },
  },

  pricing: {
    list: 1200,
    retail: 1100,
    savings: 100,
    pct_savings: 8
  },

  details: {
    title: "A Love Supreme [Original Recording Reissued]",
    artist: "John Coltrane",
    genre: [ "Jazz", "General" ],
        ...
    tracks: [
      "A Love Supreme Part I: Acknowledgement",
      "A Love Supreme Part II - Resolution",
      "A Love Supreme, Part III: Pursuance",
      "A Love Supreme, Part IV-Psalm"
    ],
  },
}
而一部电影则可以存储为

{
  sku: "00e8da9d",
  type: "Film",
  ...,
  asin: "B000P0J0AQ",

  shipping: { ... },

  pricing: { ... },

  details: {
    title: "The Matrix",
    director: [ "Andy Wachowski", "Larry Wachowski" ],
    writer: [ "Andy Wachowski", "Larry Wachowski" ],
    ...,
    aspect_ratio: "1.66:1"
  },
}
所有商品都拥有一些共同的基本信息，特定的商品可以根据需要扩展独有的内容，非常方便； 基于上述模型，MongoDB 也能很好的服务各类查询。

查询某个演员参演的所有电影，并按发型日志排序

db.products.find({'type': 'Film', 'details.actor': 'Keanu Reeves'}).sort({'details.issue_date', -1})
上述查询也可以通过建立索引来加速

db.products.createIndex({ type: 1, 'details.actor': 1, 'details.issue_date': -1 })
查询标题里包含特定信息的所有电影

db.products.find({
    'type': 'Film',
    'title': {'$regex': '.*hacker.*', '$options':'i'}}).sort({'details.issue_date', -1})
可建立如下索引来加速查询

db.products.createIndex({ type: 1, details.issue_date: -1, title: 1 })
扩展
当单个节点无法满足海量商品信息存储的需求时，就需要使用MongoDB sharding来扩展，假定大量的查询都是都会基于商品类型，那么就可以使用商品类型字段来进行分片。

db.shardCollection('products', { key: {type: 1} })
分片时，尽量使用复合的索引字段，这样能满足更多的查询需求，比如基于商品类型之后，还会经常根据商品的风格标签来查询，则可以把商品的标签字段作为第二分片key。

db.shardCollection('products', { key: {type: 1, 'details.genre': 1} })
如果某种类型的商品，拥有相同标签的特别多，则会出现jumbo chunk的问题，导致无法迁移，可以进一步的优化分片key，以避免这种情况。

db.shardCollection('products', { key: {type: 1, 'details.genre': 1, sku: 1} })
加入第3分片key之后，即使类型、风格标签都相同，但其sku信息肯定不同，就肯定不会出现超大的chunk。