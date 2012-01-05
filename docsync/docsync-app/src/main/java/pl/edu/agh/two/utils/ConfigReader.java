package pl.edu.agh.two.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads configuration from file.
 * <p/>
 * Creation date: 2011.11.16
 *
 * @author Tomasz Zdybał
 */
public class ConfigReader {
	private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
	private static ConfigReader ourInstance = new ConfigReader();
	private static final String DEFAULT_CONFIG = "config.properties";
	private static final String USER_CONFIG = "user.properties";

	private Properties properties;

	public static ConfigReader getInstance() {
		return ourInstance;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	private ConfigReader() {
		properties = new Properties();
		// load default properties
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG));
		} catch (IOException e) {
			log.error("Error", e);
		}

		// try to load user - specific properties
		Properties user = new Properties();
		try {
			user.load(new FileReader(USER_CONFIG));
			for (String name : user.stringPropertyNames()) {
				properties.put(name, user.get(name));
			}
			log.info("User defined properties loaded!");
		} catch (IOException e) {
			log.info("No user defined properties!");
		}
	}
}
