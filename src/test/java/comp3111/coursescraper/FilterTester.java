package comp3111.coursescraper;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * A JunitTest case for the filter class
 * */

public class FilterTester {
	Filter fill = new Filter();
	List<Course> input = new ArrayList<Course>();
	// first course
	Course test_course = new Course();
	String title = "comp3999";
	String desc = "this is a course";
	String excl = "math2011";
	
	Slot teSlot_1 = new Slot();
	String startT = "02:00PM";
	String endT = "03:50PM";
	String venueT = "USA";
	String instructor = "Donald Trump";
	String sc = "L1";
	int dayy_1 = 1;
	
	Slot teSlot_2 = new Slot();
	String startT_2 = "09:00AM";
	String endT_2 = "01:00PM";
	String venueT_2 = "USA";
	String instructor_2 = "Donald Trump";
	String sc_2 = "L2";
	int dayy_2 = 2;
	// second course
	Course test_course_2 = new Course();
	String title_2 = "math3999";
	String desc_2 = "this is two course";
	String excl_2 = null;
	
	Slot teSlot_3 = new Slot();
	String startT_3 = "05:00PM";
	String endT_3 = "06:50PM";
	String venueT_3 = "USA";
	String instructor_3 = "Donald Trump";
	String sc_3 = "LA1";
	int dayy_3 = 0;
	
	Slot teSlot_4 = new Slot();
	String startT_4 = "06:00AM";
	String endT_4 = "11:00AM";
	String venueT_4 = "USA";
	String instructor_4 = "Donald Trump";
	String sc_4 = "T1";
	int dayy_4 = 3;
	Boolean [] CBList = new Boolean[11];

	/**
	 * Constrctor 
	 * @throws Exception Errors discovered during construction
	 */
	@Before
	public void setUp() throws Exception {
		Arrays.fill(CBList, Boolean.FALSE);

		test_course.setTitle(title);
		test_course.setDescription(desc);
		test_course.setExclusion(excl);
		test_course.setCommonCore("Common");
		
		teSlot_1.setDay(dayy_1);
		teSlot_1.setEnd(endT);
		teSlot_1.setStart(startT);
		teSlot_1.setInstructor(instructor);
		teSlot_1.setVenue(venueT);
		teSlot_1.setSectionCode(sc);
		
		teSlot_2.setDay(dayy_2);
		teSlot_2.setEnd(endT_2);
		teSlot_2.setStart(startT_2);
		teSlot_2.setInstructor(instructor_2);
		teSlot_2.setVenue(venueT_2);
		teSlot_2.setSectionCode(sc_2);
		
		test_course.addSlot(teSlot_1);
		test_course.addSlot(teSlot_2);
		
		test_course_2.setTitle(title_2);
		test_course_2.setDescription(desc_2);
		test_course_2.setExclusion(excl_2);
		test_course_2.setCommonCore("C");
		
		teSlot_3.setDay(dayy_3);
		teSlot_3.setEnd(endT_3);
		teSlot_3.setStart(startT_3);
		teSlot_3.setInstructor(instructor_3);
		teSlot_3.setVenue(venueT_3);
		teSlot_3.setSectionCode(sc_3);
		
		teSlot_4.setDay(dayy_4);
		teSlot_4.setEnd(endT_4);
		teSlot_4.setStart(startT_4);
		teSlot_4.setInstructor(instructor_4);
		teSlot_4.setVenue(venueT_4);
		teSlot_4.setSectionCode(sc_4);
		
		test_course_2.addSlot(teSlot_3);
		test_course_2.addSlot(teSlot_4);
		
		input.add(test_course);
		input.add(test_course_2);
	}
	/**
	 * Test for all the main call_filter function given a list of course
	 * */
	@Test
	public void testfiltermain() {
		Arrays.fill(CBList, Boolean.FALSE);
		assertEquals(fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode(), teSlot_1.getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[1] = true;
		CBList[0] = true;
		assertEquals(fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode(), teSlot_2.getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[0] = true;
		assertEquals(fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode(), teSlot_2.getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[1] = true;
		assertEquals(fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode(), teSlot_1.getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[8] = true;
		assertEquals(fill.call_filter(CBList, input).get(0).getTitle(), test_course.getTitle());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[8] = true;
		List<Course> input2 = new ArrayList<Course>();
		input2.add(test_course_2);
		assertEquals(fill.call_filter(CBList, input2).isEmpty(), true);
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[10] = true;
		assertEquals(teSlot_3.getSectionCode(), fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[3] = true;
		assertEquals(teSlot_1.getSectionCode(), fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[4] = true;
		assertEquals(teSlot_2.getSectionCode(), fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[2] = true;
		assertEquals(teSlot_3.getSectionCode(), fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[5] = true;
		assertEquals(teSlot_4.getSectionCode(), fill.call_filter(CBList, input).get(0).getSlot(0).getSectionCode());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[6] = true;
		assertEquals(true, fill.call_filter(CBList, input).isEmpty());
		
		Arrays.fill(CBList, Boolean.FALSE);
		CBList[7] = true;
		assertEquals(true, fill.call_filter(CBList, input).isEmpty());
	}
	
	/**
	 * Test for the function to filter sections with lab or tutorial
	 * */
	@Test
	public void testchecklab1() {
		assertEquals(true, fill.checklab(test_course_2));
	}
	
	/**
	 * Test for the function to filter sections with lab or tutorial given a course
	 * */
	@Test
	public void testchecklab21() {
		assertEquals(false, fill.checklab(test_course));
	}
	
	/**
	 * Test for the function to filter sections with AM slot given a course
	 * */
	@Test
	public void testfiltertime() {
		boolean [] arr = new boolean [2];
		arr[0] = true;
		arr[1] = false;
		assertEquals(true, fill.filtertime(test_course, arr).contains(sc_2));
	}
	
	/**
	 * Test for the function to filter sections with PM slot given a course
	 * */
	@Test
	public void testfiltertime2() {
		boolean [] arr = new boolean [2];
		arr[0] = false;
		arr[1] = true;
		assertEquals(true, fill.filtertime(test_course_2, arr).contains(sc_3));
	}
	
	/**
	 * Test for the function to filter sections with AM and PM or AM, PM slot given a course
	 * */
	@Test
	public void testfiltertime3() {
		boolean [] arr = new boolean [2];
		arr[0] = true;
		arr[1] = true;
		assertEquals(true, fill.filtertime(test_course, arr).contains(sc_2));
	}
	
	/**
	 * Test for the function to return all the section given a course
	 * */
	@Test
	public void testallsection() {
		Set<String> te = new HashSet<String>();
		te.add(sc);
		te.add(sc_2);
		assertEquals(te, fill.allsection(test_course));
	}

	/**
	 * Test for the function to return sections with certain day(s) given a course
	 * */
	@Test
	public void testfilterday() {
		Course temp = new Course();
		Slot s1 = new Slot();
		s1.setDay(0);
		s1.setSectionCode("L1");
		
		Slot s2 = new Slot();
		s2.setDay(1);
		s2.setSectionCode("L2");
		
		Slot s3 = new Slot();
		s3.setDay(2);
		s3.setSectionCode("L3");

		Slot s4 = new Slot();
		s4.setDay(3);
		s4.setSectionCode("L4");

		Slot s5 = new Slot();
		s5.setDay(4);
		s5.setSectionCode("L5");

		Slot s6 = new Slot();
		s6.setDay(5);		
		s6.setSectionCode("L6");

		temp.addSlot(s6);
		temp.addSlot(s5);
		temp.addSlot(s4);
		temp.addSlot(s3);
		temp.addSlot(s2);
		temp.addSlot(s1);
		Set<String> te = new HashSet<String>();
		int [] arr = new int [6];
		Arrays.fill(arr, 0);
		arr[5] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s6.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[4] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s5.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[3] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s4.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[2] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s3.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[1] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		assertEquals(true, fill.filterday(temp, arr).contains(s1.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[1] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[2] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[3] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[3] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[4] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
		
		Arrays.fill(arr, 0);
		arr[0] = 100;
		arr[5] = 100;
		assertEquals(false, fill.filterday(temp, arr).contains(s2.getSectionCode()));
	}
	

}
