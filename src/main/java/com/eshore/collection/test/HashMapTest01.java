package com.eshore.collection.test;

/**
 * HashMap
 * 
 * @author hechao
 * @desc hashmap特性: 不是线程安全, 允许存null的key或者value, 底层是数组加entry单链表, 遍历时集合结构被修改就会抛出异常
 *       , 要在并发下安全使用它, 可以在外部加锁, 也可以用集合工具类Collections.synchronizedhashMap方法转换下,
 *       还可以用hashtable或concurrenthashmap代替, 不过前者效率低, 后者效率较高, 还是线程安全的
 * @desc hashmap的put: 通过key的hashcode直接找到它应该放在数组的哪个位置, 而不是用equals一个一个比,
 *       如果该位置没有元素就把entry放进去, 如果该位置有元素, 就遍历该位置链表, 看看有没有旧的key和新的key是equals为true,
 *       有, 就覆盖原来的元素, 没有, 就插入到链表头
 * @desc hashmap的get: 通过key的hashcode直接找到它应该放在数组的哪个位置,如果该位置有元素, 就遍历该位置链表,
 *       看看有没有旧的key和新的key是equals为true, 有,就返回该entry的value
 * @desc hashmap的扩容: 默认容量16装载因子0.75, 超过他们乘积12就会扩容到两倍, 具体先把旧的数组数据转移,
 *       计算每一个entry的key的hashcode该放入数组的位置, 再放入到新的数组, 扩容的过程效率很低, 所以建议初始化就设置合适的容量,
 *       同时容量值最好是2次幂, 这是为了均匀地放置元素, 减少链表的遍历和equals的比较,
 *       根据key的hashcode来计算数组位置index的过程是 hash值 & length-1 , 当length=2次幂时,
 *       根据位运算的特点, 值会<=length-1,
 */
public class HashMapTest01 {

}
