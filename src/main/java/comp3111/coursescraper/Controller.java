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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorInput;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import java.util.Map;
import java.util.ArrayList;

/**
 * Controller provides the search, display, filter, enroll, and timetable schedule
 * functions in response to user's input through the GUI.
 * 
 * allSubjectSearch() is implemented for the AllSubjectSearch tab. Clicking the 
 * 					  "All Subject Search" button outputs total number of subjects
 * 					  in the semester. Following that, clicking the "Display Search 
 * 					  Result" button displays all course information for that semester,
 * 					  along with the total number of courses fetched in the search. The
 * 					  progress bar is updated as scraping of one subject is completed. A
 * 					  green progress bar indicates the scraping is finished.
 * 
 * search() is implemented for the Main tab. Course information, instructor without
 * 			teaching assignment on Tuesday 3:10pm should be displayed after clicking 
 * 			the "Search" button. 
 */

public class Controller implements Initializable{
	/**
	 *  A list of Course object recording the scrape result
	 */
	private static List<Course> myCourseList = new ArrayList<Course>();

	/**
	 * A list of Course object contained all the filtered courses
	 * */
	private List<Course> filterCourse;
	
	/**
	 *  A list of Course object recording the course that needed to be drawed on the table
	 */
	private List<Course> drawCourse = new ArrayList<Course>();
	
	/**
	 * A list of string contains course name and section of the enrolled course
	 * */
	private List<String> EnrolledCourse = new ArrayList<String>();
	
	/**
	 * A list of FXML Label objects recording the label drawn on the timetable
	 */
	private List<Label> drawedLabel = new ArrayList<Label>();

	/**
	 * The map recording the corresponding Color object to a course represented by its code
	 */
	private Map<String, Color> map = new HashMap<String, Color>();
	
	/**
	 * A string to contain the text_on_console to be shown in the text console
	 * */
	private String text_on_console;
	
	/**
	 * The tab of 'main' scene
	 */
    @FXML
    private Tab tabMain;

    /**
     * The textfield for input term String in main
     */
    @FXML
    private TextField textfieldTerm;
    
    /**
     * The textfield for input subject String code to search
     */
    @FXML
    private TextField textfieldSubject;

    /**
     * The button to provoke search function
     */
    @FXML
    private Button buttonSearch;

    /**
     * The textfield for the input of url link to the webpage for scraping
     */
    @FXML
    private TextField textfieldURL;

    /**
     * The tab of 'backend' scene
     */
    @FXML
    private Tab tabStatistic;

    /**
     * The combobox of time slot
     */
    @FXML
    private ComboBox<?> comboboxTimeSlot;

    /**
     * The tab of 'filter' scene 
     */
    @FXML
    private Tab tabFilter;   
    
    /**
     * The AmBox Checkbox, to provoke the am condition in filter
     */
    @FXML
    private CheckBox AmBox;
    
    /**
     * The PmBox Checkbox, to provoke the pm condition in filter
     */
    @FXML
    private CheckBox PmBox;
    
    /**
     * The Monday Checkbox, to provoke the Monday condition in filter
     */
    @FXML
    private CheckBox MondayBox;
    
    /**
     * The Tuesday Checkbox, to provoke the Tuesday condition in filter
     */
    @FXML
    private CheckBox TuesdayBox;
    
    /**
     * The Wednesday Checkbox, to provoke the Wednesday condition in filter
     */
    @FXML
    private CheckBox WednesdayBox;
    
    /**
     * The Thurseday Checkbox, to provoke the Thursday condition in filter
     */
    @FXML
    private CheckBox ThursdayBox;
    
    /**
     * The Friday Checkbox, to provoke the Friday condition in filter
     */
    @FXML
    private CheckBox FridayBox;
    
    /**
     * The Saturday Checkbox, to provoke the Saturday condition in filter
     */
    @FXML
    private CheckBox SaturdayBox;
    
    /**
     * The Common core course Checkbox, to provoke the Common core course condition in filter
     */
    @FXML
    private CheckBox CCBox;
    
    /**
     * The no-exclusion Checkbox, to provoke the no-exclusion condition in filter
     */
    @FXML
    private CheckBox NExclBox;
    
    /**
     * The Lab Checkbox, to provoke the Lab condition in filter
     */
    @FXML
    private CheckBox LabBox;
    
    /**
     * The select all button to provoke the event that select all the Checkbox above in the filter scene
     */
    @FXML
    private Button SelectALL;

    /**
     * The tab of 'list' scene
     */
    @FXML
    private Tab tabList;

    /**
     * The tab of 'Timetable' scene
     */
    @FXML
    private Tab tabTimetable;

    /**
     * The tab of 'All subject search' scene
     */
    @FXML
    private Tab tabAllSubject;
    
    /**
     * The button that provokes the event of searching all the subject
     */
    @FXML
    private Button buttonAllSubjectSearch;
    
    /**
     * The button that provokes the event of displaying all the output from the search in the 
     * all subject search
     */
    @FXML
    private Button buttonDisplay;

    /**
     * The progress bar showing the progress of the searching
     */
    @FXML
    private ProgressBar progressbar;

    /**
     * The TextField for the input of url link leading to the website for scraping
     * the sfq score information
     */
    @FXML
    private TextField textfieldSfqUrl;

    /**
     * The button that provokes the event of scraping the sfq score of enrolled courses
     */
    @FXML
    private Button buttonSfqEnrollCourse;

    /**
     * The button that provokes the event of scraping the sfq score of every instructor
     */
    @FXML
    private Button buttonInstructorSfq;

    /**
     * The console on the ui scene
     */
    @FXML
    private TextArea textAreaConsole;

    /**
     * The table view showing the list of courses info,
     * with a checkbox for enroll event.
     * The courses in the list are the scrapted courses filtered
     */
    @FXML
    private TableView<Courselist> CourseListTable;
    
    /**
     * The course code column of the listed courses
     */
    @FXML
    private TableColumn<Courselist, String> courseCode;

    /**
     * The course section code column of the listed courses
     */
    @FXML
    private TableColumn<Courselist, String> sectionCode;

    /**
     * The course name of the listed courses
     */
    @FXML
    private TableColumn<Courselist, String> courseName;

    /**
     * The corresponding instructor of courses in the listed courses
     */
    @FXML
    private TableColumn<Courselist, String> instructor;

    /**
     * The enroll boxes for the listed courses
     */
    @FXML
    private TableColumn<Courselist, CheckBox> enrollbox;
    
    /**
     * Initiate scraper object for method implementation, for scraping tasks
     */
    private Scraper scraper = new Scraper();
    

    
	/**
	 * An initiate function when the list tab is selected
	 * */
    @FXML
    private void switchToList() {
    	handleBox();
    	course_enrollment_start();
    }
    
	/**
	 * A function to select / de-select all the check-boxes when the button "Select All" is clicked
	 * */
    @FXML
    private void handleSelectAll() {
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
    
    /**
     * The observablelist of lists in the table, recording the Course objects
     * scraped and passing the filter
     */
    private ObservableList<Courselist> tblist = FXCollections.observableArrayList(item -> new javafx.beans.Observable[] {item.checkedProperty()});

	/**
	 * A function to print all the enrolled course and filtered course information
	 * */
    private void course_enrollment_start() {
    	enrollmentUpdate();
    	textAreaConsole.clear();
    	textAreaConsole.setText("The following sections are enrolled:" );
    	
    	// print all the enrolled course

    	for (String Cour_ID:EnrolledCourse) {
    		String enrolled_text = Cour_ID;
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + enrolled_text);
    	}
		textAreaConsole.setText(textAreaConsole.getText() + "\n" + "\n" + "The list of courses after filter are:"+ "\n" + text_on_console);   	
    }

    //After handlebox function
	/**
	 * A function to update the enrollment course in the EnrolledCourse list
	 * */
    @FXML
    private void enrollmentUpdate() {
    	
    	if (filterCourse.isEmpty()) {
    		tblist.clear();
    		tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
    		return;
    	}
    	/*
    	 * store the list of enrolled course
    	 * */
    	
    	// Checking and recording the enrollment status in the CourseListTable
    	
    	// Enrollmentupdate would also be called if selected
    	for (int i =0;i< CourseListTable.getItems().size();i++) {
    		String courseIDString = CourseListTable.getItems().get(i).getCourseCode() + "--" + CourseListTable.getItems().get(i).getSection();
    		if (CourseListTable.getItems().get(i).getEnroll().isSelected()) {
    			if(EnrolledCourse.contains(courseIDString) == false) {
    				EnrolledCourse.add(courseIDString);	
    				System.out.println(courseIDString);
    				for(Course curr : filterCourse) {
    		    		String [] titleL = curr.getTitle().split("\\ -"); 
    		    		for (int slotnum = 0; slotnum<curr.getNumSlots(); slotnum++) {
    		    			String CourRefID = titleL[0] + "--" + curr.getSlot(slotnum).getSectionCode();
    		    			//
    		    			if (CourRefID.equals(courseIDString)) {
    		    				boolean newCourse = true;
    		    				for (Course dcurr:drawCourse) {
        		    				// this course is already in drawCourse, probably because we select additional slot
        		    				if (CourRefID.equals( (dcurr.getTitle().split("\\ -")[0] + "--" + curr.getSlot(slotnum).getSectionCode()))){
        		    					newCourse = false;
        		    					drawCourse.remove(dcurr);
        		    					dcurr.setEnrollStatus(slotnum, true);
        		    					drawCourse.add(dcurr);
        		    					System.out.println("old course with new slot is added to drawCourse, which is " + CourRefID);
        		    					System.out.println(dcurr.getSlot(slotnum).getDay());
        		    					break;
        		    				}
    		    				}
    		    				if (newCourse) {
    		    					// this course is not in drawCourse
        		    					curr.setEnrollStatus(slotnum, true);
        		    					drawCourse.add(curr);
        		    					System.out.println("new course added to drawCourse, which is " + CourRefID);
        		    					System.out.println(curr.getSlot(slotnum).getDay());
    		    				}
        		    			
    					}
    				}

    			}
    		}
    		}
    		else {
    			if(EnrolledCourse.contains(courseIDString) == true) {
    				EnrolledCourse.remove(courseIDString);
    				System.out.println(courseIDString);   		
    		    		for(Course dcurr:drawCourse) {
    		    			String [] titleL = dcurr.getTitle().split("\\ -"); 
    		    			for (int slotnum = 0; slotnum<dcurr.getNumSlots(); slotnum++) {
        		    			String CourRefID = titleL[0] + "--" + dcurr.getSlot(slotnum).getSectionCode();
        		    			//
        		    			if (CourRefID.equals(courseIDString)) {
//        		    				boolean newCourse = true;
            		    				// this course is already in drawCourse, probably because we select additional slot

//            		    				newCourse = false;
            		    				dcurr.setEnrollStatus(slotnum, false);
            		    				System.out.println("old course with new slot is removed from drawCourse, which is " + CourRefID + " " + dcurr.getSlot(slotnum).getDay());
            		    				
            		    				if (dcurr.getEnrolledNum() == 0) {
            		    					drawCourse.remove(dcurr);
            		    					System.out.println(dcurr.getTitle() + " is removed");
            		    				}
        		    				}
    		    			}
    		    			
    		    		}
    		    		
				break;

    			}
    		}
    	}   
    }
    
	/**
	 * A function to update the courses shown in the list table based on the filtered and enrolled courses
	 * */
    private void updateList() {
    	this.tblist.clear();

    	if (filterCourse.isEmpty()) {
    		tblist.clear();
    		tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
    		return;
    	}
    	
      
      /* Get num slooooooooooooooooooot!*/
    	for (Course curr : filterCourse) {
    		Set<String> checkedID = new HashSet<String>();
    		String [] titleL = curr.getTitle().split("\\ -"); 
    	
    		for(int i=0;i<curr.getNumSlots();i++) {
    			Courselist currCour = new Courselist(titleL[0], curr.getSlot(i).getSectionCode(), titleL[1], curr.getSlot(i).getInstructor());
    			String CourRefID = titleL[0] + "--" + curr.getSlot(i).getSectionCode();
    			
    			if (EnrolledCourse.contains(CourRefID)) {
    				currCour.getEnroll().setSelected(true);
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
    
	/**
	 * A function initiated when any of the check-box is selected or de-selected.
	 * And it will print out all the filtered courses in the textAreaConsole
	 * */
    @FXML
    private void handleBox() {
    	textAreaConsole.clear();
    	// intitate the filter checklist and pass to the filter class to process
    	filterCourse = new ArrayList<Course>();
    	// Create a filter type object
    	Filter filterEN = new Filter();
    	// A boolean array to record the status of the checkbox
    	// and feel the checkbox with default value FALSE
    	Boolean []CBList = new Boolean[11];
    	Arrays.fill(CBList, Boolean.FALSE);
    	// A flag indicate if we need use the filter or not
    	int filter_flag = 0;

    	// if a checkbox is selected, the corresponding array box will store as true and the filter_flag value increase
    	if (AmBox.isSelected()) {
    		CBList[0] = true;
    		filter_flag ++;
    	}
    	if (PmBox.isSelected()) {
    		CBList[1] = true;
    		filter_flag ++;
    	}
    	
    	if (MondayBox.isSelected()) {
    		CBList[2] = true;
    		filter_flag ++;
    	}
    	
    	if (TuesdayBox.isSelected()) {
    		CBList[3] = true;
    		filter_flag ++;
    	}
    	
    	if (WednesdayBox.isSelected()) {
    		CBList[4] = true;
    		filter_flag ++;
    	}
    	
    	if (ThursdayBox.isSelected()) {
    		CBList[5] = true;
    		filter_flag ++;
    	}
    	
    	if (FridayBox.isSelected()) {
    		CBList[6] = true;
    		filter_flag ++;
    	}
    	
    	if (SaturdayBox.isSelected()) {
    		CBList[7] = true;
    		filter_flag ++;
    	}
    	
    	if (CCBox.isSelected()) {
    		CBList[8] = true;
    		filter_flag ++;
    	}
    	
    	if (NExclBox.isSelected()) {
    		CBList[9] = true;
    		filter_flag++;
    	}
    	
    	if (LabBox.isSelected()) {
    		CBList[10] = true;
    		filter_flag ++;
    	}
    	
    	/*
    	 * If filter_flag >0 means we need to do thte filtering
    	 * we will store the resuls of filtering in filterCourse
    	 * If filter_flag = 0, means we dont need to do the filtering
    	 * we will directly do shallow copy of myCourseList to filterCourse
    	 * */
    	if(filter_flag >0) {
    		filterCourse = filterEN.call_filter(CBList, myCourseList);
    	}else {
    		filterCourse = myCourseList;
    	}
    	text_on_console = "";
    	// print text on console after filtering
    	for (Course c:filterCourse) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0;i<c.getNumSlots();i++) {
    			Slot curr_slot = c.getSlot(i);
    			newline += "Section " + curr_slot.getSectionCode() + " Slot " + i + ":" + curr_slot.toString()+ "\n";
    		}
    		text_on_console += newline + "\n";
    	} 	

   		textAreaConsole.setText( "\n" + text_on_console);
    	enrollmentUpdate();
    	updateList();
    	drawtable();
    	
    	return;
    }
    
    
    @FXML
    private void allSubjectSearch() {
    	// Setup for new search
    	buttonSfqEnrollCourse.setDisable(false);
    	buttonInstructorSfq.setDisable(false);
    	resetCourseList();				// Reset static variable myCourseList
    	progressbar.setStyle("");		// Reset progress bar for new search
		Course.resetNumCourse();		// Reset number of courses for new search
		Section.resetNumSections();		// Reset number of unique sections for new search
		Instructor.resetAllNameList();	// Reset instructor list for new search
		textAreaConsole.setText("");	// Clear console
    	
    	// Get number of code prefix
    	List<String> subjects = scraper.scrapeAllSubject(textfieldURL.getText(), textfieldTerm.getText());
    	int totalSubjectCount = subjects.size();
    	textAreaConsole.setText("Total Number of Categories/Code Prefix: " + totalSubjectCount);
    	
    	// Display result on click event
    	buttonDisplay.setOnAction(e -> {
        	final Task<Void> allSearchThread = new Task<Void>() {
        		
        		@Override
        		protected Void call() throws Exception {
    		    	
    		    	int subjectCount = 0;
    		    	for (String subject : subjects) {
    		    		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);
    		    		String newline = "";
    		        	for (Course c : v) {
    		        		myCourseList.add(c);
    		        		newline += "\n" + c.getTitle() + "\n";
    		        		for (int i = 0; i < c.getNumSlots(); i++) {
    		        			Slot t = c.getSlot(i);
    		        			newline += "Section " + t.getSectionCode() + "\tSlot " + (i+1) + ":" + t + "\n";
    		        		}
    		        	}
    		        	textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		    		subjectCount++;
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
        		}
        	});
    	});
    	
    	// To draw the timetable if the enrollment is not yet implemented
    	if (enrollbox.isEditable()==false) {
    		drawtable();
    	}
    }

	/**
	 * Initialization function to initialize all the table column in the tableview list
	 * and add a listener function to "enroll" checkbox column to capture any change in the selection
	 * status of the "enroll" checkbox column
	 * return Nothing
	 * @param url the link of the course
	 * @param rb the package resources used
	 * */
    @Override
	public void initialize(URL url, ResourceBundle rb) {
		courseCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseCode"));
    	sectionCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("section"));
    	courseName.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseName"));
    	instructor.setCellValueFactory(new PropertyValueFactory<Courselist,String>("instructor"));

    	Callback<TableColumn<Courselist,CheckBox>, TableCell<Courselist,CheckBox>> selectCellFactory =
        new Callback<TableColumn<Courselist,CheckBox>, TableCell<Courselist,CheckBox>>() {
			@Override
            public TableCell<Courselist, CheckBox> call(TableColumn<Courselist, CheckBox> p) {
                TableCell<Courselist, CheckBox> cell = new TableCell<Courselist,CheckBox>() {
                    @Override
                    public void updateItem(CheckBox item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(item);
                        if (item != null) {
                            item.selectedProperty()
                                    .addListener(new ChangeListener<Boolean>() {
                                        @Override
                                        public void changed(
                                                ObservableValue<? extends Boolean> observable,
                                                Boolean oldValue, Boolean newValue) {
                                        		course_enrollment_start();
                                        }
                                    });
                        }
                    }
                };
                return cell;
            }
        };
        enrollbox.setCellFactory(selectCellFactory);
    	enrollbox.setCellValueFactory(new PropertyValueFactory<Courselist,CheckBox>("enroll"));
    	enrollbox.setCellValueFactory(cellData -> cellData.getValue().getCheckBox());

//    	buttonInstructorSfq.setDisable(true);
//		There is no need to disable buttonInstructorSfq since the requirement does not indicate so
    	buttonSfqEnrollCourse.setDisable(true);
    	
    	tblist.clear();
    	tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
  	
    	CourseListTable.setItems(tblist);
	}
    
    /**
     * The function that scrapes the instructor sfq score from the 
     * url link provided in the textfield, and output the instructor name
     * with corresponding sfq score in the console
     */
    @FXML
    public void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(false);
    	
//    	System.out.println("sorry, I am here");
    	
    	String display = "";
    	
    	String sfqurl = textfieldSfqUrl.getText();
    	
    	Hashtable<String,Float> sfqins = scraper.sfqins(sfqurl);
    	
    	System.out.println(sfqins);
    	
    	for(String key : sfqins.keySet()) {
    		if (key.equals("") || key.equals(" ")) continue;
    		else	display = display + "Instructor" + key + " SFQ score : "+ (Float.toString(sfqins.get(key))) + "\n";
    	}
    	
    	
    	textAreaConsole.setText(display);
    }

    /**
     * The function first scrapes all the sfq score of coursesfrom the 
     * url link provided in the textfield. It then compares the enrolled 
     * course with the courses in the key set of recorded dictionary
     * recording the correspondence between course and sfq score. It eventually
     * output the enrolled courseswith corresponding sfq score in the console
     */
    @FXML
    public void findSfqEnrollCourse() {
    	buttonSfqEnrollCourse.setDisable(false);
    	
    	System.out.println("sorry, I am here");
    	
    	String display = "";
    	
    	String sfqurl = textfieldSfqUrl.getText();
    	
    	Hashtable<String,Float> sfqcourse = scraper.sfqcourse(sfqurl);
    	
    	System.out.println(sfqcourse);
    	
    	for (String coursetitle:EnrolledCourse) {
    		String coursecode = coursetitle.split("--")[0];
    		System.out.println(coursecode);
    		if (sfqcourse.keySet().contains(coursecode))
    		display = display + "Course " +coursecode + " SFQ score : "+ (Float.toString(sfqcourse.get(coursecode))) + "\n";
    		else display = display + "Course " + coursecode + " SFQ score : Not founded on the webpage" + "\n";
    	}
    	
    	textAreaConsole.setText(display);
    	
    }

	@FXML
    private void search() {
		// Setup for new search
		buttonSfqEnrollCourse.setDisable(false);
		buttonInstructorSfq.setDisable(false);
		resetCourseList();				// Reset static variable myCourseList 
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
    	
    	// Scrape is successful and copy courses to myCourseList and display the output
    	else {
    		myCourseList = v;
    		
    		// Get search result statistics
    		String info1 = "Total number of different Sections in this search: " + Section.getNumSections() + "\n";
    		String info2 = "Total number of Courses in this search: " + Course.getNumCourse() + "\n";
    		String info3 = "Instructor who has teaching assignment this term but does not need to teach at Tu 3:10pm: \n";
    		
    		// Print instructor info
    		List<String> nameList = Instructor.getInstructorNoTu1510();
    		for (String name : nameList) {
    			info3 += name + "\n";
    		}
    		textAreaConsole.setText(info1 + info2 + info3);
    		
        	for (Course c : myCourseList) {
        		String newline = c.getTitle() + "\n";
        		for (int i = 0; i < c.getNumSlots(); i++) {
        			Slot t = c.getSlot(i);
        			newline += "Section " + t.getSectionCode() + "\tSlot " + (i+1) + ":" + t + "\n";
        		}
        		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
        	}
    	}	
    	
    	// To draw the timetable if the enrollment is not yet implemented
    	if (enrollbox.isEditable()==false) {
    		drawtable();
    	}
    }
	
	/**
	 * Function that reset the course list
	 */
	static void resetCourseList() {
	    myCourseList = new ArrayList<Course>();
	}

	/**
	 * The function first adds the new courses from the enrolled courses list
	 * to the drawcourse list, remove the courses in the drawcourse list but
	 * no longer exist in the enrolled courses list.
	 * Then it draw all the courses in the drawcourse list to the timetable in the 
	 * scene. The courses tiem block with overlapped slot would intersects, resulting
	 * in overlapped block color. The course name and section of the enrolled course
	 * is written on the corresponding block as well.
	 */
	@FXML
	void drawtable() {
//		System.out.println("Can you see me ?");
		if (enrollbox.isEditable()==false) {
			drawCourse.clear();
			if (myCourseList.size()<5) {
				drawCourse = myCourseList;
				System.out.println("Works fine");
			}
			else {
				for(int i = 0; i<5 ; i++) {
					drawCourse.add(myCourseList.get(i));
				}
			}
		}
		// drawCourse.get(0).getSlot(0).getEnd().gethour returns INTEGER value
		
		//TODO Draw the table!
		//Transparency issue done
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();

    
    	for(Label l:drawedLabel) {
    		ap.getChildren().remove(l);
    	}
    	
    	for (Course drawcurr:drawCourse) {
//			if (drawedCourse.contains(drawcurr)) {
//				continue;
	//		}
    		for (int slotnum = 0; slotnum<drawcurr.getNumSlots(); slotnum++) {
    			if (drawcurr.getEnrollStatus(slotnum)==false) {
    				continue;
    			}
    			
    			// I suppose if enrollnum change in a course, it differs
//    			boolean sameslots = false;
//    			for (Course drawedcurr:drawedCourse) {
 //  				if (drawedcurr.getTitle().equals(drawcurr.getTitle())&& drawedcurr.getSlot(slotnum).getSectionCode().equals(drawcurr.getSlot(slotnum).getSectionCode())) {
    //					continue;
    //				}
    //			}

        		String [] titleL = drawcurr.getTitle().split("\\ -");
    			String CourRefID = titleL[0] + "--" + drawcurr.getSlot(slotnum).getSectionCode();
    			
    			if (map.containsKey(CourRefID)==false) {
    	    		Random r = new Random();
//    	        	double start = (r.nextInt(10) + 1) * 20 + 40;
    	        	double color1 = r.nextDouble();
    	        	double color2 = r.nextDouble();
    	        	double color3 = r.nextDouble();
    	        	Color drawcurrColor = Color.color(color1, color2, color3, 0.3);
    	        	map.put(CourRefID, drawcurrColor);
    			}

    			
    			Slot drawcurrSlot = drawcurr.getSlot(slotnum);
    			int drawDay = drawcurrSlot.getDay();
    			int drawStartHour = drawcurrSlot.getStartHour();
    			int drawEndHour = drawcurrSlot.getEndHour();
    			int drawStartMin = drawcurrSlot.getStartMinute();
    			int drawEndMin = drawcurrSlot.getEndMinute();
    			
    			double drawXlayout = (drawDay + 1) * 100;
    			double drawYlayout = (drawStartHour - 7) * 20 + (drawStartMin) /3;
    			
    			double drawWidth = 100;
    			double drawHeight = (drawEndHour - drawStartHour) * 20 + (drawEndMin - drawStartMin + 10) / 3;
    			
    			String drawCode = drawcurr.getTitle().split("\\ -")[0] + "\n" + drawcurr.getSlot(slotnum).getSectionCode();
    			
    			if (drawHeight < 30) {
    				drawCode = drawcurr.getTitle().split("\\ -")[0] + " " + drawcurr.getSlot(slotnum).getSectionCode();
    			}
    			
    			Label drawLabel = new Label(drawCode);
    			drawLabel.setBackground(new Background(new BackgroundFill(map.get(CourRefID), CornerRadii.EMPTY, Insets.EMPTY)));
    			drawLabel.setLayoutX(drawXlayout);
    			drawLabel.setLayoutY(drawYlayout);
    			drawLabel.setMinHeight(drawHeight);
    			drawLabel.setMaxHeight(drawHeight);
    			drawLabel.setMinWidth(drawWidth);
    			drawLabel.setMaxWidth(drawWidth);
    			drawLabel.setTextFill(Color.WHITE);
    			ap.getChildren().add(drawLabel);
    			drawedLabel.add(drawLabel);
    		}
		}
//    		drawedCourse.add(drawcurr);

    	
//    	for (Course drawedcurr:drawedCourse) {
    		// How to delete labels
//    		if (drawCourse.contains(drawedcurr)==false) {
/*    			try {
    				String drawedCode = drawedcurr.getTitle().split("\\ -")[0] + "\n" + drawedcurr.getSlot(0).getSectionCode();
    				for (Label drawedcurrlabel:drawedLabel) {
        				if(drawedcurrlabel.getText().contains(drawedcurr.getTitle().split("\\ -")[0]) || 
        				   drawedcurrlabel.getText().contains(drawedcurr.getSlot(0).getSectionCode())) {
        					ap.getChildren().remove(drawedcurrlabel);
        				}
        			}
    			}
    			catch (Exception e){
    				System.out.println(e);
    				System.out.println("The drawedCode is empty");
    			} */
//   			drawedCourse.remove(drawedcurr);
//    		}
//    	}
    	
    	//Test drawedCourse list
//    	System.out.println("");
//    	for (Course e: drawedCourse) {
//    		System.out.println(e.getTitle() + "is enrolled");
//    	}

	}
	
	
	
	
		/*
		// Add enrolled courses into the timetable as the normal case
		else {
			for (Course curr : filterCourse) {
	    		if AmBox
				Set<String> checkedID = new HashSet<String>();
	    		String [] titleL = curr.getTitle().split("\\ -");
	    		
	    		for(int i=0;i<curr.getNumSlots();i++) {
	    			Courselist currCour = new Courselist(titleL[0], curr.getSlot(i).getSectionCode(), titleL[1], curr.getSlot(i).getInstructor());
	    			String CourRefID = titleL[0] + "--" + curr.getSlot(i).getSectionCode();
//	    			textAreaConsole.setText(CourRefID+"2222222222222222222222");
	    			
	    			if (EnrolledCourse.contains(CourRefID)) {
	    				currCour.getEnroll().setSelected(true);
	    			}
	    			
	    	    	for(int k = 0; k < EnrolledCourse.size(); k++) {
	    	    		System.out.println(EnrolledCourse.get(k));
	    	    	}
	    			

	    			if(checkedID.contains(CourRefID) == false) {
	    				checkedID.add(CourRefID);
	    				tblist.add(currCour);
	    				}    			     			   			
	    			}
			}
		}
	} 

	    void enrollmentUpdate() {
	    	
	    	if (filterCourse == null) {
	    		tblist.clear();
	    		tblist.add(new Courselist("N/A","N/A","N/A","N/A"));
	    		return;
	    	}
			//store the list of enrolled course
	    	 
	    	
	    	// Checking and recording the enrollment status in the CourseListTable
	    	
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
	*/
	

	


}
