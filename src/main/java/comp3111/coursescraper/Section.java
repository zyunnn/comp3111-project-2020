package comp3111.coursescraper;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import javafx.scene.control.CheckBox;

public class Section {
	
	private String sectionId;		// 1808 refers to "COMP 1029J L1"
	private String sectionCode;	// L1, L2, LA1, T2
	private boolean validFlag;
	static private int numSections;
	

	public Section(HtmlElement e) {


		String[] sectionInfo = e.getChildNodes().get(1).asText().split(" ");
		sectionCode = sectionInfo[0];
		sectionId = sectionInfo[1].substring(1,5);
		if (sectionCode.substring(0,1).equals("T") || 
				sectionCode.substring(0,2).equals("LA") ||
				sectionCode.substring(0,1).equals("L")) {
			validFlag = true;
		} else {
			validFlag = false;
		}	
	}
	
	/**
	 * @return whether the section is lecture, tutorial or lab
	 */
	public boolean validSection() {
		return validFlag;
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
	

	/**
	 * @return code of the section
	 */
	public String getSectionCode() {
		return sectionCode;
	}
	
	/**
	 * @return section id
	 */
	public String getSectionId() {
		return sectionId;
	}

 }
