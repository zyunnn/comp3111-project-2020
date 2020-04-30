package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.scene.control.CheckBox;

public class Filter {

//	private String [] filterlist;	
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
	public List<Course> call_filter (Boolean [] CBList, List<Course> input) {
		List<Course> temp = new ArrayList<Course>(input);


		boolean flag = false;
    	if((CBList[0]==true) && (CBList[1]==true)) {
    		temp = new ArrayList<Course>(filtertime(temp, true, true));
    		flag = true;
    		System.out.println("captured");
    	}else if (CBList[0]==true && CBList[1]==false) {
    		temp = new ArrayList<Course>(filtertime(temp, true, false));
    		flag = true;
    	}else if (CBList[0]==false && CBList[1]==true){
    		temp = new ArrayList<Course>(filtertime(temp, false, true));
    		flag = true;
    	}
    	boolean mon_flag = false;
    	boolean tue_flag = false;
    	boolean wed_flag = false;
    	boolean thur_flag = false;
    	boolean fri_flag = false;
    	boolean sat_flag = false;
    	boolean call_filter_date = false;
    	
    	if (CBList[2]==true) {
    		mon_flag = true;
    		call_filter_date = true;
    	}
    	if (CBList[3]==true) {
    		tue_flag = true;
    		call_filter_date = true;

    	}
    	if (CBList[4]==true) {
    		wed_flag = true;
    		call_filter_date = true;

    	}
    	if (CBList[5]==true) {
    		thur_flag = true;
    		call_filter_date = true;

    	}
    	if (CBList[6]==true) {
    		fri_flag = true;
    		call_filter_date = true;

    	}
    	if (CBList[7]==true) {
    		sat_flag = true;
    		call_filter_date = true;

    	}
    	
    	if (call_filter_date == true) {
    		temp = filterdate(temp, mon_flag, tue_flag, wed_flag, thur_flag, fri_flag, sat_flag);
    	}
    	
    	if (CBList[8]==true) {
    		temp = filterCC(temp);
    		flag = true;
    	}
    	
       	if (CBList[9]==true) {
    		temp = filterEX(temp);
    		flag = true;
    	}
       	
       	if (CBList[10]==true) {
    		temp = filterLab(temp);
    		flag = true;
    	}
       	
       	if (flag == true) {
       		return temp;
       	}else {
       		return input;
       	}
    
	}

	// filter the course by whether have am or pm session
	
	public List<Course> filtertime(List<Course> input, boolean AMM, boolean PMM) {
		System.out.println("captured2");

		List<Course> result = new ArrayList<Course>(input);
		for (int i = 1; i < input.size(); i++) {
			Course curr_course = input.get(i);
			Course to_add = new Course();
			to_add.Clone(curr_course);
			Set<String> section_flag = new HashSet<String>();
			Set<String> section_AMPM = new HashSet<String>();
			Set<String> section_AM = new HashSet<String>();
			Set<String> section_PM = new HashSet<String>();
			System.out.println(curr_course.getTitle());
			// get the related section code
			for (int j = 0; j < curr_course.getNumSlots();j++) {
				Slot curr_slot = curr_course.getSlot(j);
				if ((curr_slot.getStartHour()<=12)&&(curr_slot.getEndHour()>12)) {
					section_AMPM.add(curr_slot.getSections());
				}
				if	((curr_slot.getStartHour()<=12)) {
					section_AM.add(curr_slot.getSections());
					System.out.println("captured3332");

				}
				if	((curr_slot.getStartHour()>12)) {
					section_PM.add(curr_slot.getSections());
				}
			}
			
			if(AMM==true&& PMM==true) {
				Set<String> intersection = new HashSet<String>(section_AM);
				intersection.retainAll(section_PM);;
				section_flag.addAll(section_AMPM);
				section_flag.addAll(intersection);
			}else if ((AMM == true) && (PMM == false)){
				section_flag.addAll(section_AM);
			}else if ((AMM == false) && (PMM == true)) {
				section_flag.addAll(section_PM);
			}
	
			// add the section to return result
			for (int j = 0; j < curr_course.getNumSlots();j++) {
				Slot curr_slot = curr_course.getSlot(j);
				if (section_flag.contains(curr_slot.getSections())) {
					to_add.addSlot(curr_slot.clone());
				}
			}
			// add the section 
			result.add(to_add);
		}
		if (result.isEmpty()) {
			return input;
		}else {
			return result;
		}

	};	
	
	
	
	
	
	
	// filter the course based on the days selected

	public List<Course> filterdate(List<Course> input, boolean flag_Mon, boolean flag_Tue, boolean flag_Wed, 
			boolean flag_Thur, boolean flag_Fri, boolean flag_Sat) {
		System.out.println("captured3");

		List<Course> result = new ArrayList<Course>();
		for (int i = 1; i < input.size(); i++) {
			Course curr_course = input.get(i);
			Course to_add = curr_course;
			to_add.resetSlot();	
			Set<String> section_flag = new HashSet<String>();
			Set<String> section_Mon = new HashSet<String>();
			Set<String> section_Tue = new HashSet<String>();
			Set<String> section_Wed = new HashSet<String>();
			Set<String> section_Thur = new HashSet<String>();
			Set<String> section_Fri = new HashSet<String>();
			Set<String> section_Sat = new HashSet<String>();

			// get the related section code
			for (int j = 0; j < curr_course.getNumSlots();j++) {
				Slot curr_slot = curr_course.getSlot(j);
				if (curr_slot.getDay() == 0) {
					section_Mon.add(curr_slot.getSections());
				}
				if (curr_slot.getDay() == 1) {
					section_Tue.add(curr_slot.getSections());
				}
				if (curr_slot.getDay() == 2) {
					section_Wed.add(curr_slot.getSections());
				}
				if (curr_slot.getDay() == 3) {
					section_Thur.add(curr_slot.getSections());
				}
				if (curr_slot.getDay() == 4) {
					section_Fri.add(curr_slot.getSections());
				}
				if (curr_slot.getDay() == 5) {
					section_Sat.add(curr_slot.getSections());
				}

			}
			
			if (flag_Mon == true) {
				section_flag = section_Mon;
			}
			if (flag_Tue == true) {
				if (section_flag.isEmpty()) {
					section_flag = section_Tue;
				}else {
					section_flag.retainAll(section_Tue);
				}
			}
			if (flag_Wed == true) {
				if (section_flag.isEmpty()) {
					section_flag = section_Wed;
				}else {
					section_flag.retainAll(section_Wed);
				}
			}
			if (flag_Thur == true) {
				if (section_flag.isEmpty()) {
					section_flag = section_Thur;
				}else {
					section_flag.retainAll(section_Thur);
				}
			}
			if (flag_Fri == true) {
				if (section_flag.isEmpty()) {
					section_flag = section_Fri;
				}else {
					section_flag.retainAll(section_Fri);
				}
			}
			if (flag_Sat == true) {
				if (section_flag.isEmpty()) {
					section_flag = section_Sat;
				}else {
					section_flag.retainAll(section_Sat);
				}
			}
			
			// add the section to return result
			for (int j = 0; j < curr_course.getNumSlots();j++) {
				Slot curr_slot = curr_course.getSlot(j);
				if (section_flag.contains(curr_slot.getSections())) {
					to_add.addSlot(curr_slot);
				}
			}
			// add the section 
			result.add(to_add);
		}
		
		if (result.isEmpty()) {
			return input;
		}else {
			return result;
		}
		
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

//
//public String testing(String sta2te) {
//	return sta2te;
//}

//public List<Course> filtertime_special(List<Course> input) {
//	for (int i = 1; i < input.size(); i++) {
//		
//
//		Course curr_course = input.get(i);
//		Course to_add = curr_course;
//		to_add.resetSlot();
//		/*get the sections code for the two cases
//		 */
//		for (int j = 1; j < curr_course.getNumSlots();j++) {
//			Slot curr_slot = curr_course.getSlot(j);
//			if ((curr_slot.getStartHour()<=12)&&(curr_slot.getEndHour()>12)) {
//				section_AMPM.add(curr_slot.getSections());
//			}
//			if	((curr_slot.getStartHour()<=12)) {
//				section_AM.add(curr_slot.getSections());
//			}
//			if	((curr_slot.getStartHour()>12)) {
//				section_PM.add(curr_slot.getSections());
//			}
//		}
//		Set<String> intersection = new HashSet<String>(section_AM);
//		intersection.retainAll(section_PM);
//		/* all the sections fulfill the criteria
//		 * */
//		section_AMPM.containsAll(intersection);
//
//		for (int j = 1; j < curr_course.getNumSlots();j++) {
//			Slot curr_slot = curr_course.getSlot(j);
//			if (section_AMPM.contains(curr_slot.getSections())) {
//				to_add.addSlot(curr_slot);
//			}
//		}
//		// add the section 
//		result.add(to_add);
//						
//	}
//	return null;
//}
