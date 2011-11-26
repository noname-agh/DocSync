package pl.edu.agh.two.ws;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(IMetadataXMLAdapter.class)
public interface IMetadata extends Serializable {
	
	/**
	 * Returns a version of metadata.
	 * 
	 * @return version of metadata
	 */
	public long getVersion();
	
	/**
	 * Sets the version to given value.
	 * 
	 * @param version new version value
	 */
	public void setVersion(long version);

	/**
	 * Returns a map underlying the metadata instance. 
	 * Changes to the map are reflected in metadata instance and vice-versa.
	 * 
	 * @return map of values stored in metadata
	 */
	public Map<String, String> getMap();

	/**
	 * Associates the specified value with given key.
	 * 
	 * @param key key
	 * @param value value
	 */
	public void put(String key, String value);

	/**
	 * Returns a value associated with given key
	 * or null if there is no mapping present for given key.
	 * 
	 * @param key key
	 * @return associated value or null
	 */
	public String get(String key);
}
