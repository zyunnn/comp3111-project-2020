package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	
	@Before
	public void setUp2() throws Exception {
		
		teSlot.setStart(startT);
		teSlot.setEnd(endT);
		teSlot.setVenue(venueT);
		teSlot.setDay(dayy);
		test_course.addSlot(teSlot);
		
//		
	}

	@Test
	public void testTitle() {
		assertEquals(title, test_course.getTitle());
	}
	
	@Test
	public void testdse() {
		assertEquals(desc, test_course.getDescription());
	}
	
	@Test
	public void testexclu() {
		assertEquals(excl, test_course.getExclusion());
	}

	@Test
	public void testnumslot() {
		assertEquals(1, test_course.getNumSlots());
	}
	

	@Test
	public void teststarthr() {
		assertEquals(14, teSlot.getStartHour());
	}
	
	@Test
	public void teststartmin() {
		assertEquals(0, teSlot.getStartMinute());
	}
	


	@Test
	public void testendhr() {
		assertEquals(15, teSlot.getEndHour());
	}
	
	@Test
	public void testendmin() {
		assertEquals(50, teSlot.getEndMinute());
	}	
	
	@Test
	public void testvenue() {
		assertEquals(venueT, teSlot.getVenue());
	}

	@Test
	public void testday() {
		assertEquals(2, teSlot.getDay());
	}
	
}
