
/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;


import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}
	
	@Test
	public void testsfqcoursebeforesearch() {
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertTrue(b.isDisable());
	}

	@Test
	public void testInvalidURL() {
		clickOn("#tabMain");
		clickOn("#textfieldURL");
		type(KeyCode.DELETE, 3);
		clickOn("#buttonSearch");
		sleep(3000);
	}
	
	
	@Test
	public void testSearch() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(5000);
		
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertFalse(b.isDisable());
	}
	
	@Test
	public void testAllSubjectSearch() {
		clickOn("#tabAllSubject");
		clickOn("#buttonAllSubjectSearch");
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertTrue(!b.isDisabled());
		clickOn("#buttonDisplay");
		sleep(50000);


		clickOn("#tabFilter");
		sleep(5000);
		clickOn("#tabList");
		sleep(5000);
		clickOn("#tabStatistic");
		sleep(1000);
		
		Button b1 = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertFalse(b1.isDisable());
	}
	
	@Test
	public void testInvalidURLsfq() {
		clickOn("#tabSfq");
		clickOn("#textfieldSfqUrl");
		type(KeyCode.DELETE, 3);
		clickOn("#buttonSfqEnrollCourse");
		sleep(3000);
		
		clickOn("#buttonInstructorSfq");
		sleep(3000);
	}
	
	/**
	 * Test for all the filter functions and enrollement function
	 * under the cases of subject search and all subject search cases
	 * */
	@SuppressWarnings("rawtypes")
	@Test
	public void testfilter() {
		sleep(500);
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(500);
		
		clickOn("#tabFilter");
		clickOn("#SelectALL");
		FxAssert.verifyThat("#SelectALL", LabeledMatchers.hasText("De-select All"));
		sleep(500);
		
		clickOn("#SelectALL");
		FxAssert.verifyThat("#SelectALL", LabeledMatchers.hasText("Select All"));
		sleep(500);
		
		clickOn("#AmBox");
		CheckBox am = (CheckBox)s.lookup("#AmBox");
		sleep(200);
		assertTrue(am.isSelected());
		
		clickOn("#PmBox");
		CheckBox pm = (CheckBox)s.lookup("#PmBox");
		sleep(200);
		assertTrue(pm.isSelected());
		
		sleep(500);
		clickOn("#tabList");
		TableView tt = (TableView)s.lookup("#CourseListTable");
		((Courselist) tt.getItems().get(0)).getEnroll().setSelected(true);
		sleep(500);	
		
		clickOn("#tabFilter");
		clickOn("#SelectALL");
		FxAssert.verifyThat("#SelectALL", LabeledMatchers.hasText("De-select All"));
		sleep(500);
		
		clickOn("#SelectALL");
		FxAssert.verifyThat("#SelectALL", LabeledMatchers.hasText("Select All"));
		sleep(500);
		
		clickOn("#MondayBox");
		clickOn("#PmBox");
		sleep(100);
		clickOn("#MondayBox");
		clickOn("#PmBox");
		sleep(100);
		
		clickOn("#AmBox");
		CheckBox amm = (CheckBox)s.lookup("#AmBox");
		sleep(200);
		assertTrue(amm.isSelected());
		
		clickOn("#CCBox");
		CheckBox cc = (CheckBox)s.lookup("#CCBox");
		sleep(200);
		assertTrue(cc.isSelected());
		sleep(100);
		clickOn("#NExclBox");
		CheckBox ex = (CheckBox)s.lookup("#NExclBox");
		sleep(200);
		assertTrue(ex.isSelected());
		sleep(100);
		clickOn("#LabBox");
		CheckBox lab = (CheckBox)s.lookup("#LabBox");
		sleep(200);
		assertTrue(lab.isSelected());
		sleep(100);
		
		clickOn("#CCBox");
		CheckBox ccc = (CheckBox)s.lookup("#CCBox");
		sleep(200);
		assertFalse(ccc.isSelected());
		sleep(100);
		clickOn("#NExclBox");
		CheckBox exx = (CheckBox)s.lookup("#NExclBox");
		sleep(200);
		assertFalse(exx.isSelected());
		sleep(100);
		clickOn("#LabBox");
		CheckBox labb = (CheckBox)s.lookup("#LabBox");
		sleep(200);
		assertFalse(labb.isSelected());
		sleep(100);


		
		sleep(500);
		clickOn("#tabList");
		TableView ttt = (TableView)s.lookup("#CourseListTable");
		((Courselist) ttt.getItems().get(1)).getEnroll().setSelected(true);
		sleep(500);
		
		clickOn("#tabTimetable");
		sleep(500);
		clickOn("#tabList");
		TableView tttt = (TableView)s.lookup("#CourseListTable");
		((Courselist) tttt.getItems().get(1)).getEnroll().setSelected(false);
		sleep(500);
		clickOn("#tabTimetable");
		sleep(500);
		
	}
	
	/**
	 * A test case to test the timetable functions w/o initiating search
	 * */
	@Test
	public void testtimetable() {
		sleep(500);
		clickOn("#tabMain");
		sleep(100);
		clickOn("#tabTimetable");
	}


}
