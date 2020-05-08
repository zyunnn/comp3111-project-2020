package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

/**
 * A slot should contain information about time (start, end, day), venue, 
 * instructor and section code. Slot with time out of the range of 9:00am 
 * to 10:00pm or with time on Sunday is considered as invalid. A time slot 
 * which contains 2 days (e.g. TuTh 12:00PM - 01:20PM) should be considered 
 * as two slots.
 */

public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private String instructor;
	private String sectionCode;
	/**
	 * An array store all the days in string
	 * */
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	/**
	 * A mapping of string value of day to integer value
	 * */
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}

	/**
	 * Copy a slot as the new slot
	 * @return a new slot created
	 */
	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		s.sectionCode = this.sectionCode;
		s.instructor = this.instructor;
		return s;
	}
	
	/**
	 * Convert time format of the slot
	 * @return a string type time 
	 */
	public String toString() {
		return DAYS[day] + start.toString() + "-" + end.toString() + ":" + venue;
	}
	
	/**
	 * @return the starting hour of the slot
	 */
	public int getStartHour() {
		return start.getHour();
	}
	
	/**
	 * @return the starting minute of the slot
	 */
	public int getStartMinute() {
		return start.getMinute();
	}
	
	/**
	 * @return the ending hour of the slot
	 */
	public int getEndHour() {
		return end.getHour();
	}
	
	/**
	 * @return the ending minute of the slot
	 */
	public int getEndMinute() {
		return end.getMinute();
	}
	
	/**
	 * @return the start time in hour and minute
	 */
	public LocalTime getStart() {
		return start;
	}
	
	/**
	 * @param start 	the start time to set for the slot
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	
	/**
	 * @return the end time in hour and minute
	 */
	public LocalTime getEnd() {
		return end;
	}
	
	/**
	 * @param end 		the end time to set for the slot
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	
	/**
	 * @return the venue of the slot
	 */
	public String getVenue() {
		return venue;
	}
	
	/**
	 * @param venue 	the venue to set for the slot
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the day of the slot
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day 		the day to set for the slot
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * @param instructorName the name of instructor to set for the slot
	 */
	public void setInstructor(String instructorName) {
		this.instructor = instructorName;
	}
	
	/**
	 * @return name of instructor of the slot
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * @param sectionCode	section code to set for the slot
	 */
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	/**
	 * @return section code of the slot
	 */
	public String getSectionCode() {
		return sectionCode;
	}

}
