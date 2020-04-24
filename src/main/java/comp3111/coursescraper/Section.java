package comp3111.coursescraper;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import javafx.scene.control.CheckBox;

public class Section {
	
	private String sectionId;		// 1808 refers to "COMP 1029J L1"
	private String sectionCode;	// L1, L2, LA1, T2
	private int numSlots;
	private boolean invalidFlag;
	static private int numSections;
	
	// handle enrol box
	private CheckBox enrolled;
	private boolean enrollment;
	
	public Section() {
		numSlots = 0;
		invalidFlag = true;
		
		// handle enroll box
		this.enrolled = new CheckBox();
		enrollment = false;
		
	}
	
	public Section(HtmlElement e) {
		numSlots = 0;
		//handle enrol box
		this.enrolled = new CheckBox();
		enrollment = false;
		
		String[] sectionInfo = e.getChildNodes().get(1).asText().split(" ");
		sectionCode = sectionInfo[0];
		sectionId = sectionInfo[1].substring(1,5);
//		System.out.println("substring: " + sectionInfo[0]);
//		System.out.println( sectionInfo[1]);
		if (sectionCode.substring(0,1).equals("T") || 
				sectionCode.substring(0,2).equals("LA") ||
				sectionCode.substring(0,1).equals("L")) {
			invalidFlag = true;
		} else {
			invalidFlag = false;
		}	
	}
	
	/**
	 * @return whether the section is lecture, tutorial or lab
	 */
	public boolean validSection() {
		return invalidFlag;
	}
	
	/*
	 * increase number of slot(s) in a section
	 */
	public static void incrementNumSections() {
		numSections++;
	}
	
	/**
	 * @return number of slot(s) in the section
	 */
	public static int getNumSections() {
		return numSections;
	}
	
	/*
	 * reset static variable numSections 
	 */
	public static void resetNumSections() {
		numSections = 0;
	}
	
	// handle the clicking of enrolled box in list tab
	public CheckBox getSelect() {
		return enrolled;
	}
	
	// flage to state whether the section has been enrolled or not
	public void setSelect(CheckBox select) {
		this.enrolled = select;
		if (enrolled.isSelected())
			enrollment = true;
		else 
			enrollment = false;
	}
	
 }
