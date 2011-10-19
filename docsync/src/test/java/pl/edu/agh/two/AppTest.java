package pl.edu.agh.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Sample unit test.
 * <p/>
 * Creation date: 2011.10.19
 *
 * @author Tomasz Zdybał
 */
public class AppTest {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(AppTest.class);

	/**
	 * Application instance.
	 */
	private App app;

	@BeforeClass
	public void setUp() throws Exception {
		app = new App();
	}

	@Test
	public void testInvalidAdd() throws Exception {
		log.debug("Checking whether 2 + 2 = 4 using invalid method...");
		assertEquals(app.invalidAdd(2, 2), 4);
	}

	@Test
	public void testValidAdd() throws Exception {
		log.debug("Checking whether 1 + 1 = 2 (using valid method...");
		assertEquals(app.invalidAdd(1, 1), 2);
	}
}
