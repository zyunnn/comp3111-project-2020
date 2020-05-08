package comp3111.coursescraper;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import javafx.scene.control.CheckBox;

/**
 * A section can be identified by a unique section-ID (e.g. 1808 for "COMP1029J L1)
 * There should be at least one section in a course. Within a course, each section 
 * should have a unique section code (L1, L2, LA1, T2). A section should have 
 * information on section-ID and section code.
 */
public class Section {
	
	private String sectionId;		// 1808 refers to "COMP 1029J L1"
	private String sectionCode;		// L1, L2, LA1, T2
	private boolean validFlag;		// Indicate validity of a section
	static private int numSections;
	

	/**
	 * Constructor for a Section. Only lecture, tutorial and lab sections 
	 * are considered as valid section. An invalid section should not have 
	 * a slot as invalid section should be ignored.
	 * @param e		HtmlElement that host section information
	 */
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
	 * Check validity of a section
	 * @return whether the section is lecture, tutorial or lab
	 */
	public boolean validSection() {
		return validFlag;
	}
	
	/**
	 * Increase count of different sections in a search
	 */
	public static void incrementNumSections() {
		numSections++;
	}
	
	/**
	 * @return count of different sections in a search
	 */
	public static int getNumSections() {
		return numSections;
	}
	
	/**
	 * Reset count of different sections in a search
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
	 * @return id of the section
	 */
	public String getSectionId() {
		return sectionId;
	}

 }
