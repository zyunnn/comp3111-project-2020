package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.scene.control.CheckBox;


/**
* A class to filter the course based on certain criteria
* @version 1.0
* @author Tam Wui Wo
* @
*/
public class Filter {


	/*
	 *  CBList[0] = AmBox;
     *  CBList[1] = PmBox;
     *  CBList[2] = MondayBox;
     *  CBList[3] = TuesdayBox;
     *  CBList[4] = WednesdayBox;
     *  CBList[5] = ThursdayBox;
     *  CBList[6] = FridayBox;
     *  CBList[7] = SaturdayBox;
     *  CBList[8] = CCBox;
     *  CBList[9] = NExclBox;
     *  CBList[10] = LabBox;
	 * */
	
	/**
	 * A function to filter the course based on the buttons status in the filter tab
	 * @return List<Course> list of filtered course
	 * @param CBList list of boolean flag indicate the checkbox is clicked or not
	 * @param input list of course scraped 
	 */
	public List<Course> call_filter (Boolean [] CBList, List<Course> input) {		
       	/*
       	 * temp: a list of course type to store the input course list
       	 * cour_re: a list of course type to store the return course list
       	 * */
    	List<Course> temp = input;
    	List<Course> cour_re = new ArrayList<Course>();
		
		/*
		 * time flag: To indicate whether we need the section information about AM or PM of the classes or not
		 * 0 means NO
		 * 1 means YES
		 * 
		 * time_ff[0] indicates whether AM time is needed
		 * time_ff[1] indicates whether PM time is needed
		 */
		int time_flag = 0;
		boolean[] time_ff = new boolean[2];
		Arrays.fill(time_ff, Boolean.FALSE);
    	if((CBList[0]==true) && (CBList[1]==true)) {
    		time_ff[0] = true;
    		time_ff[1] = true;
    		time_flag = 2;
    	}else if (CBList[0]==true && CBList[1]==false) {
    		time_ff[0] = true;
    		time_ff[1] = false;
    		time_flag = 3;
    	}else if (CBList[0]==false && CBList[1]==true){
    		time_ff[0] = false;
    		time_ff[1] = true;
    		time_flag = 4;
    	}
    	
    	
    	/*
    	 * date_flag: a flag indicate whether any of the day checkbox is selected or not
    	 * 0 means NO
		 * 1 means YES
		 * 
		 * transform the boolean flag into integer flags indicate the selected day box
		 * day_ff[] array of integer flag, with initial value of 0 for the boxes
		 * if the a certain day is selected, CBList[i] is true, then store the integer flag in day_ff[]
		 * with values not equal to 0
    	 * */
    	int date_flag = 0;
		int[] day_ff = new int[6];
		Arrays.fill(day_ff, 0);
    	
    	if (CBList[2]==true) {
    		day_ff[0] = 11;
    		date_flag =1;    	
    	}
    	if (CBList[3]==true) {
    		day_ff[1] = 12;
    		date_flag =1;
    	}
    	if (CBList[4]==true) {
    		day_ff[2] = 13;
    		date_flag =1;
    	}
    	if (CBList[5]==true) {
    		day_ff[3] = 14;
    		date_flag =1;
    	}
    	if (CBList[6]==true) {
    		day_ff[4] = 15;
    		date_flag =1;
    	}
    	if (CBList[7]==true) {
    		day_ff[5] = 16;
    		date_flag =1;
    	}
    	
       	
    	/*
    	 * cc_flag indicate whether the course filtered is a common core or not
    	 * ex_flag indicate whether the course filtered has exclusion or not
    	 * lab_flag indicate whether the course filtered has lab or tutorial or not
    	 * 0 means NO
		 * 1 means YES
    	 * */
    	int cc_flag = 0;
    	int ex_flag = 0;
    	int lab_flag = 0;
    	
    	if (CBList[8]==true) { cc_flag = 1;}    	
       	if (CBList[9]==true) { ex_flag = 1;}  	
       	if (CBList[10]==true){ lab_flag = 1;}
    	   	
       	/*
       	 * a for loop to loop through all the courses and slots from input
       	 * if any slots fulfill the selected filter requirements, create a new course object
       	 * to store the selected courses with slots fulfill the filters.
       	 * */
    	for (Course curr_input:temp) {
    		/*
    		 * if the current course with 0 slots, skip the current iteration
    		 * */
    		if(curr_input.getNumSlots() == 0) {
    			continue;
    		}
    		
    		/*
    		 * section_flag: store all the section code fulfill the filters
    		 * to_add: a new object of course type to store the current course information
    		 * */
			Set<String> section_flag = new HashSet<String>();
			Course to_add = curr_input.clone();
			
			/*
			 * time_flag > 0 means either AM or PM or both is selected
			 * Call filtertime(curr_input, time_ff) to return all the section code fulfill the
			 * days selected, store the result in time_temp
			 * 
			 * Then store the time_temp results into the section_flag list for return
			 * */
			if (time_flag >0) {
				Set<String> time_temp = filtertime(curr_input, time_ff);
				if (time_temp.isEmpty()) {
					continue;
				}
				section_flag.addAll(time_temp);
			}
			
			/*
			 * time_flag > 0 means there is at least one checkbox for day is selected
			 * Call filtertime(curr_input, day_ff) to return all the section code fulfill the
			 * days selected, store the result in date_temp
			 * 
			 * If date_temp not empty, carry out AND logic with the current result in section_flag list 
			 * for return
			 * */
			if(date_flag >0) {
				Set<String> date_temp = filterday(curr_input, day_ff);
				if (date_temp.isEmpty()) {
					continue;
				}
				if(section_flag.isEmpty()) {
					section_flag.addAll(date_temp);
				}else {
					section_flag.retainAll( date_temp);
				}
			}
        	
			
           boolean flag_add = true;
           boolean flag_add_cc = true;
           boolean flag_add_ex = true;
           boolean flag_add_lab = true;
        	if (cc_flag>0) {
        		if (curr_input.checkCommonCore() == true) {
        			flag_add_cc = true;
        		}else {
        			flag_add = false;
        		}
        	}
        	
        	if (ex_flag>0) { 
        		if (curr_input.getExclusion().equals("null") == false) {
        			flag_add_ex = false; 
        		}else {
        			flag_add_ex = true;
        		}
        	}
        	
        	if (lab_flag>0) { 
        		if (checklab(curr_input) == false) {
        			flag_add_lab = false;
        		}else {
        			flag_add_lab = true;
        		}
        	}
        	
        	if(cc_flag>0 && !flag_add_cc) {
        		flag_add = false;
        	}
        	
        	if(ex_flag>0 && !flag_add_ex) {
        		flag_add = false;
        	}
        	
        	if(lab_flag>0&& !flag_add_lab) {
        		flag_add = false;
        	}
        	
        	if(flag_add) {
    			if (section_flag.isEmpty()) {
    				section_flag.addAll(allsection(curr_input));
    			}else {
    				section_flag.retainAll(allsection(curr_input));
    			}
        	}
        	
			if(section_flag.isEmpty() == false) {
	        	for (int i = 0; i<curr_input.getNumSlots();i++) {
					Slot curr_slot = curr_input.getSlot(i);
					if (section_flag.contains(curr_slot.getSectionCode())) {
						to_add.addSlot(curr_slot);
					}
	        	}
			}
        	
        	if (flag_add&& (section_flag.isEmpty() == false)) {
        		cour_re.add(to_add);
        	}
    	}   
    	return cour_re;
	}
	
	/**
	 * A function to check if the current course contain any laboratory or tutorial sections
	 * @param in an object of class course
	 * @return boolean a boolean value indicate the existence of section code starts with "LA" or "T"
	 * */
	public boolean checklab(Course in) {
		
		for (int i = 0; i<in.getNumSlots();i++) {
			if (in.getSlot(i).getSectionCode().contains("LA")||in.getSlot(i).getSectionCode().contains("T")) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * A function to check if the current course fulfill the AM or/and PM filters
	 * @param input_c an object of class course
	 * @param arr an array contained the flags indicate if AM or PM box is either or both selected
	 * @return Set<String> a set of string contained all the section code of the input course fulfill the filters
	 * */
	public Set<String> filtertime(Course input_c, boolean [] arr) {
		boolean AMM = arr[0];
		boolean PMM = arr[1];

		Set<String> section_flag = new HashSet<String>();
		Set<String> section_AMPM = new HashSet<String>();
		Set<String> section_AM = new HashSet<String>();
		Set<String> section_PM = new HashSet<String>();
		
			// get the related section code
		for (int j = 0; j < input_c.getNumSlots();j++) {
			Slot curr_slot = input_c.getSlot(j);
			
			if ((curr_slot.getStartHour()<12)&&(curr_slot.getEndHour()>=12)) {
				section_AMPM.add(curr_slot.getSectionCode());
			}
			
			if	((curr_slot.getStartHour()<12)) {
				section_AM.add(curr_slot.getSectionCode());
			}
			
			if	((curr_slot.getEndHour()>=12)) {
				section_PM.add(curr_slot.getSectionCode());
			}
		}
		
		if((AMM==true) && (PMM==true)) {
			Set<String> intersection = new HashSet<String>(section_AM);
			intersection.retainAll(section_PM);
			section_flag.addAll(section_AMPM);
			section_flag.addAll(intersection);
		}else if ((AMM == true) && (PMM == false)){
			section_flag.addAll(section_AM);
		}else if ((AMM == false) && (PMM == true)) {
			section_flag.addAll(section_PM);
		}
		return section_flag;
	}
	
	/**
	 * A function to check if the current course fulfill the day filters
	 * @param input_c an object of class course
	 * @param arr an array contained the flags indicate if any of the day checkbox selected
	 * @return Set<String> a set of string contained all the section code of the input course fulfill the filters
	 * */
	public Set<String> filterday(Course input_c, int [] arr) {
		int flag_Mon  = arr[0];
		int flag_Tue  = arr[1];
		int flag_Wed  = arr[2]; 
		int flag_Thur = arr[3];
		int flag_Fri  = arr[4];
		int flag_Sat  = arr[5];

		Set<String> section_flag = new HashSet<String>();
		
		Set<String> section_Mon  = new HashSet<String>();
		Set<String> section_Tue  = new HashSet<String>();
		Set<String> section_Wed  = new HashSet<String>();
		Set<String> section_Thur = new HashSet<String>();
		Set<String> section_Fri  = new HashSet<String>();
		Set<String> section_Sat  = new HashSet<String>();
		// get the related section code
		for (int j = 0; j < input_c.getNumSlots();j++) {
			Slot curr_slot = input_c.getSlot(j);			
			if (curr_slot.getDay() == 0) {
				section_Mon.add(curr_slot.getSectionCode());
			}
			
			if (curr_slot.getDay() == 1) {
				section_Tue.add(curr_slot.getSectionCode());
			}
			
			if (curr_slot.getDay() == 2) {
				section_Wed.add(curr_slot.getSectionCode());
			}
			
			if (curr_slot.getDay() == 3) {
				section_Thur.add(curr_slot.getSectionCode());
			}
			
			if (curr_slot.getDay() == 4) {
				section_Fri.add(curr_slot.getSectionCode());
			}
			
			if (curr_slot.getDay() == 5) {
				section_Sat.add(curr_slot.getSectionCode());
			}
		}
		
		// finalize the section_flag by implementing the AND Logic
		if (flag_Mon>0) {
			if (section_Mon.isEmpty()) {
				return new HashSet<String>();
			}
			section_flag = section_Mon;
		}
		
		if (flag_Tue >0) {
			if (section_Tue.isEmpty()) {
				return new HashSet<String>();
			}
			if (section_flag.isEmpty()) {
				section_flag.addAll(section_Tue);
			}else {
				section_flag.retainAll(section_Tue);
				// if the intersect set gives empty means there is not intersection, return it
				if (section_flag.isEmpty()) {
					return new HashSet<String>();
				}
			}
		}
			
		if (flag_Wed >0) {
			if (section_Wed.isEmpty()) {
				return new HashSet<String>();
			}
			if (section_flag.isEmpty()) {
				section_flag.addAll(section_Wed);
			}else {
				section_flag.retainAll(section_Wed);
				// if the intersect set gives empty means there is not intersection, return it
				if (section_flag.isEmpty()) {
					return new HashSet<String>();
				}
			}
		}
		
		if (flag_Thur >0) {
			if (section_Thur.isEmpty()) {
				return new HashSet<String>();
			}
			if (section_flag.isEmpty()) {
				section_flag.addAll(section_Thur);
			}else {
				section_flag.retainAll(section_Thur);
				// if the intersect set gives empty means there is not intersection, return it
				if (section_flag.isEmpty()) {
					return new HashSet<String>();
				}
			}
		}
			
		if (flag_Fri >0) {
			if (section_Fri.isEmpty()) {
				return new HashSet<String>();
			}
			if (section_flag.isEmpty()) {
				section_flag.addAll(section_Fri);
			}else {
				section_flag.retainAll(section_Fri);
				// if the intersect set gives empty means there is not intersection, return it
				if (section_flag.isEmpty()) {
					return new HashSet<String>();
				}
			}
		}
		
		if (flag_Sat >0) {
			if (section_Sat.isEmpty()) {
				return new HashSet<String>();
			}
			if (section_flag.isEmpty()) {
				section_flag.addAll(section_Sat);
			}else {
				section_flag.retainAll(section_Sat);
				// if the intersect set gives empty means there is not intersection, return it
				if (section_flag.isEmpty()) {
					return new HashSet<String>();
				}
			}
		}		
		return section_flag;
	}
	
	/**
	 * A function to return all the section code of the course
	 * @return Set<String> a set of string contains all the section code of the input course c 
	 * @param c a object of course type to select all the section codes
	 * */
	public Set<String> allsection (Course c){
		Set<String> ans = new HashSet<String>();
		for (int i = 0;i<c.getNumSlots();i++) {
			ans.add(c.getSlot(i).getSectionCode());
		}
		return ans;
	}

	// end of the class
};	



