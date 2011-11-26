package pl.edu.agh.two.ws;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapts any instance of IMetadata to CloudMetadata,
 * which can be marshaled using JAXB.
 * 
 */
public class IMetadataXMLAdapter extends XmlAdapter<CloudMetadata, IMetadata> {

	@Override
	public IMetadata unmarshal(CloudMetadata v) throws Exception {
		return v;
	}

	@Override
	public CloudMetadata marshal(IMetadata v) throws Exception {
		if (v == null) {
			return null;
		}
		
		if (v instanceof CloudMetadata) {
			return (CloudMetadata) v;
		} else {
			CloudMetadata cloudMeta = new CloudMetadata();
			cloudMeta.setMap(v.getMap());
			cloudMeta.setVersion(v.getVersion());
			return cloudMeta;
		}
	}
}
