package pl.edu.agh.two.interfaces;

import java.io.Serializable;
import java.util.Map;


public interface IMetadata extends Serializable {
	public Map<String, String> getMap();
	public void addValue(String key, String value);
	public String getValue(String key);
}
