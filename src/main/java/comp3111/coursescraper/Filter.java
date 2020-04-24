package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Filter {

	private String [] filterlist;
	private static final int DEFAULT_MAX_filter = 11;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}
	
	// identify the needs
	public void call_filter () {
		
	}
	
	
	// filter the course by whether have am or pm session
	public List<Course> filtertime(List<Course> input, Boolean AMM, Boolean PMM) {
		List<Course> result = new ArrayList<Course>();
		for (int i = 1; i < input.size(); i++) {
			Course temp = input.get(i);
			if (AMM && PMM) {
				
			}else if ((AMM==true) && ( PMM == false)) {
				
			}else {
				
			}
			result.add(input.get(i));
		}
		
		return result;
	};
	
	
	// filter the course based on the days selected
	public List<Course> filterdate(Course input, String[] dayy) {
		
		return null;
	};
	
	// select those course that are CC
	public List<Course> filterCC(Course input) {
		return null;
	};
	
	// select those course that have no exclusion
	public List<Course> filterEX(Course input) {
		return null;
	};
	
	
	// select those course that have labs or tutorials
	public List<Course> Lab(Course input) {
		return null;
	};

}
