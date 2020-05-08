

package comp3111.coursescraper;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Represents a row inserted in the tableview CourselistTable
 * A tableview can have many Courselist
 * */
public class Courselist {
	/**
	 * The course code of a course
	 * */
	private final SimpleStringProperty courseCode = new SimpleStringProperty("");
	
	/**
	 * The section code of a section of a course
	 * */
	private final SimpleStringProperty sectionCode= new SimpleStringProperty("");
	
	/**
	 * The name of a course
	 * */
	private final SimpleStringProperty courseName= new SimpleStringProperty("");
	
	/**
	 * The instructor name of a course section
	 * **/
	private final SimpleStringProperty instructor= new SimpleStringProperty("");
	
	/**
	 * The check-box item for enrollment
	 * */
	private CheckBox enbox = new CheckBox();
	
	/**
	 * A boolean flag to whether the enrollment is selected or not
	 * */
	private final BooleanProperty checked= new SimpleBooleanProperty(false);
	
	/**
	 * The status of a course
	 * =0 not enrolled
	 * >0 enrolled
	 * */
	private int status;
	
	/**
	 * A copy constructor of Courselist object
	 * @param code course code of the Courselist object
	 * @param section section code of the Courselist object
	 * @param name name of the Courselist object
	 * @param instructor instructor name of the Courselist object
	 * */
	public Courselist(String code, String section, String name, String instructor) {
		this.courseCode.set(code);
		this.sectionCode.set(section);
		this.courseName.set(name);
		this.instructor.set(instructor);
		this.checked.set(false);
		this.enbox = new CheckBox();
		this.status = 0;
	}
	
	/**
	 * Default Constructor
	 * */
	public Courselist() {

	}

	/**
	 * A function to return the course code of the Courselist object
	 * @return String course code of the course
	 * */
	public String getCourseCode() {
		return courseCode.get();
	}
	
	/**
	 * A function to set the course code of the Courselist object
	 * @param code course code of the course to be set
	 * */
	public void setCourseCode(String code) {
		this.courseCode.set(code);
	}
	
	/**
	 * A function to return the section code of the Courselist object
	 * @return String section code of the course
	 * */
	public String getSectionCode() {
		return sectionCode.get();
	}
	
	/**
	 * A function to return the Coursecode property of the Courselist object
	 * @return StringProperty course code property of simple string property
	 * */
	public StringProperty courseCodeProperty() { 
        return courseCode; 
    }

	/**
	 * A function to return the section code of the Courselist object
	 * @return String section code of the course
	 * */
	public String getSection() {
		return sectionCode.get();
	}

	/**
	 * A function to set the section code of the Courselist object
	 * @param sectionC section code of the course to be set
	 * */
	public void setSectionCode(String sectionC) {
		this.sectionCode.set(sectionC);
	}

	/**
	 * A function to return the sectioncode property of the Courselist object
	 * @return StringProperty section code property of type simple string property
	 * */
	public StringProperty sectionProperty() { 
        return sectionCode; 
    }
	
	/**
	 * A function to return the course name of the Courselist object
	 * @return String course name of the course
	 * */
	public String getCourseName() {
		return courseName.get();
	}

	/**
	 * A function to set the course name of the Courselist object
	 * @param courseName course name of string type
	 * */
	public void setCourseName(String courseName) {
		this.courseName.set(courseName);
	}
	
	/**
	 * A function to return the courseName property of the Courselist object
	 * @return StringProperty course name property of type simple string property
	 * */
	public StringProperty courseNameProperty() { 
        return courseName; 
    }

	/**
	 * A function to return the instructor name of the Courselist object
	 * @return String instructor name of the course
	 * */
	public String getInstructor() {
		return instructor.get();
	}

	/**
	 * A function to set the instructor name of the Courselist object
	 * @param instructor instructor name of string type
	 * */
	public void setInstructor(String instructor) {
		this.instructor.set(instructor);
	}

	/**
	 * A function to return the instructor property of the Courselist object
	 * @return StringProperty instructor property of type simple string property
	 * */
	public StringProperty instructorProperty() { 
        return instructor; 
    }
	
	/**
	 * A function to return the CheckBox object in the Courselist object
	 * @return CheckBox the CheckBox of this Courselist object
	 * */
	public CheckBox getEnroll() {
		return this.enbox;
	}

	/**
	 * A function to set the Enrollment Box of the Courselist object
	 * @param enroll a CheckBox object
	 * */
	public void setEnroll(CheckBox enroll) {
		this.enbox = enroll;
	}

	/**
	 * A function to return the status of the Courselist object
	 * @return int status flag
	 * */
	public int getStatus() {
		return status;
	}

	/**
	 * A function to set the status flag of the Courselist object
	 * @param status status value
	 * */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * A function to return the checked property of the Courselist object
	 * @return BooleanProperty checked property of type simple boolean property
	 * */
    public BooleanProperty checkedProperty() {
        return checked;
      }

	/**
	 * A function to return the observable value of the check box type
	 * @return a ObservableValue list of checkbox type
	 * */
    public ObservableValue<CheckBox> getCheckBox() {
        return new ObservableValue<CheckBox>() {
            @Override
            public void addListener(ChangeListener<? super CheckBox> listener) {
 
            }
 
            @Override
            public void removeListener(ChangeListener<? super CheckBox> listener) {
 
            }
 
            @Override
            public CheckBox getValue() {
                return enbox;
            }
 
            @Override
            public void addListener(InvalidationListener listener) {
 
            }
 
            @Override
            public void removeListener(InvalidationListener listener) {
 
            }
        };
    }
}
