package comp3111.coursescraper;

import java.util.*;

/**
 * An instructor teaches at least one valid slot and have information
 * about his/her name, score on sfq and number of section(s) teach.
 */

public class Instructor {
	static private Set<String> allNames = new HashSet<String>();
	static private Set<String> teachingTu1510 = new HashSet<String>();
	private String name;
	private float sfqScore;
	private int numSection;
	
	/**
	 * Constructor for Instructor class
	 * @param name		name of the instructor
	 * @param score		instructor's score in sfq
	 */
	public Instructor(String name, float score) {
		this.name = name;
		this.sfqScore = score;
	}
	
	/**
	 * Add instructor name to the full list 
	 * @param instructorName	name of the instructor
	 */
	public static void addAllInstructor(String instructorName) {
		allNames.add(instructorName);
	}
	
	/**
	 * Add instructor who has teaching assignment on Tuesday 3:10pm
	 * so that the name is excluded in search result (Main tab)
	 * @param instructorName	name of the instructor
	 */
	public static void addTeachingTu1510(String instructorName) {
		teachingTu1510.add(instructorName);
	}
	
	/**
	 * Get list of instructor(s) who has no teaching assignment on 
	 * Tuesday 3:10pm
	 * @return instructorList 
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
	
	/**
	 * Reset all instructor name list for new search
	 */
	public static void resetAllNameList() {
		allNames.clear();
		teachingTu1510.clear();
	}
}


