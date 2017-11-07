package com.eshore.collection.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

/**
 * Iterator & ListIterator
 * @author hechao
 * @desc iterator单向遍历,适用所有集合类型,  listiterator双向遍历, 适合list
 */
public class IteratorTest {

	/**
	 * iterator: 
		list1
		list2
		list3
		list4
		list iterator (next) : 
		list1
		list2
		list3
		list4
		list iterator (previous) : 
		list4
		list3
		list2
		list1

	 */
	@Test
	public void test01(){
		List<String> list= new ArrayList<String>();
		list.add("list1");
		list.add("list2");
		list.add("list3");
		list.add("list4");
		Iterator<String> iterable=list.iterator();
//		iterable.remove();
		System.out.println("iterator: ");
		while(iterable.hasNext()) {
			System.out.println(iterable.next());
		}
		System.out.println("list iterator (next) : ");
		ListIterator<String> listIterator=list.listIterator();
//		listIterator.remove();
		while(listIterator.hasNext()) {
			System.out.println(listIterator.next());
		}
		System.out.println("list iterator (previous) : ");
		while(listIterator.hasPrevious()){
			System.out.println(listIterator.previous());
		}
		
	}
 
	
}
