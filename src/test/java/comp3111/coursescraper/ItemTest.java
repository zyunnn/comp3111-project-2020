package comp3111.coursescraper;


import org.junit.Test;

import comp3111.coursescraper.Course;

import static org.junit.Assert.*;

/**
 * Test if Course object items are normal
 * @author zhou zhuo rui
 *
 */
public class ItemTest {

	/**
	 * Test if title set and get function normally
	 */
	@Test
	public void testSetTitle() {
		Course i = new Course();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
}
