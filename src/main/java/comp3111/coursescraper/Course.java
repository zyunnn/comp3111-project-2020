package comp3111.coursescraper;

/**
 * A course can be identified by a unique course code. COMP3111 and 
 * COMP3111H are two different courses although they are colisted. A
 * course should have information on its title, description, exclusion,
 * common core, slots and number of slots associated with it.
 */

public class Course {
	private static final int DEFAULT_MAX_SLOT = 100;
	private static int numCourse = 0;	// for task 1, count only valid course
	private static int allCourse = 0;	// for task 5, includes invalid course
	private String title ; 
	private String description ;
	private String exclusion;

	private int enrollednum;


	private boolean [] enrollstatus;
	private Slot [] slots;
	private int numSlots;
	private boolean isCommonCore;
	
	/**
	 * Default constructor for Course
	 */
	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		enrollstatus = new boolean[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) enrollstatus[i] = false;
		numSlots = 0;
		enrollednum = 0;
	}
	
	@SuppressWarnings("static-access")
	/**
	 * Copy constructor of Course
	 * */
	public Course clone() {
		Course one = new Course();
		one.setDescription(this.description);
		one.setExclusion(this.exclusion);
		one.setTitle(this.title);
		one.numCourse = this.getNumCourse();
		one.allCourse = this.getAllCourse();
		return one;
	}
	
	/**
	 * Add a new slot the course
	 * @param s		slot to be copied to the course
	 */
	public void setEnrollStatus(int i, boolean status) {
		enrollstatus[i] = status;
		if (status) {
			enrollednum++;
		}
		else if (!status) {
			enrollednum--;
		}
	}
	
	public boolean getEnrollStatus(int i) {
		return enrollstatus[i];
	}
	
	public int getEnrolledNum() {
		return enrollednum;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	/**
	 * Get slot information given its index 
	 * @param i		index of the slot
	 * @return slot corresponding to the given index
	 */
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	/**
	 * Clear and reset all slot information of the course
	 */
	public void resetSlot() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
	}

	/**
	 * @return title of the course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title of the course
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return description of the course
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description	course overview
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return exclusion of the course
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion		exclusion of the course
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return number of slots associated with the course
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * @param numSlots	number of slots associated with the course
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	/**
	 * Increase the count of all courses (include those without a valid section) 
	 */
	public static void incrementAllCourse() {
		allCourse++;
	}

	/**
	 * @return count of all courses (include those without a valid section)
	 */
	public static int getAllCourse() {
		return allCourse;
	}
	
	/**
	 * Reset count of all courses for new search
	 */
	public static void resetAllCourse() {
		allCourse = 0;
	}
	
	/**
	 * Increase the count of valid course (exclude those without a valid section)
	 */
	public static void incrementNumCourse() {
		numCourse++;
	}

	/**
	 * @return count of valid course
	 */
	public static int getNumCourse() {
		return numCourse;
	}
	
	/**
	 * Reset count of valid course for new search
	 */
	public static void resetNumCourse() {
		numCourse = 0;
	}
	
	/**
	 * Indicate whether the course is a common core
	 * @param commonCore	common core details 
	 */
	public void setCommonCore(String commonCore) {
		if (commonCore.contains("Common"))
			this.isCommonCore = true;
		else
			this.isCommonCore = false;
	}
	
	/**
	 * @return if the course is a common core
	 */
	public boolean checkCommonCore() {
		return isCommonCore;
	}
	
}
