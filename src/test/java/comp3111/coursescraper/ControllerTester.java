package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ControllerTester {

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
	Controller can = new Controller();

	
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
	

}
