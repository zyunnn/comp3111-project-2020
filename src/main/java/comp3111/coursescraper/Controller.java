package comp3111.coursescraper;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

//import javafx.scene.control.TableColumn;
import javafx.concurrent.Task;
//import javafx.scene.control.TableView;

//for handling checkbox
import javafx.scene.control.CheckBox;


import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * @author jacky tam
 *
 */
public class Controller {
	private static List<Course> myCourseList;
	private List<Course> filterCourse;
    private List<String> myEnrolledCourseList = new ArrayList<String>();
	
	
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
    
//    AmBox.selectedProperty().addListener(BoxListen);

    // need a listener to listen the filter and identify the needs, then call the interface to call the functions
    /*//    		textAreaConsole.setText(nimab);
//    		AmBox.selectedProperty().addListener(listener);
    		
     * things needed
     * event handler
     * 	@FXML
	void handleBox() {
		textAreaConsole.setText("AM");
	}
	
	@FXML
	void handleBox2() {
		textAreaConsole.setText("bye");
	}
	    
     * listener
     * 
     * declare all the checkbox with fx:id
     * 
     * */
    
    

    @FXML
    private Tab tabList;
    /*
     * things needed
     * observe list
     * 
     * checkbox updater
     * 
     * 
     * */

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

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
    
    
    void enrollmentUpdate() {
    	
    }
    
    void updateList() {
    	this.myEnrolledCourseList.clear();
    	if (filterCourse == )
    }
    
    @FXML
    void handleBox() {
    	textAreaConsole.clear();
    	// intitate the filter checklist and pass to the filter class to process
//    	filterCourse = myCourseList;
    	this.filterCourse.clear();
    	Filter filterEN = new Filter();
    	CheckBox[] CBList = new CheckBox[11];
    	CBList[0] = AmBox;
    	CBList[1] = PmBox;
    	CBList[2] = MondayBox;
    	CBList[3] = TuesdayBox;
    	CBList[4] = WednesdayBox;
    	CBList[5] = ThursdayBox;
    	CBList[6] = FridayBox;
    	CBList[7] = SaturdayBox;
    	CBList[8] = CCBox;
    	CBList[9] = NExclBox;
    	CBList[10] = LabBox;
    	filterCourse = filterEN.call_filter(CBList, myCourseList);
    	
    	
    	// print text on console after filtering
    	for (Course c:this.filterCourse) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 1;i<c.getNumSlots();i++) {
    			Slot curr_slot = c.getSlot(i);
    			newline += "Section " + curr_slot.getSections() + " Slot " + i + ":" + curr_slot.toString();
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	} 	
    	enrollmentUpdate();
    	updateList();
    	return;
    }
    
    
    private Scraper scraper = new Scraper();
    
    @FXML
    void allSubjectSearch() {
    	buttonSfqEnrollCourse.setDisable(false);
    	final Task<Void> allSearchThread = new Task<Void>() {
    		
    		@Override
    		protected Void call() throws Exception {
    	
		    	List<String> subjects = scraper.scrapeAllSubject(textfieldURL.getText(), textfieldTerm.getText());
		    	int totalSubjectCount = subjects.size();
		    	textAreaConsole.setText("Total Number of Categories/Code Prefix: " + totalSubjectCount);
		    	
		    	int subjectCount = 0;
		    	int courseCount = 0;
		    	
		    	for (String subject : subjects) {
		    		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);
		    		String newline = "";
		        	for (Course c : v) {
//		        		String newline = c.getTitle() + "\n";
		        		newline += "\n" + c.getTitle() + "\n";
		        		for (int i = 0; i < c.getNumSlots(); i++) {
		        			Slot t = c.getSlot(i);
		        			newline += "Slot " + i + ":" + t + "\n";
		        		}
		        	}
		        	textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		    		subjectCount++;
		    		courseCount += v.size();
		    		updateProgress(subjectCount+1, totalSubjectCount);
		    		System.out.println("Subject " + subject + " is done.");
		    	}
		    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of Courses fetched: " + courseCount);
		    	return null;
    		}
    	};
    	
//    	final ProgressBar pb = new ProgressBar();
    	progressbar.progressProperty().bind(allSearchThread.progressProperty());
    	
    	// Color the bar green when scraping is complete
    	progressbar.progressProperty().addListener(observable -> {
    		if (progressbar.getProgress() == 1) {
    			progressbar.setStyle("-fx-accent: forestgreen;");
    		}
    	});
    	
    	Thread thread = new Thread(allSearchThread, "scrape-thread");
    	thread.setDaemon(true);
    	thread.start();
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
		Course.resetNumCourse();
		Section.resetNumSections();
		Instructor.resetAllNameList();
		textAreaConsole.setText("");
		
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
  
    	/*
    	 * Task 1.1 Handle Error 404 
    	 */
    	if (v == null) {
    		textAreaConsole.setText("Error scraping page " + textfieldURL.getText());
    	}
    	else if (v.size() == 1 && v.get(0).getTitle().substring(0,3).equals("404")) {
    		textAreaConsole.setText("Invalid URL: Page not found");
    	}  
    	else {
    		textAreaConsole.setText("Scraping success");
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
//        		System.out.println(c.getNumSlots());
        		for (int i = 0; i < c.getNumSlots(); i++) {
        			Slot t = c.getSlot(i);
        			newline += "Slot " + i + ":" + t + "\n";
        		}
        		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
        	}
    	}
	
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	
    	
    	
    }
	


}
