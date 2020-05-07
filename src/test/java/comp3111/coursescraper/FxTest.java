
/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

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
	}
	
	@Test
	public void testAllSubjectSearch() {
		clickOn("#tabAllSubject");
		clickOn("#buttonAllSubjectSearch");
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertTrue(!b.isDisabled());
		clickOn("#buttonDisplay");
		sleep(30000);
		
		clickOn("#tabFilter");
		sleep(5000);
		clickOn("#tabList");
		sleep(5000);
		clickOn("#tabStatistic");
		sleep(1000);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testfilter() {
		sleep(500);
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(500);
		clickOn("#tabFilter");
		clickOn("#SelectALL");
		sleep(500);
		clickOn("#SelectALL");
		sleep(500);
		clickOn("#AmBox");
		CheckBox am = (CheckBox)s.lookup("#AmBox");
		sleep(100);
		assertTrue(am.isSelected());
		clickOn("#PmBox");
		CheckBox pm = (CheckBox)s.lookup("#PmBox");
		sleep(100);
		assertTrue(pm.isSelected());
		sleep(500);
		clickOn("#tabList");

		
	}
}
