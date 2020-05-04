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
import javafx.scene.effect.ColorInput;
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
	private List<Course> drawCourse = new ArrayList<Course>();
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
    private TableColumn<Courselist, CheckBox> enrollbox;
    
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
    
//    private ObservableList<Courselist> tblist = FXCollections.observableArrayList();
    private ObservableList<Courselist> tblist = FXCollections.observableArrayList(item -> new javafx.beans.Observable[] {item.checkedProperty()});

    //After handlebox function
    @FXML
    void enrollmentUpdate() {
    	
    	if (filterCourse == null) {
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
    				for (Course ftcurr : filterCourse) {
    					String ftcurr_string = ftcurr.getTitle().split("\\ -")[0] + "--" + ftcurr.getSlot(i).getSectionCode();
    					if (ftcurr_string == courseIDString) {
    						drawCourse.add(ftcurr);
    					}
    				}
    			}
    		}
    		else {
    			if(EnrolledCourse.contains(courseIDString) == true) {
    				EnrolledCourse.remove(courseIDString);
    				for (Course ftcurr : filterCourse) {
    					String ftcurr_string = ftcurr.getTitle().split("\\ -")[0] + "--" + ftcurr.getSlot(i).getSectionCode();
    					if (ftcurr_string == courseIDString) {
    						drawCourse.remove(ftcurr);
    					}
    				}
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

    	/* Get num slooooooooooooooooooot!*/
    	
    	for (Course curr : filterCourse) {
    		Set<String> checkedID = new HashSet<String>();
    		String [] titleL = curr.getTitle().split("\\ -"); 
    		
    		for(int i=0;i<curr.getNumSlots();i++) {
    			Courselist currCour = new Courselist(titleL[0], curr.getSlot(i).getSectionCode(), titleL[1], curr.getSlot(i).getInstructor());
    			String CourRefID = titleL[0] + "--" + curr.getSlot(i).getSectionCode();
//    			textAreaConsole.setText(CourRefID+"2222222222222222222222");
    			
    			if (EnrolledCourse.contains(CourRefID)) {
    				currCour.getEnroll().setSelected(true);
    			}
    			
//    	    	for(int k = 0; k < EnrolledCourse.size(); k++) {
  //  	    		System.out.println(EnrolledCourse.get(k));
    //	    	}
    			/* if this course section has not been added to the table list
    			 * add the course section the checkedID reference set
    			 * and add this course section to the table list to show it
    			 */
    			// I don't need to understand this -Andrew
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
    	filterCourse = new ArrayList<Course>();
    	Filter filterEN = new Filter();
    	Boolean []CBList = new Boolean[11];
    	Arrays.fill(CBList, Boolean.FALSE);
    	int filter_flag = 0;

    	
    	if (AmBox.isSelected()) {
    		CBList[0] = true;
    		filter_flag ++;
//    		System.out.println("captured");
    	}
    	if (PmBox.isSelected()) {
    		CBList[1] = true;
    		filter_flag ++;
    	}
    	
    	if (MondayBox.isSelected()) {
    		CBList[2] = true;
    		filter_flag ++;
//    		System.out.println("mon");
    	}
    	
    	if (TuesdayBox.isSelected()) {
    		CBList[3] = true;
    		filter_flag ++;
//    		System.out.println("tue");

    	}
    	if (WednesdayBox.isSelected()) {
    		CBList[4] = true;
    		filter_flag ++;
//    		System.out.println("wed");
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
    	
    	if(filter_flag >0) {
    		filterCourse = filterEN.call_filter(CBList, myCourseList);
    	}else {
    		filterCourse = myCourseList;
    	}
    	
    	// print text on console after filtering
    	for (Course c:filterCourse) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0;i<c.getNumSlots();i++) {
    			Slot curr_slot = c.getSlot(i);
    			newline += curr_slot.getDay() + "day check" + "Section " + curr_slot.getSectionCode() + " Slot " + i + ":" + curr_slot.toString()+ "\n";
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	} 	
    	enrollmentUpdate();
    	updateList();
    	drawtable();
    	

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
    	
    	// To draw the timetable if the enrollment is not yet implemented
    	if (enrollbox.isEditable()==false) {
    		drawtable();
    	}
    	}

    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
		courseCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseCode"));
    	sectionCode.setCellValueFactory(new PropertyValueFactory<Courselist,String>("section"));
    	courseName.setCellValueFactory(new PropertyValueFactory<Courselist,String>("courseName"));
    	instructor.setCellValueFactory(new PropertyValueFactory<Courselist,String>("instructor"));
    	enrollbox.setCellValueFactory(new PropertyValueFactory<Courselist,CheckBox>("enroll"));
    	
    	tblist.clear();
    	tblist.add(new Courselist("N/A","N/A","N/A","N/A"));


    	
    	CourseListTable.setItems(tblist);
    	
    	tblist.addListener(new ListChangeListener<Courselist>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Courselist> c) {
				// TODO Auto-generated method stub
				while(c.next()) {
					if(c.wasUpdated()) {
						System.out.println("a change");
						updateList();						
					}
				}
			}
    		
    	});
	}
    
    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(false);
    	
    	textAreaConsole.setText("Let's get started!");
    	System.out.println("Would this show up?");
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
    	
    	// Scrape is successful and copy courses to myCourseList and display the output
    	else {
    		// Get search result statistics
    		myCourseList = v;
    		String info1 = "Total number of different Sections in this search: " + Section.getNumSections() + "\n";
    		String info2 = "Total number of Courses in this search: " + Course.getNumCourse() + "\n";
    		String info3 = "Instructor who has teaching assignment this term but does not need to teach at Tu 3:10pm: \n";
    		
    		//Print instructor info
    		List<String> nameList = Instructor.getInstructorNoTu1510();
    		for (String name : nameList) {
    			info3 += name + "\n";
    		}
    		textAreaConsole.setText(info1 + info2 + info3);
    		
    		// Print course info
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
    	
    	// To draw the timetable if the enrollment is not yet implemented
    	if (enrollbox.isEditable()==false) {
    		drawtable();
    	}
    }
	
	
	@FXML
	void drawtable() {
		if (enrollbox.isEditable()==false) {
			drawCourse.clear();
			if (myCourseList.size()<5) {
				drawCourse = myCourseList;
			}
			else {
				for(int i = 0; i<5 ; i++) {
					drawCourse.add(myCourseList.get(i));
				}
			}
		}
		// drawCourse.get(0).getSlot(0).getEnd().gethour returns INTEGER value
		
		//TODO Draw the table!
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Label randomLabel2 = new Label("COMP3711\nL3");
    	Random r = new Random();
    	double start = (r.nextInt(2) + 1) * 20 + 40;
    	
    	// Use random, Random r = new Random().nextInt(1) to Color.color(red, green, blue), to have different color everytime.
    	
    	randomLabel2.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

    	randomLabel2.setLayoutX(500.0);
    	randomLabel2.setLayoutY(start + 40);
    	randomLabel2.setMinWidth(100.0);
    	randomLabel2.setMaxWidth(100.0);
    	randomLabel2.setMinHeight(60);
    	randomLabel2.setMaxHeight(60);
    	randomLabel2.setblendmode;
    	String address = getParameter("adress");
    	ap.getChildren().addAll(randomLabel2);
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
