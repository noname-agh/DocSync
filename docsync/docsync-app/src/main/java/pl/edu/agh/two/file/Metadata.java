package pl.edu.agh.two.file;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.two.interfaces.IMetadata;

public class Metadata implements IMetadata {
	private static final long serialVersionUID = 1787742459685153165L;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Map<String, String> getMap() {
		return map;
	}
	
	public void addValue(String key, String value) {
		map.put(key, value);
	}
	
	public String getValue(String key) {
		if (!map.containsKey(key))
			return null;
		return map.get(key);
	}

}
