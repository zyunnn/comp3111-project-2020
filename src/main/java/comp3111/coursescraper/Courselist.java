

package comp3111.coursescraper;

import javafx.beans.Observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Courselist {
	private SimpleStringProperty courseCode;
	private SimpleStringProperty sectionCode;
	private SimpleStringProperty courseName;
	private SimpleStringProperty instructor;
	private CheckBox enbox;
	private BooleanProperty checked;
	private int status;
	
	public Courselist(String code, String section, String name, String instructor) {
		this.courseCode = new SimpleStringProperty(code);
		this.sectionCode = new SimpleStringProperty (section);
		this.courseName = new SimpleStringProperty (name);
		this.instructor = new SimpleStringProperty (instructor);
		this.checked = new SimpleBooleanProperty(false);
		this.enbox = new CheckBox();
		this.status = 0;
	}
	
	public String getCourseCode() {
		return courseCode.get();
	}
	
	public void setCourseCode(String code) {
		this.courseCode.set(code);
	}
	
	public String getSectionCode() {
		return sectionCode.get();
	}
	
	public StringProperty courseCodeProperty() { 
        if (courseCode == null) courseCode = new SimpleStringProperty(this, "courseCode");
        return courseCode; 
    }

	public String getSection() {
		return sectionCode.get();
	}

	public void setSectionCode(String sectionC) {
		this.sectionCode.set(sectionC);
	}

	public StringProperty sectionProperty() { 
        if (sectionCode == null) sectionCode = new SimpleStringProperty(this, "section");
        return sectionCode; 
    }
	
	public String getCourseName() {
		return courseName.get();
	}

	public void setCourseName(String courseName) {
		this.courseName = new SimpleStringProperty(courseName);
	}
	
	public StringProperty courseNameProperty() { 
        if (courseName == null) courseName = new SimpleStringProperty(this, "courseName");
        return courseName; 
    }

	public String getInstructor() {
		return instructor.get();
	}

	public void setInstructor(String instructor) {
		this.instructor = new SimpleStringProperty(instructor);
	}

	public StringProperty instructorProperty() { 
        if (instructor == null) instructor = new SimpleStringProperty(this, "instructor");
        return instructor; 
    }
	
	public CheckBox getEnroll() {
		return this.enbox;
	}

	public void setEnroll(CheckBox enroll) {
		this.enbox = enroll;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
    public BooleanProperty checkedProperty() {
        return checked;
      }

	
}
