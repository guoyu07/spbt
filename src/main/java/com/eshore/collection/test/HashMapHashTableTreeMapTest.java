package com.eshore.collection.test;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

/**
 * hashmap & hashtable & treemap
 * 
 * @author hechao
 * @desc hashtable线程安全效率较低不能存入null的key, hashmap线程不安全效率高可以存入null的key,
 *       treemap是线程不安全key不能为空且有序的map,
 */
public class HashMapHashTableTreeMapTest {

	@Test
	public void test() {
		TreeMap<String, String> treeMap = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		treeMap.put("123", "123");
		treeMap.put("123", "123");
		treeMap.put("123", "123");
		treeMap.put("123", "123");
		treeMap.put("123", "123");

		for (Entry<String, String> entry : treeMap.entrySet()) {
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}
	}
}
