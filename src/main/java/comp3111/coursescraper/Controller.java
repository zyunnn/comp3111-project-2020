package comp3111.coursescraper;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.concurrent.Task;


//for handling checkbox
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
/**
 * @author jacky tam
 *
 */
public class Controller  implements Initializable{
	private static List<Course> myCourseList;
	private List<Course> filterCourse;
	private List<String> EnrolledCourse = new ArrayList<String>();
	
	
    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;   
    
    @FXML
    private CheckBox AmBox;
    
    @FXML
    private CheckBox PmBox;
    
    @FXML
    private CheckBox MondayBox;
    
    @FXML
    private CheckBox TuesdayBox;
    
    @FXML
    private CheckBox WednesdayBox;
    
    @FXML
    private CheckBox ThursdayBox;
    
    @FXML
    private CheckBox FridayBox;
    
    @FXML
    private CheckBox SaturdayBox;
    
    @FXML
    private CheckBox CCBox;
    
    @FXML
    private CheckBox NExclBox;
    
    @FXML
    private CheckBox LabBox;
    
    @FXML
    private Button SelectALL;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;
    
    @FXML
    private Button buttonAllSubjectSearch;
    
    @FXML
    private Button buttonDisplay;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;

    @FXML
    private TableView<Courselist> CourseListTable;
    
    @FXML
    private TableColumn<Courselist, String> courseCode;

    @FXML
    private TableColumn<Courselist, String> sectionCode;

    @FXML
    private TableColumn<Courselist, String> courseName;

    @FXML
    private TableColumn<Courselist, String> instructor;

    @FXML
    private TableColumn<Courselist, Boolean> enrollbox;
    
    @FXML
    void switchToList() {
    	handleBox();
    }
    
    @FXML
    void handleSelectAll() {
    	if (SelectALL.getText().equals("Select All")) {
    		SelectALL.setText("De-select All");

        	AmBox.setSelected(true);
        	PmBox.setSelected(true);
        	MondayBox.setSelected(true);
        	TuesdayBox.setSelected(true);
        	WednesdayBox.setSelected(true);
        	ThursdayBox.setSelected(true);
        	FridayBox.setSelected(true);
        	SaturdayBox.setSelected(true);
        	CCBox.setSelected(true);
        	NExclBox.setSelected(true);
        	LabBox.setSelected(true);
    		handleBox();
    	}else {
    		SelectALL.setText("Select All");
        	AmBox.setSelected(false);
        	PmBox.setSelected(false);
        	MondayBox.setSelected(false);
        	TuesdayBox.setSelected(false);
        	WednesdayBox.setSelected(false);
        	ThursdayBox.setSelected(false);
        	FridayBox.setSelected(false);
        	SaturdayBox.setSelected(false);
        	CCBox.setSelected(false);
        	NExclBox.setSelected(false);
        	LabBox.setSelected(false);
    		handleBox();
    	}
    };
    
    private ObservableList<Courselist> tblist = FXCollections.observableArrayList();
    
    @FXML
    void enrollmentUpdate() {
    	
//    	if (filterCourse == null) {
//    		tblist.clear();
//    		tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
//    		return;
//    	}
    	/*
    	 * store the list of enrolled course
    	 * */
    	for (int i =0;i< CourseListTable.getItems().size();i++) {
    		String courseIDString = CourseListTable.getItems().get(i).getCourseCode() + "--" + CourseListTable.getItems().get(i).getSection();
    		if (CourseListTable.getItems().get(i).getEnroll().isSelected()) {
    			if(EnrolledCourse.contains(courseIDString) == false) {
    				EnrolledCourse.add(courseIDString);
    			}
    		}else {
    			if(EnrolledCourse.contains(courseIDString) == true) {
    				EnrolledCourse.remove(courseIDString);
    			}
    		}
    	}   
    }
    
    void updateList() {
    	this.tblist.clear();
//    	textAreaConsole.setText("enter");

//    	if (filterCourse == null) {
//    		tblist.clear();
//    		tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
//    		return;
//    	}
    	
    	for (Course curr : myCourseList) {
    		Set<String> checkedID = new HashSet<String>();
    		String [] titleL = curr.getTitle().split("\\ -");
    		
    		for(int i=0;i<curr.getNumSlots();i++) {
    			Courselist currCour = new Courselist(titleL[0], curr.getSlot(i).getSectionCode(), titleL[1], curr.getSlot(i).getInstructor());
    			String CourRefID = titleL[0] + "--" + curr.getSlot(i).getSectionCode();
//    			textAreaConsole.setText(CourRefID+"2222222222222222222222");
    			
    			if (EnrolledCourse.contains(CourRefID)) {
    				currCour.getEnroll().setSelected(true);
    			}
    			
    	    	for(int k = 0; k < EnrolledCourse.size(); k++) {
    	    		System.out.println(EnrolledCourse.get(k));
    	    	}
    			/* if this course section has not been added to the table list
    			 * add the course section the checkedID reference set
    			 * and add this course section to the table list to show it
    			 */
    			if(checkedID.contains(CourRefID) == false) {
    				checkedID.add(CourRefID);
    				tblist.add(currCour);
    			}    			     			   			
    		}
    		
    	}
    	courseCode.setEditable(false);
    	sectionCode.setEditable(false);
    	courseName.setEditable(false);
    	instructor.setEditable(false);
    	enrollbox.setEditable(true);
    	

    	
    	return;
    	
    }
    
    @FXML
    void handleBox() {
    	textAreaConsole.clear();
    	// intitate the filter checklist and pass to the filter class to process
//    	filterCourse = myCourseList;
//    	filterCourse.clear();
    	Filter filterEN = new Filter();
    	Boolean []CBList = new Boolean[11];
    	Arrays.fill(CBList, Boolean.FALSE);
    	int filter_flag = 0;

    	
    	if (AmBox.isSelected()) {
    		CBList[0] = true;
    		filter_flag = 1;
    		System.out.println("captured");
    	}
    	if (PmBox.isSelected()) {
    		CBList[1] = true;
    		filter_flag = 1;
    	}
    	if (MondayBox.isSelected()) {
    		CBList[2] = true;
    		filter_flag = 1;
    	}
    	if (TuesdayBox.isSelected()) {
    		CBList[3] = true;
    		filter_flag = 1;
    	}
    	if (WednesdayBox.isSelected()) {
    		CBList[4] = true;
    		filter_flag = 1;
    	}
    	if (ThursdayBox.isSelected()) {
    		CBList[5] = true;
    		filter_flag = 1;
    	}
    	if (FridayBox.isSelected()) {
    		CBList[6] = true;
    		filter_flag = 1;
    	}
    	if (SaturdayBox.isSelected()) {
    		CBList[7] = true;
    		filter_flag = 1;
    	}
    	if (CCBox.isSelected()) {
    		CBList[8] = true;
    		filter_flag = 1;
    	}
    	if (NExclBox.isSelected()) {
    		CBList[9] = true;
    		filter_flag = 1;
    	}
    	if (LabBox.isSelected()) {
    		CBList[10] = true;
    		filter_flag = 1;
    	}
    	
//    	if(filter_flag == 1) {
//    		filterCourse = filterEN.call_filter(CBList, myCourseList);
//    	}else {
//    		filterCourse = myCourseList;
//    	}
    	
    	// print text on console after filtering
//    	for (Course c:filterCourse) {
//    		String newline = c.getTitle() + "\n";
//    		for (int i = 0;i<c.getNumSlots();i++) {
//    			Slot curr_slot = c.getSlot(i);
//    			newline += "Section " + curr_slot.getSectionCode() + " Slot " + i + ":" + curr_slot.toString()+ "\n";
//    		}
//    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
//    	} 	
    	enrollmentUpdate();
    	updateList();
    	

    	return;
    }

    
    private Scraper scraper = new Scraper();
    
    
    @FXML
    void allSubjectSearch() {
    	// Debugging purpose
//    	System.out.println("Enter function");
    	
    	buttonSfqEnrollCourse.setDisable(false);
    	progressbar.setStyle("");		// Reset progress bar for new search
		Course.resetNumCourse();		// Reset number of courses for new search
		Section.resetNumSections();		// Reset number of unique sections for new search
		Instructor.resetAllNameList();	// Reset instructor list for new search
		textAreaConsole.setText("");	// Clear console
    	
    	// Get number of code prefix
    	List<String> subjects = scraper.scrapeAllSubject(textfieldURL.getText(), textfieldTerm.getText());
    	int totalSubjectCount = subjects.size();
//    	int totalSubjectCount = Course.getAllCourse();
    	textAreaConsole.setText("Total Number of Categories/Code Prefix: " + totalSubjectCount);
//    	buttonAllSubjectSearch.setText("Click again to display all courses");
    	
    	// Display result on click event
    	buttonDisplay.setOnAction(e -> {
//    		if (buttonAllSubjectSearch.getText().equals("Click again to display all courses")) {
        	final Task<Void> allSearchThread = new Task<Void>() {
        		
        		@Override
        		protected Void call() throws Exception {
    		    	
    		    	int subjectCount = 0;
//    		    	int courseCount = 0;
    		    	
    		    	for (String subject : subjects) {
    		    		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);
    		    		myCourseList = v;
    		    		String newline = "";
    		        	for (Course c : v) {
    		        		newline += "\n" + c.getTitle() + "\n";
    		        		for (int i = 0; i < c.getNumSlots(); i++) {
    		        			Slot t = c.getSlot(i);
    		        			newline += "Section " + t.getSectionCode() + "\tSlot " + (i+1) + ":" + t + "\n";
    		        		}
    		        	}
    		        	textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		    		subjectCount++;
//    		    		courseCount += v.size();
    		    		updateProgress(subjectCount+1, totalSubjectCount);
    		    		System.out.println("Subject " + subject + " is done.");
    		    	}
    		    	// courseCount
    		    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of Courses fetched: " + Course.getAllCourse());
    		    	return null;
        		}
        	};
        	// Use Java thread for scraping and update 
        	Thread thread = new Thread(allSearchThread, "scrape-thread");
        	progressbar.progressProperty().bind(allSearchThread.progressProperty());
        	thread.setDaemon(true);
        	thread.start();        	
        	
        	// Color the bar green when scraping is complete
        	progressbar.progressProperty().addListener(observable -> {
        		if (progressbar.getProgress() == 1) {
        			progressbar.setStyle("-fx-accent: forestgreen;");
//        			thread.interrupt();
//        			System.out.println("Thread interrupted" + thread.isInterrupted());
        		}
        	});
//    			}
//    		buttonAllSubjectSearch.setText("All Subject Search");
        	// Debugging purpose
    		System.out.println("Finished subject search");
    		});
    	}

    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
		courseCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseCode"));
    	sectionCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("section"));
    	courseName.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseName"));
    	instructor.setCellValueFactory(new PropertyValueFactory<Courselist,String>("instructor"));
    	enrollbox.setCellValueFactory(new PropertyValueFactory<Courselist,Boolean>("enroll"));
    	
    	tblist.clear();
    	tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
    	CourseListTable.setItems(tblist);
    	
	}
    
    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

	@FXML
    void search() {
		buttonSfqEnrollCourse.setDisable(false);
		
		Course.resetNumCourse();		// Reset number of courses for new search
		Section.resetNumSections();		// Reset number of unique sections for new search
		Instructor.resetAllNameList();	// Reset instructor list for new search
		textAreaConsole.setText("");	// Clear console
		
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
  
    	// Handle error 404
    	if (v == null) {
    		textAreaConsole.setText("Error scraping page " + textfieldURL.getText());
    	}
    	else if (v.size() == 1 && v.get(0).getTitle().substring(0,3).equals("404"))  {
    		textAreaConsole.setText("Invalid URL: Page not found");
    	}
    	else {
    		// Get search result statistics
    		myCourseList = v;
    		String info1 = "Total number of different Sections in this search: " + Section.getNumSections() + "\n";
    		String info2 = "Total number of Courses in this search: " + Course.getNumCourse() + "\n";
    		String info3 = "Instructor who has teaching assignment this term but does not need to teach at Tu 3:10pm: \n";
    		List<String> nameList = Instructor.getInstructorNoTu1510();
    		for (String name : nameList) {
    			info3 += name + "\n";
    		}
    		textAreaConsole.setText(info1 + info2 + info3);
    		
        	for (Course c : v) {
        		String newline = c.getTitle() + "\n";
//        		System.out.println("Number slots" + c.getNumSlots());
        		for (int i = 0; i < c.getNumSlots(); i++) {
        			Slot t = c.getSlot(i);
        			newline += "Section " + t.getSectionCode() + "\tSlot " + (i+1) + ":" + t + "\n";
        		}
        		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
        	}
    	}	
    }
	

	


}
