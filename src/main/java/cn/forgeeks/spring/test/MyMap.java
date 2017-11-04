package cn.forgeeks.spring.test;

import java.util.HashMap;

public class MyMap {

	 HashMap< String, String> hashMap;

	public MyMap() {
		super();
	}

	public MyMap(HashMap<String, String> hashMap) {
		super();
		this.hashMap = hashMap;
	}

	public HashMap<String, String> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, String> hashMap) {
		this.hashMap = hashMap;
	}
	 
	

}
