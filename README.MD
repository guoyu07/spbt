Java程序员的自我修养
======================

[![Build Status](https://travis-ci.org/mybatis/spring.svg?branch=master)](https://travis-ci.org/mybatis/spring)
[![Coverage Status](https://coveralls.io/repos/mybatis/spring/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/spring?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/5619b698a193340f2f000520/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5619b698a193340f2f000520)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)


清单
------------------
 
* [集合]( )
* 线程
* JVM
 
* 关系DB
* Redis
* DB优化
* DB锁
 
* 查找排序
* 快排归并
 
* 常见dp
 
* nginx和tomcat
 
* osi和tcpi/ip
 

Redis
----------
  
* redis适用场景

		redis是存储键值对的非关系型数据库, 他可以应用在分布式缓存,消息队列, redis查询存储效率相比于传统数据库高很多  

* redis数据结构

		redis可以存储的数据结构有 :  string字符串, list列表, hash哈希, set去重列表, sortedSet有序去重列表  
		string操作的具体过程是, 根据key来判断是否存储过该键值对, 若是则覆盖, 否则直接存进去		
		list操作的具体过程是, lpush 将某个值左插入到指定list, rpush右插入, lpop左删除一个元素, rpop右删除, 
			lrange查看元素
		set操作的具体过程是, sadd添加唯一元素, srem删除, smembers取出元素, sunion取并集, sinter取交集, 
			sdiff显示收个set的独特元素
		sortedSet操作的具体过程是, 		
		hash操作的具体过程是hmset添加具有不同属性元素, hmget获取元素属性值, hgetall获取元素所有属性值
		
* redis复制
		
		redis复制有两种方式, 主从和从从, 主节点负责写从节点负责读
		当有新增的数据或数据发生变化时, 就需要从主节点同步数据到从节点, 具体过程是,
		从节点发送同步命令到主节点, 主节点收到后依次发送rpd文件和增量数据到从节点,  
		复制的优点: 
			高可用: 主节点宕机从节点上, 服务仍可用
			高性能: 读写分离, 减轻主节点压力, 提高从节点相应速度
			横向拓展: 从节点越多, 查询能力越强
		复制的缺点:
			数据一致性


* redis哈希槽

		redis集群有16384个哈希槽, 存入数据时用crc16算法计算出key的转换结果, 在对16384取模, 
		得到的数字就是该key-value要存入的hash槽id, 这种机制主要是方便了从节点的增加和移除, 
		增加时只要将其他节点的hash槽移动到该节点即可, 移除就将该节点hash槽移动到其他节点














