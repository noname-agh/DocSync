package pl.edu.agh.two.log;


public interface ILogger {

	public void info(String msg, boolean viewable);
	public void info(String msg, Throwable t, boolean viewable);
	
	public void warn(String msg, boolean viewable);
	public void warn(String msg, Throwable t, boolean viewable);
	
	public void debug(String msg, boolean viewable);
	public void debug(String msg, Throwable t, boolean viewable);
	
	public void error(String msg, boolean viewable);
	public void error(String msg, Throwable t, boolean viewable);
}
