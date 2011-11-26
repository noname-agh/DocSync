package pl.edu.agh.two.file;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.two.ws.IMetadata;

public class Metadata implements IMetadata {
	private static final long serialVersionUID = 1787742459685153165L;
	private Map<String, String> map = new LinkedHashMap<String, String>();
	private long version;

	@Override
	public Map<String, String> getMap() {
		return map;
	}
	
	@Override
	public void put(String key, String value) {
		map.put(key, value);
	}
	
	@Override
	public String get(String key) {
		if (!map.containsKey(key))
			return null;
		return map.get(key);
	}

	@Override
	public long getVersion() {
		return version;
	}

	@Override
	public void setVersion(long version) {
		this.version = version;
	}

}
