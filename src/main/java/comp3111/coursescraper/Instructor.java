package comp3111.coursescraper;

import java.util.*;

public class Instructor {
	static private Set<String> allNames = new HashSet<String>();
	static private Set<String> teachingTu1510 = new HashSet<String>();
	private String name;
	private float sfqScore;
	private int numSection;
	
	public Instructor(String name, float score) {
		this.name = name;
		this.sfqScore = score;
	}
	
	/**
	 * @param name of instructor
	 */
	public static void addAllInstructor(String instructorName) {
		allNames.add(instructorName);
	}
	
	/**
	 * @param name of instructor
	 */
	public static void addTeachingTu1510(String instructorName) {
		teachingTu1510.add(instructorName);
	}
	
	/**
	 * @return all instructor list without teaching assignment at Tu3:10pm
	 */
	public static List<String> getInstructorNoTu1510() {
		Set<String> all = new HashSet<String>();
		all.addAll(allNames);
		all.removeAll(teachingTu1510);
		List<String> instructorList = new ArrayList<String>(all);
		Collections.sort(instructorList);
		return instructorList;
	}
	
	
	/**
	 * @return average sfq score
	 */
	public float getAverageSfq() {
		return sfqScore/numSection;
	}
	
	/*
	 * reset both static variable
	 */
	public static void resetAllNameList() {
		allNames.clear();
		teachingTu1510.clear();
	}
}


