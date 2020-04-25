package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	private static int numCourse = 0;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private Section[] sections;
	private Slot [] slots;
	private int numSlots;
	
	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	/*
	 * increase number of valid course by 1 
	 */
	public static void incrementNumCourse() {
		numCourse++;
	}

	/**
	 * @return number of courses
	 */
	public static int getNumCourse() {
		return numCourse;
	}
	
	/*
	 * reset static variable numCourse
	 */
	public static void resetNumCourse() {
		numCourse = 0;
	}
	
}
