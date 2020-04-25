package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javafx.scene.control.CheckBox;

public class Filter {

//	private String [] filterlist;
	private static final int DEFAULT_MAX_filter = 11;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}
	
	// identify the needs
	/*
	 *  CBList[0] = AmBox;
    	CBList[1] = PmBox;
    	CBList[2] = MondayBox;
    	CBList[3] = TuesdayBox;
    	CBList[4] = WednesdayBox;
    	CBList[5] = ThursdayBox;
    	CBList[6] = FridayBox;
    	CBList[7] = SaturdayBox;
    	CBList[8] = CCBox;
    	CBList[9] = NExclBox;
    	CBList[10] = LabBox;
	 * */
	public List<Course> call_filter (CheckBox[] CBList, List<Course> input) {
		List<Course> temp = input;
		boolean flag = false;
    	if(CBList[0].isSelected() && CBList[1].isSelected()) {
    		temp = filtertime(temp, true, true);
    		flag = true;
//    		System.out.println("captured");
    	}else if (CBList[0].isSelected()) {
    		temp = filtertime(temp, true, true);
    		flag = true;
    	}else {
    		temp = filtertime(temp, true, true);
    		flag = true;
    	}
    	
    	if (CBList[2].isSelected()) {
    		temp = filterdate(temp, "Mo");
    		flag = true;
    	}
    	if (CBList[3].isSelected()) {
    		temp = filterdate(temp, "Tu");
    		flag = true;
    	}
    	if (CBList[4].isSelected()) {
    		temp = filterdate(temp, "We");
    		flag = true;
    	}
    	if (CBList[5].isSelected()) {
    		temp = filterdate(temp, "Th");
    		flag = true;
    	}
    	if (CBList[6].isSelected()) {
    		temp = filterdate(temp, "Fr");
    		flag = true;
    	}
    	if (CBList[7].isSelected()) {
    		temp = filterdate(temp, "Sa");
    		flag = true;
    	}
    	
    	if (CBList[8].isSelected()) {
    		temp = filterCC(temp);
    		flag = true;
    	}
    	
       	if (CBList[9].isSelected()) {
    		temp = filterEX(temp);
    		flag = true;
    	}
       	
       	if (CBList[10].isSelected()) {
    		temp = filterLab(temp);
    		flag = true;
    	}
       	
       	if (flag) {
       		return temp;
       	}else {
       		return input;
       	}
    
	}
//	
//	public String testing(String sta2te) {
//		return sta2te;
//	}
	
	
	// filter the course by whether have am or pm session
	public List<Course> filtertime(List<Course> input, Boolean AMM, Boolean PMM) {
		System.out.println("captured2");

		List<Course> result = new ArrayList<Course>();
		for (int i = 1; i < input.size(); i++) {
			Course temp = input.get(i);
//			for (int )
				if (AMM && PMM) {
					
				}else if ((AMM==true) && ( PMM == false)) {
					
				}else {
					
				}
			result.add(input.get(i));
		}
		
		return null;
	};
	
	
	// filter the course based on the days selected
	public List<Course> filterdate(List<Course> input, String dayy) {
		
		return null;
	};
	
	// select those course that are CC
	public List<Course> filterCC(List<Course> input) {
		return null;
	};
	
	// select those course that have no exclusion
	public List<Course> filterEX(List<Course> input) {
		return null;
	};
	
	
	// select those course that have labs or tutorials
	public List<Course> filterLab(List<Course> input) {
		return null;
	};

}
