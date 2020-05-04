package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SLOT = 100;
	private static int numCourse = 0;	// for task 1, count only valid course
	private static int allCourse = 0;	// for task 5, includes invalid course
	private String title ; 
	private String description ;
	private String exclusion;



	private Slot [] slots;
	private int numSlots;
	private boolean isCommonCore;
	
	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
	}
	
	@SuppressWarnings("static-access")
	public Course clone() {
		Course one = new Course();
		one.setDescription(this.description);
		one.setExclusion(this.exclusion);
		one.setTitle(this.title);
		one.numCourse = this.getNumCourse();
		one.allCourse = this.getAllCourse();
		return one;
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
	public void resetSlot() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
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
	 * @param the numSlots to set
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	/*
	 * increase number of all courses (including those without a valid section) 
	 */
	public static void incrementAllCourse() {
		allCourse++;
	}

	/**
	 * @return number of all courses (including those without a valid section)
	 */
	public static int getAllCourse() {
		return allCourse;
	}
	
	/*
	 * reset static variable allCourse
	 */
	public static void resetAllCourse() {
		allCourse = 0;
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


	
	/**
	 * @param common core details
	 */
	public void setCommonCore(String commonCore) {
// 		System.out.println(title + "<_____details ------>" + commonCore);
		if (commonCore.contains("Common"))
			this.isCommonCore = true;
		else
			this.isCommonCore = false;
	}
	
	/**
	 * @return if course is a common core
	 */
	public boolean checkCommonCore() {
		return isCommonCore;
	}
	
}
