package models;

import enums.Gender;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class Student {

	private long id;
	private String name;
	private int age;
	private Gender gender;
	private int marks;
	private double tuitionFee;

	public Student(String name) {
		this.name = name;
	}

	public Student(long id, String name, int age, Gender gender, int marks, double tuitionFee) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.marks = marks;
		this.tuitionFee = tuitionFee;
	}

	public Student() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public double getTuitionFee() {
		return tuitionFee;
	}

	public void setTuitionFee(double tuitionFee) {
		this.tuitionFee = tuitionFee;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", gender=" + gender +
				", marks=" + marks +
				'}';
	}
}
