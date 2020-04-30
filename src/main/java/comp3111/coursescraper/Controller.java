package comp3111.coursescraper;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
//import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
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
import java.util.List;
/**
 * @author jacky tam
 *
 */
public class Controller {
	private static List<Course> myCourseList;
	private static List<String> subject;
	private List<Course> filterCourse;
	
	
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
    
    @FXML
    void handleBox() {
    	
    	// intitate the filter checklist and pass to the filter class to process
//    	filterCourse = myCourseList;
    	filterCourse = null;
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
    	filterCourse = filterEN.call_filter(CBList, filterCourse);
    	
    	// print text on console after filtering
    	
//    	textAreaConsole.setText(one);
  	
    	
    }
    
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

    private Scraper scraper = new Scraper();
    
    private static List<Course> myCourseList;
    
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
