package comp3111.coursescraper;

import java.awt.event.ActionEvent;
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

import java.util.Random;
import java.util.List;
public class Controller {

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
    private Tab tabList;

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
