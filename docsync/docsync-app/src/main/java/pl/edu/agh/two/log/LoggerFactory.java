package pl.edu.agh.two.log;

import javax.swing.JFrame;

public class LoggerFactory {
	
	private static ILogger logger;
	
	public static ILogger getLogger(String name, JFrame frame) {
		if(logger == null) {
			logger = new Logger(name, frame);
		}
		return logger;
	}
	
	public static ILogger getLogger(Class clazz, JFrame frame) {
		if(logger == null) {
			logger = new Logger(clazz, frame);
		}
		return logger;
	}
}
