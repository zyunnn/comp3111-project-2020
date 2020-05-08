package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * A Junit Test case for testing all the functions given the course class
 * */
public class CourseTester {
	Course test_course = new Course();
	String title = "comp";
	String desc = "this is a course";
	String excl = "math";
	int num_slot = 0;
	
	Slot teSlot = new Slot();
	String startT = "02:00PM";
	String endT = "03:50PM";
	String venueT = "home";
	int dayy = 2;
	
	
	/**
	 * Constructor for test_course
	 * @throws Exception There are errors during the process of 
	 * constructing the test_course Course object
	 */
	@Before
	public void setUp() throws Exception {
		test_course.setTitle(title);
		test_course.setDescription(desc);
		test_course.setExclusion(excl);
		test_course.setNumSlots(num_slot);

//		
//		teSlot.setStart(startT);
//		teSlot.setEnd(endT);
//		teSlot.setVenue(venueT);
//		teSlot.setDay(dayy);
//		
//		
	}
	
	/**
	 * Constructor for test slot Slot object
	 * @throws Exception There are errors during the process of 
	 * constructing the test_course Course object
	 */
	@Before
	public void setUp2() throws Exception {
		
		teSlot.setStart(startT);
		teSlot.setEnd(endT);
		teSlot.setVenue(venueT);
		teSlot.setDay(dayy);
		test_course.addSlot(teSlot);
		
//		
	}

	/**
	 * Test for the getTitle function of course class
	 * */
	@Test
	public void testTitle() {
		assertEquals(title, test_course.getTitle());
	}
	
	/**
	 * Test for the getDescription function of course class
	 * */
	@Test
	public void testdse() {
		assertEquals(desc, test_course.getDescription());
	}
	
	/**
	 * Test for the getExclusion function of course class
	 * */
	@Test
	public void testexclu() {
		assertEquals(excl, test_course.getExclusion());
	}
	
	/**
	 * Test for the getNumSolts function of course class
	 * */
	@Test
	public void testnumslot() {
		assertEquals(1, test_course.getNumSlots());
	}
	
	/**
	 * Test for the getStartHour function of course class
	 * */
	@Test
	public void teststarthr() {
		assertEquals(14, teSlot.getStartHour());
	}
	
	/**
	 * Test for the getStartMinute function of course class
	 * */
	@Test
	public void teststartmin() {
		assertEquals(0, teSlot.getStartMinute());
	}
	

	/**
	 * Test for the getEndHour function of course class
	 * */
	@Test
	public void testendhr() {
		assertEquals(15, teSlot.getEndHour());
	}
	/**
	 * Test for the getEndMinute function of course class
	 * */
	@Test
	public void testendmin() {
		assertEquals(50, teSlot.getEndMinute());
	}	
	/**
	 * Test for the getVenue function of course class
	 * */
	@Test
	public void testvenue() {
		assertEquals(venueT, teSlot.getVenue());
	}
	/**
	 * Test for the getDay function of course class
	 * */
	@Test
	public void testday() {
		assertEquals(2, teSlot.getDay());
	}
	
}
