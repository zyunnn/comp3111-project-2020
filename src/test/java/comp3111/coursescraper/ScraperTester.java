package comp3111.coursescraper;

import static org.junit.Assert.*;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Tester to test the Scraper class
 * @author zhou zhuo rui
 *
 */
public class ScraperTester extends ApplicationTest{

	private String test_url;
	
	private Scraper scraper = new Scraper();
	
    @FXML
    private TextField textfieldSfqUrl;
	
	private Scene s;
	
	/**
	 * Codes necessary for setting up the stage for FXML
	 */
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
//   		stage.show();
   		s = scene;
	}
	
	/**
	 * Constructor for url value
	 * @throws Exception Errors during constructor
	 */
	@Before
	public void setUp() throws Exception{
		TextField input_url_tf = (TextField)s.lookup("#textfieldSfqUrl");
		textfieldSfqUrl = input_url_tf;
		test_url = input_url_tf.getText();
	}
	
	/**
	 * Test if sfqcourse runs normally
	 */
	@Test
	public void testsfqcourse() {
		scraper.sfqcourse(test_url);
	}
	
	/**
	 * Test if sfqins runs normally
	 */
	@Test
	public void testsfqins() {
		scraper.sfqins(test_url);
	}
	
	
}
