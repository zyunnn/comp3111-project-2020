

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

public class Courselist {
	private SimpleStringProperty courseCode;
	private SimpleStringProperty sectionCode;
	private SimpleStringProperty courseName;
	private SimpleStringProperty instructor;
	private CheckBox enbox;
	private BooleanProperty checked;
	private int status;
	
	/**
	 * A copy constructor of Courselist object
	 * @return Courselist an object of Courselist class
	 * @param code course code of the Courselist object
	 * @param section section code of the Courselist object
	 * @param name name of the Courselist object
	 * @param instructor instructor name of the Courselist object
	 * @return a Courselist object
	 * */
	public Courselist(String code, String section, String name, String instructor) {
		this.courseCode = new SimpleStringProperty(code);
		this.sectionCode = new SimpleStringProperty (section);
		this.courseName = new SimpleStringProperty (name);
		this.instructor = new SimpleStringProperty (instructor);
		this.checked = new SimpleBooleanProperty(false);
		this.enbox = new CheckBox();
		this.status = 0;
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
	 * @return Nothing
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
        if (courseCode == null) courseCode = new SimpleStringProperty(this, "courseCode");
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
	 * @return Nothing
	 * */
	public void setSectionCode(String sectionC) {
		this.sectionCode.set(sectionC);
	}

	/**
	 * A function to return the sectioncode property of the Courselist object
	 * @return StringProperty section code property of type simple string property
	 * */
	public StringProperty sectionProperty() { 
        if (sectionCode == null) sectionCode = new SimpleStringProperty(this, "section");
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
	 * @return Nothing
	 * */
	public void setCourseName(String courseName) {
		this.courseName = new SimpleStringProperty(courseName);
	}
	
	/**
	 * A function to return the courseName property of the Courselist object
	 * @return StringProperty course name property of type simple string property
	 * */
	public StringProperty courseNameProperty() { 
        if (courseName == null) courseName = new SimpleStringProperty(this, "courseName");
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
	 * @return Nothing
	 * */
	public void setInstructor(String instructor) {
		this.instructor = new SimpleStringProperty(instructor);
	}

	/**
	 * A function to return the instructor property of the Courselist object
	 * @return StringProperty instructor property of type simple string property
	 * */
	public StringProperty instructorProperty() { 
        if (instructor == null) instructor = new SimpleStringProperty(this, "instructor");
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
	 * @return Nothing
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
	 * @return Nothing
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
	 * @return ObservableValue<CheckBox>
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
