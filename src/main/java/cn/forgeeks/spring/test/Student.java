package cn.forgeeks.spring.test;

public class Student {

	String name;
	String number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Student() {
		super();
	}

	public Student(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}

	@Override
	public String toString() {
		return name + " : " + number;
	}
}
