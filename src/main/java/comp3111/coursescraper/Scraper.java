package comp3111.coursescraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;



import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.DomText;
import java.util.Vector;



/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url and parse the page by looking at the HTML DOM.  
 * <br>
 * In this particular sample code, it access to HKUST class schedule and quota page (COMP). 
 * <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP
 *  <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP is the course code begins with COMP.
 * <br>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; div id="classes" &rarr; div class="course" &rarr;. You might see something like this:
 * <br>
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style="position: relative; float: left; visibility: hidden; top: -164px;"><a name="COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class="crseattrword">[3Y10]</span><div class="popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class="popup attrword"><span class="crseattrword">[3Y12]</span><div class="popupdetail">CC for 3Y 2012 cohort</div></div><div class="popup attrword"><span class="crseattrword">[4Y]</span><div class="popupdetail">CC for 4Y 2012 and after</div></div><div class="popup attrword"><span class="crseattrword">[DELI]</span><div class="popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style="font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style="text-align: left">Date &amp; Time</th><th width="160" style="text-align: left">Room</th><th width="190" style="text-align: left">Instructor</th><th width="45">Quota</th><th width="45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width="81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 *</pre>
 * <br>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()).   
 * 
 *
 */
public class Scraper {
	private WebClient client;
	private Hashtable<String, List<Float>> sfqcs = new Hashtable<String, List<Float>>(); //sfq course score
	private Hashtable<String, List<Float>> sfqins = new Hashtable<String, List<Float>>(); // instructor score
	private Hashtable<String, Float> sfqins_final = new Hashtable<String, Float>();
	private Hashtable<String, Float> sfqcs_final = new Hashtable<String, Float>();
	private int[] numList = {1,2,3,4,5,6,7,8,9,0};

	/**
	 * Default Constructor 
	 */
	public Scraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}

	private void addSlot(HtmlElement e, Course c, boolean secondRow, String sectionCode) {

		String times[] =  e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
		String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
		String instructorName = e.getChildNodes().get(secondRow ? 2 : 5).asText();
		
		// Invalid time slot
		if (times[0].equals("TBA"))		
			return;
		
		String startAt = times[1];
		String endAt = times[3];
		String classDay = times[0];
		
		// Different time format
		if (classDay.length() == 11) {
			classDay = times[2].split("\n")[1];
			startAt = times[3];
			endAt = times[5];
		}
		
		// Check 9:00am to 10:00pm time slot
		LocalTime startTime = LocalTime.parse(startAt, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		LocalTime endTime = LocalTime.parse(endAt, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		if (startTime.compareTo(LocalTime.parse("09:00:00")) < 0 &&
				endTime.compareTo(LocalTime.parse("22:00:00")) > 0) {
			return;
		}
		
		// Raise flag for Tuesday 3:10pm
		boolean tuFlag = false;
		for (int j = 0; j < classDay.length(); j+=2) {
			String code = classDay.substring(j , j + 2);
			if (Slot.DAYS_MAP.get(code) == null)	// Invalid Sunday slot
				break;
			Slot s = new Slot();
			s.setDay(Slot.DAYS_MAP.get(code));
			s.setStart(startAt);
			s.setEnd(endAt);
			s.setVenue(venue);
			s.setSectionCode(sectionCode);
			s.setInstructor(instructorName);
			c.addSlot(s);
			
			// Check teaching assignment on Tuesday 15:10
			if (startTime.compareTo(LocalTime.parse("15:10:00")) <= 0 &&
					endTime.compareTo(LocalTime.parse("15:10:00")) >= 0 &&
					code.equals("Tu")) {
				tuFlag = true;
			}
			
			// Get list of instructors
			String nameList[] = instructorName.split("\n");
			for (String name : nameList) {
				if (!name.equals("TBA") && !name.equals(" ")) {
					Instructor.addAllInstructor(name);
					if (tuFlag) {
						Instructor.addTeachingTu1510(name);
					}
				}
			}	
		}
	}

	public List<Course> scrape(String baseurl, String term, String sub) {

		try {
			
			HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
			List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
			
			Vector<Course> result = new Vector<Course>();

			for (int i = 0; i < items.size(); i++) {
				Course c = new Course();
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				
				HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
				c.setTitle(title.asText());
				
				// Exclusion subject
				List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement exclusion = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("EXCLUSION")) {
						exclusion = d;
					}
				}
				c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
				
				// Common core subject
				List<?> popupdetailslist2 = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement commonCore = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist2) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("ATTRIBUTES")) {
						commonCore = d;
					}
				}
				c.setCommonCore((commonCore == null ? "null" : commonCore.asText()));
				
				// Description of subject
				List<?> popupdetailslist3 = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement description = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist3) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("DESCRIPTION")) {
						description = d;
					}
				}
				c.setDescription((description == null ? "null" : description.asText()));
				
				List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
				// Add slot information
				boolean validCourseFlag = false;		
				for ( HtmlElement e: (List<HtmlElement>)sections) {
					Section s = new Section(e);
					if (!s.validSection()) 
						continue;
							
					Section.incrementNumSections();
					validCourseFlag = true;
					addSlot(e, c, false, s.getSectionCode());
					e = (HtmlElement)e.getNextSibling();
					if (e != null && !e.getAttribute("class").contains("newsect"))
						addSlot(e, c, true, s.getSectionCode());
				}
				Course.incrementAllCourse();
				
				// Keep track for course with at least 1 valid section
				if (validCourseFlag) {		
					Course.incrementNumCourse();
					result.add(c);
				}
				// Debugging purpose
				else
					System.out.println("Invalid course: " + c.getTitle());
			}
			client.close();
			return result;
		} 
		catch (Exception e) {
			// Handle Error 404
			if (e instanceof com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException &&
					e.getMessage().substring(0,3).equals("404")) {
				Vector<Course> invalid = new Vector<Course>();
				Course dummy = new Course();
				dummy.setTitle(e.getMessage());
				invalid.add(dummy);
				return invalid;
			} else {
				System.out.println(e);
			}
		}
		return null;
	}
		
	
	public List<String> scrapeAllSubject(String baseurl, String term) {
		
		try {
			HtmlPage page = client.getPage(baseurl + "/" + term + "/");
			
			List<?> items = (List<?>) page.getByXPath("//div[@id='navigator']/div[@class='depts']/a");
			List<String> subjectList = new Vector<String>();
			
			for (int i = 0; i < items.size(); i++) {
				subjectList.add(((HtmlElement) items.get(i)).asText());
			}
			client.close();
			return subjectList;
		}
		catch (Exception e) {
			
			// Handle Error 404
			if (e instanceof com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException &&
					e.getMessage().substring(0,3).equals("404")) {
				System.out.println("Invalid URL input for AllSubjectSearch");
				return  null;
			} else {
				System.out.println(e);
			}
		}
		return null;
	}

	
	public List<Course> scrape_dir(String url, String sub) {

		try {
			
			HtmlPage page = client.getPage(url);
			List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
			
			Vector<Course> result = new Vector<Course>();

			for (int i = 0; i < items.size(); i++) {
				Course c = new Course();
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				
				HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
				c.setTitle(title.asText());
				
				// Exclusion subject
				List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement exclusion = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("EXCLUSION")) {
						exclusion = d;
					}
				}
				c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
				
				// Common core subject
				List<?> popupdetailslist2 = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement commonCore = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist2) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("ATTRIBUTES")) {
						commonCore = d;
					}
				}
				c.setCommonCore((commonCore == null ? "null" : commonCore.asText()));
				
				// Description of subject
				List<?> popupdetailslist3 = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement description = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist3) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("DESCRIPTION")) {
						description = d;
					}
				}
				c.setDescription((description == null ? "null" : description.asText()));
				
				List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
				// Add slot information
				boolean validCourseFlag = false;		
				for ( HtmlElement e: (List<HtmlElement>)sections) {
					Section s = new Section(e);
					if (!s.validSection()) 
						continue;
							
					Section.incrementNumSections();
					validCourseFlag = true;
					addSlot(e, c, false, s.getSectionCode());
					e = (HtmlElement)e.getNextSibling();
					
					if (e != null && !e.getAttribute("class").contains("newsect"))
						addSlot(e, c, true, s.getSectionCode());
				}
				Course.incrementAllCourse();
				
				// Keep track for course with at least 1 valid section
				if (validCourseFlag) {		
					Course.incrementNumCourse();
					result.add(c);
				}
				// Debugging purpose
				else
					System.out.println("Invalid course: " + c.getTitle());
			}
			client.close();
			return result;
		} 
		catch (Exception e) {
			// Handle Error 404
			if (e instanceof com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException &&
					e.getMessage().substring(0,3).equals("404")) {
				Vector<Course> invalid = new Vector<Course>();
				Course dummy = new Course();
				dummy.setTitle(e.getMessage());
				invalid.add(dummy);
				return invalid;
			} else {
				System.out.println(e);
			}
		}
		return null;
	}
	
	public Hashtable<String,Float> sfqcourse(String url) {
		try {

			HtmlPage page = client.getPage(url);
//			List<?> items = (List<?>) page.getByXPath(".//table");
			List<?> bs = (List<?>) page.getByXPath(".//b");
			
			for (int i = 0; i < bs.size(); i++) {
				HtmlElement b = (HtmlElement) bs.get(i);
				String rawCourseCode[] = b.asText().split("\\(");
				if(rawCourseCode.length<2) {
//					System.out.println("The scraped text, which supposed to be in b, does not contain '('");
					continue;
				}
				String CourseCode = rawCourseCode[1].substring(0,rawCourseCode[1].length()-1);

				
				for(int b_num = 0;b_num<2;b_num++) {
					if(b.getNextSibling()!=null)
					b = (HtmlElement) b.getNextSibling();
				}
				
				if (b.getNodeName().equals("table") == false) {
					continue;
				}
				if (CourseCode.contains("-")) {
					CourseCode = CourseCode.split("-")[0];
				}
//				System.out.println("CourseCode "+CourseCode+" is scraped.");
				
				// Below we are scraping courses under the SAME DEPARTMENT
				HtmlElement sfqtable = b;
				List<?> blockvalue = (List<?>) sfqtable.getByXPath(".//tbody/tr/td[@colspan='3']");
				
				for (int courseRowNum = 0; courseRowNum < blockvalue.size(); courseRowNum++) {
					// Every iteration witches one course
//					System.out.println(((HtmlElement) blockvalue.get(courseRowNum)).asText());
					HtmlElement trcourse =null;
					if ((HtmlElement) blockvalue.get(courseRowNum)!=null) {
					trcourse = (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum)).getParentNode();
					}
					if (trcourse == null) continue;
					
//					System.out.println("Out");
//					boolean multipleIns = false;
					boolean multiSecs = false;
					boolean abnormal = false; // How come a course would have multiple ins or sections! Abnormal!
					List<?> trcoursetitle = (List<?>) trcourse.getByXPath(".//td");
					String courseName = ((HtmlElement) trcoursetitle.get(0)).asText().trim();
					
					do {
						if(trcourse.getNextSibling().getNextSibling()==null) {
//							System.out.println("trcourse.getNextSibling().getNextSibling()==null");
							break;
						}
						
						trcourse = (HtmlElement) trcourse.getNextSibling().getNextSibling();
						
/*						boolean contain = false;
						for (int tempp = 0; tempp < trins.getChildElementCount(); tempp++) {
							System.out.println(trins.getChildNodes().get(tempp).getNodeName().equals("td"));
							if((trins.getChildNodes().get(tempp).getNodeName()).equals("td")) {
								contain = true;
								break;
							}
						}*/
						
						List<?> trcourseContent = (List<?>) trcourse.getByXPath(".//td");
						
						// Coursename
						for (int size = 0; size < trcourseContent.size(); size++) {
//							System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
							if(size == 2) {
								if(!((HtmlElement) trcourseContent.get(size)).asText().trim().isEmpty()) {
//									System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
									multiSecs = true;
								}
							}

						}
						if(multiSecs) {
							multiSecs = false;
							continue;
						}
						
//						System.out.println("Abnormal is" +Boolean.toString(abnormal));
						
						
//						for (int size = 0; size < trcourseContent.size(); size++) {
//							if(size == 1) {
//							courseName = ((HtmlElement) trcourseContent.get(size)).asText();
//							System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
	//						}
//						}
						
						
						
//						if (((HtmlElement) trcourseContent.get(2)).asText().split("\\(").length<2) continue;
						
						boolean effectiveScore = false;
						for(int test:numList) {
//							System.out.println(String.valueOf(test));
							if(((HtmlElement) trcourseContent.get(3)).asText().split("\\(")[0].contains(String.valueOf(test))) effectiveScore = true;
						}
						
						float insSecScore;
						
						if(!effectiveScore) insSecScore = -1;
						else{
							// Four should be the place where ins grade is.
							insSecScore = Float.parseFloat(((HtmlElement) trcourseContent.get(3)).asText().split("\\(")[0].trim());
						}
						List<Float> temp = new ArrayList<Float>();
						
						
						if (sfqcs.get(courseName) == null) {
							temp.clear();
							temp.add(insSecScore);
							sfqcs.put(courseName, temp);
						}
						else {
							temp.clear();
							temp = sfqcs.get(courseName);
							temp.add(insSecScore);
							sfqcs.put(courseName, temp);
						}
						
//						System.out.println("CourseRowNum: "+ courseRowNum+ " blockvalue size: " + blockvalue.size());
						if(courseRowNum+1==blockvalue.size()) {
//							System.out.println("Reach last");
							break;
						}
/*						if((HtmlElement) trcourse.getNextSibling().getNextSibling() == null) {
							System.out.println("trcourse = (HtmlElement) trcourse.getNextSibling() is null");
						}
						System.out.println(Boolean.toString(((HtmlElement) trcourse.getNextSibling().getNextSibling()).equals((HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode())));
						if(!(((HtmlElement) trcourse.getNextSibling().getNextSibling()) == (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode())) {
							System.out.println("sdfsdafdsafasd is DIFEREEEEENT!");
							abnormal = true;
						}
						else if (((HtmlElement) trcourse.getNextSibling().getNextSibling()) == (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode()) {
							System.out.println("The next sibling and the next course element is EQUALLLLLL");
						}*/
					}
					while (((HtmlElement) trcourse.getNextSibling().getNextSibling()) != (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode());
					// parent的next simbling是下一个children的parent，停；如果下面没有了，停；
//					System.out.println("One course finished");
				}
				for (String key:sfqcs.keySet()) {
//					System.out.println("Course: " + key+ " sfq: " + sfqcs.get(key));
					List<Float> temp_score = sfqcs.get(key);
					float temp_score_avg = 0;
					float arr_size = temp_score.size();
					for (int it = 0; it< temp_score.size(); it++) {
						if(temp_score.get(it)>0) {
							temp_score_avg += temp_score.get(it);
						}
						else {
							arr_size -=1;
						}
					}
					if(arr_size<=0) {
						temp_score_avg = -1;
					}
					temp_score_avg = temp_score_avg/arr_size;
					sfqcs_final.put(key, temp_score_avg);
				}
			}
			


			
//			b.getnextsibling
//			List<String> result = new Vector<String>();
			
//			for (int i = 0; i < items.size(); i++) {
//				HtmlElement htmlItem = (HtmlElement) items.get(1);
				//if (htmlItem == b) {
					//System.out.println("Effective");
				//}

//				result.add(((HtmlElement) items.get(i)).asText());
//			}
	    	return sfqcs_final;
		}
		
		
		catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sfqcs_final;
	}
	


	
	public Hashtable<String,Float> sfqins(String url) {
		try {

			HtmlPage page = client.getPage(url);
//			List<?> items = (List<?>) page.getByXPath(".//table");
			List<?> bs = (List<?>) page.getByXPath(".//b");
			
			for (int i = 0; i < bs.size(); i++) {
				HtmlElement b = (HtmlElement) bs.get(i);
				String rawCourseCode[] = b.asText().split("\\(");
				if(rawCourseCode.length<2) {
//					System.out.println("The scraped text, which supposed to be in b, does not contain '('");
					continue;
				}
				String CourseCode = rawCourseCode[1].substring(0,rawCourseCode[1].length()-1);

				
				for(int b_num = 0;b_num<2;b_num++) {
					if(b.getNextSibling()!=null)
					b = (HtmlElement) b.getNextSibling();
				}
				
				if (b.getNodeName().equals("table") == false) {
					continue;
				}
				if (CourseCode.contains("-")) {
					CourseCode = CourseCode.split("-")[0];
				}
//				System.out.println("CourseCode "+CourseCode+" is scraped.");
				
				// Below we are scraping courses under the SAME DEPARTMENT
				HtmlElement sfqtable = b;
				List<?> blockvalue = (List<?>) sfqtable.getByXPath(".//tbody/tr/td[@colspan='3']");
				
				for (int courseRowNum = 0; courseRowNum < blockvalue.size(); courseRowNum++) {
					// Every iteration witches one course
//					System.out.println(((HtmlElement) blockvalue.get(courseRowNum)).asText());
					HtmlElement trcourse =null;
					if ((HtmlElement) blockvalue.get(courseRowNum)!=null) {
					trcourse = (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum)).getParentNode();
					}
					if (trcourse == null) continue;
					
//					System.out.println("Out");
//					boolean multipleIns = false;
					boolean multiSecs = false;
					boolean abnormal = false; // How come a course would have multiple ins or sections! Abnormal!
					do {
						if(trcourse.getNextSibling().getNextSibling()==null) {
//							System.out.println("trcourse.getNextSibling().getNextSibling()==null");
							break;
						}
//						if(trcourse.getNextSibling().getNextSibling().getNextSibling() == null || trcourse.getNextSibling().getNextSibling().getNextSibling().getNextSibling()==null) {
						if(!abnormal) {
							trcourse = (HtmlElement) trcourse.getNextSibling().getNextSibling().getNextSibling().getNextSibling();
						}
						else {
							trcourse = (HtmlElement) trcourse.getNextSibling().getNextSibling();
							abnormal = false;
						}
						HtmlElement trins = trcourse;
						
/*						boolean contain = false;
						for (int tempp = 0; tempp < trins.getChildElementCount(); tempp++) {
							System.out.println(trins.getChildNodes().get(tempp).getNodeName().equals("td"));
							if((trins.getChildNodes().get(tempp).getNodeName()).equals("td")) {
								contain = true;
								break;
							}
						}*/
						
						List<?> trcourseContent = (List<?>) trins.getByXPath(".//td");
						
						// Ins name
						for (int size = 0; size < trcourseContent.size(); size++) {
//							System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
							if(size == 1) {
								if(!((HtmlElement) trcourseContent.get(size)).asText().trim().isEmpty()) {
//									System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
									multiSecs = true;
								}
							}

						}
						if(multiSecs) {
							multiSecs = false;
//							System.out.println("Touched");
							trcourse = (HtmlElement) trcourse.getNextSibling().getNextSibling();
							trins = trcourse;
							trcourseContent = (List<?>) trins.getByXPath(".//td");
						}
						
//						System.out.println("Abnormal is" +Boolean.toString(abnormal));
						
						String insName = "";
						
						for (int size = 0; size < trcourseContent.size(); size++) {
							if(size == 2) {
							insName = ((HtmlElement) trcourseContent.get(size)).asText();
//							System.out.println(((HtmlElement) trcourseContent.get(size)).asText());
							}
						}
						
						
						
//						if (((HtmlElement) trcourseContent.get(2)).asText().split("\\(").length<2) continue;
						
						boolean effectiveScore = false;
						for(int test:numList) {
//							System.out.println(String.valueOf(test));
							if(((HtmlElement) trcourseContent.get(4)).asText().split("\\(")[0].contains(String.valueOf(test))) effectiveScore = true;
						}
						
						float insSecScore;
						
						if(!effectiveScore) insSecScore = -1;
						else{
							// Four should be the place where ins grade is.
							insSecScore = Float.parseFloat(((HtmlElement) trcourseContent.get(4)).asText().split("\\(")[0].trim());
						}
						List<Float> temp = new ArrayList<Float>();
						
						
						if (sfqins.get(insName) == null) {
							temp.clear();
							temp.add(insSecScore);
							sfqins.put(insName, temp);
						}
						else {
							temp.clear();
							temp = sfqins.get(insName);
							temp.add(insSecScore);
							sfqins.put(insName, temp);
						}
						
//						System.out.println("CourseRowNum: "+ courseRowNum+ " blockvalue size: " + blockvalue.size());
						if(courseRowNum+1==blockvalue.size()) {
//							System.out.println("Reach last");
							break;
						}
						if((HtmlElement) trcourse.getNextSibling().getNextSibling() == null) {
//							System.out.println("trcourse = (HtmlElement) trcourse.getNextSibling() is null");
						}
//						System.out.println(Boolean.toString(((HtmlElement) trcourse.getNextSibling().getNextSibling()).equals((HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode())));
						if(!(((HtmlElement) trcourse.getNextSibling().getNextSibling()) == (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode())) {
//							System.out.println("sdfsdafdsafasd is DIFEREEEEENT!");
							abnormal = true;
						}
						else if (((HtmlElement) trcourse.getNextSibling().getNextSibling()) == (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode()) {
//							System.out.println("The next sibling and the next course element is EQUALLLLLL");
						}
					}
					while (((HtmlElement) trcourse.getNextSibling().getNextSibling()) != (HtmlElement) ((HtmlElement) blockvalue.get(courseRowNum+1)).getParentNode());
					// parent的next simbling是下一个children的parent，停；如果下面没有了，停；
//					System.out.println("One course finished");
				}
				for (String key:sfqins.keySet()) {
//					System.out.println("Course: " + key+ " sfq: " + sfqins.get(key));
					List<Float> temp_score = sfqins.get(key);
					float temp_score_avg = 0;
					float arr_size = temp_score.size();
					for (int it = 0; it< temp_score.size(); it++) {
						if(temp_score.get(it)>0) {
							temp_score_avg += temp_score.get(it);
						}
						else {
							arr_size -=1;
						}
					}
					if(arr_size<=0) {
						temp_score_avg = -1;
					}
					temp_score_avg = temp_score_avg/arr_size;
					sfqins_final.put(key, temp_score_avg);
			}
			


			
//			b.getnextsibling
//			List<String> result = new Vector<String>();
			
//			for (int i = 0; i < items.size(); i++) {
//				HtmlElement htmlItem = (HtmlElement) items.get(1);
				//if (htmlItem == b) {
					//System.out.println("Effective");
				//}

//				result.add(((HtmlElement) items.get(i)).asText());
//			}
		}
		}
		
		
		catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sfqins_final;
	}


}
