package pl.edu.agh.two.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Properties properties;

	public static ConfigReader getInstance() {
		return ourInstance;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	private ConfigReader() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			log.error("Error", e);
		}
	}
}
