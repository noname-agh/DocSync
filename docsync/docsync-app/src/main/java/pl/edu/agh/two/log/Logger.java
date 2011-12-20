package pl.edu.agh.two.log;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.IconUIResource;

import org.slf4j.LoggerFactory;

public class Logger implements ILogger {

	private static final String LOG_FILE = System.getProperty("user.dir") + "/log.out";	
	private static org.slf4j.Logger log;
	private JFrame frame;
	
	public static final List<String> logList = new LinkedList<String>();

	public Logger(String name, JFrame frame) {
		this.frame = frame;
		Logger.log = LoggerFactory.getLogger(name);
	}

	public Logger(Class clazz, JFrame frame) {
		this.frame = frame;
		Logger.log = LoggerFactory.getLogger(clazz);
	}

	public static List<String> readLogFile() {
		List<String> logList = new LinkedList<String>();
		try {
			FileInputStream fstream = new FileInputStream(LOG_FILE);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				Logger.logList.add(strLine);
			}
			in.close();
		} catch (Exception ex) {
			Logger.log.error(ex.getMessage(), true);
		}
		return logList;
	}
	
	public static List<String> getLogList() {
		return Logger.logList;
	}

	@Override
	public void info(String msg, boolean viewable) {
		Logger.logList.add(new Date() + " [INFO] " + msg);
		Logger.log.info(msg);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void info(String msg, Throwable t, boolean viewable) {
		Logger.logList.add(new Date() + " [INFO] " + msg);
		Logger.log.info(msg, t);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void warn(String msg, boolean viewable) {
		Logger.logList.add(new Date() + " [WARN] " + msg);
		Logger.log.warn(msg);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void warn(String msg, Throwable t, boolean viewable) {
		Logger.logList.add(new Date() + " [WARN] " + msg);
		Logger.log.warn(msg, t);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void debug(String msg, boolean viewable) {
		Logger.logList.add(new Date() + " [DEBUG] " + msg);
		Logger.log.debug(msg);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg);
		}
	}

	@Override
	public void debug(String msg, Throwable t, boolean viewable) {
		Logger.logList.add(new Date() + " [DEBUG] " + msg);
		Logger.log.debug(msg, t);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg);
		}
	}

	@Override
	public void error(String msg, boolean viewable) {
		Logger.logList.add(new Date() + " [ERROR] " + msg);
		Logger.log.error(msg);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void error(String msg, Throwable t, boolean viewable) {
		Logger.logList.add(new Date() + " [ERROR] " + msg);
		Logger.log.error(msg, t);
		if (viewable) {
			JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
