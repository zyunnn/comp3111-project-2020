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
		/* to indicate whether we need the section information about AM or PM of the classes or not
		 * indicate by time flag
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
    	
    	int date_flag = 0;
		int[] day_ff = new int[6];
		Arrays.fill(day_ff, 0);
    	
		//
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
  	
    	List<Course> temp = input;
    	List<Course> cour_re = new ArrayList<Course>();
    	
    	for (Course curr_input:temp) {
    		if(curr_input.getNumSlots() == 0) {
    			continue;
    		}
			Set<String> section_flag = new HashSet<String>();
			Course to_add = curr_input.clone();
			
			if (time_flag >0) {
				Set<String> time_temp = filtertime(curr_input, time_ff);
				if (time_temp.isEmpty()) {
					continue;
				}
				section_flag.addAll(time_temp);
			}
			
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
//			System.out.println("*******************************************************");
//			System.out.println();

        	
           boolean flag_add = true;
//        	if (cc_flag>0) {
//        		if (curr_input.checkcc() == true) {
//        			flag_add = true;
           
//					if (section_flag.isEmpty()) {
//						section_flag.addAll(allsection(curr_input));
//					}
//           		section_flag.retainAll(allsection(curr_input));
//        		}else {
//        			flag_add = false;
//        		}
//        	}
        	
        	if (ex_flag>0) {
        		if (curr_input.getExclusion().isEmpty()) {
        			flag_add = false;
        		}else {
        			flag_add = true;
        			if (section_flag.isEmpty()) {
        				section_flag.addAll(allsection(curr_input));
        			}else {
        				section_flag.retainAll(allsection(curr_input));
        			}
        		}
        	}
        	
        	if (lab_flag>0) {
        		if (checklab(curr_input).isEmpty()) {
        			flag_add = false;
        		}else {
        			flag_add = true;
        			if (section_flag.isEmpty()) {
        				section_flag.addAll(checklab(curr_input));
        			}else {
        				section_flag.retainAll(checklab(curr_input));
        			}
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
	
	
	public Set<String> checklab(Course in) {
		Set<String> ans = new HashSet<String>();
		for (int i = 1; i<in.getNumSlots();i++) {
			if (in.getSlot(i).getSectionCode().contains("LA")||in.getSlot(i).getSectionCode().contains("T")) {
				ans.add(in.getSlot(i).getSectionCode());
			}
		}
		if (ans.isEmpty()) {
			return new HashSet<String>();
		}else {
			return ans;
		}
	}
	
	
	public Set<String> filtertime(Course input_c, boolean [] arr) {
//		System.out.println("captured by filter time");
		boolean AMM = arr[0];
		boolean PMM = arr[1];

		Set<String> section_flag = new HashSet<String>();
		Set<String> section_AMPM = new HashSet<String>();
		Set<String> section_AM = new HashSet<String>();
		Set<String> section_PM = new HashSet<String>();
//		System.out.println(input_c.getTitle());
		
			// get the related section code
		for (int j = 0; j < input_c.getNumSlots();j++) {
			Slot curr_slot = input_c.getSlot(j);
			
			if ((curr_slot.getStartHour()<12)&&(curr_slot.getEndHour()>=12)) {
				section_AMPM.add(curr_slot.getSectionCode());
			}
			
			if	((curr_slot.getStartHour()<12)) {
				section_AM.add(curr_slot.getSectionCode());
			}
			
			if	((curr_slot.getStartHour()>=12)) {
				section_PM.add(curr_slot.getSectionCode());
			}
		}
		
		if((AMM==true) && (PMM==true)) {
			Set<String> intersection = new HashSet<String>(section_AM);
			intersection.retainAll(section_PM);
			section_flag.addAll(section_AMPM);
			section_flag.addAll(intersection);
//			System.out.println("1");
		}else if ((AMM == true) && (PMM == false)){
			section_flag.addAll(section_AM);
//			System.out.println("2");
			if(section_flag.isEmpty()) {
//				System.out.println("error");
			}

		}else if ((AMM == false) && (PMM == true)) {
			section_flag.addAll(section_PM);
		}
		return section_flag;
	}
	
	public Set<String> filterday(Course input_c, int [] arr) {
//		System.out.println("captured3");
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
//				System.out.println("em tue");

			}else {
				section_flag.retainAll(section_Tue);
//				System.out.println("pre tue");

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
//				System.out.println("captured3222222222");
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
			}
		}		

//		System.out.println(input_c.getTitle() + "    has    " + section_flag);


		return section_flag;
	}
	
	public Set<String> allsection (Course c){
		Set<String> ans = new HashSet<String>();
		for (int i = 1;i<c.getNumSlots();i++) {
			ans.add(c.getSlot(i).getSectionCode());
		}
		return ans;
	}

	// end of the class
};	


