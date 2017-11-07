package com.eshore.collection.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Iterator fail-fast 和 fail-safe的区别
 * 
 * @author hechao
 *
 * @desc fail-fast指遍历集合过程中集合结构被改变的话就会抛出
 *       java.util.ConcurrentModificationException异常的机制, 例如ArrayList, HashMap,
 *       HashSet
 * @desc fail-save指遍历集合过程中即使集合结构被改变也不会抛出
 *       java.util.ConcurrentModificationException异常的机制,
 *       这是由于fail-safe机制下只会操作集合的副本, 例如 ConcurrentHashMap, CopyOnWriteArrayList
 */
public class IteratorTest02 {

	List<String> list = new ArrayList<String>();

	@Before
	public void before() {
		list.add("list1");
		list.add("list2");
		list.add("list3");
		list.add("list4");
		list.add("list5");
	}

	@Test
	public void test01() {
		Iterator<String> iterator = list.iterator();
		/*
		 * while(iterator.hasNext()){ String string=iterator.next();
		 * System.out.println(string); if(string.equals("list2"))
		 * list.remove(2); }
		 */

		for (String s : list) {
			if (s.equals("list2"))
				list.remove(2);
			System.out.println(s);
		}
		
	}

	@Test
	public void test02() {
		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.put("key-1", "value-1");
		concurrentHashMap.put("key-2", "value-2");
		concurrentHashMap.put("key-3", "value-3");
		Iterator<Entry<String, String>> iterator = concurrentHashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
			if (entry.getKey().equals("key-2")) {
				concurrentHashMap.remove("key-2");
			}
		}
		iterator = concurrentHashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
	}

}
