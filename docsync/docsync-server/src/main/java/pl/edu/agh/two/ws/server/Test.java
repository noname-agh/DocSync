package pl.edu.agh.two.ws.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.RSSItem;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException {
		Service service = Service.create(new URL("http://localhost:8080/docsync/CloudStorage?wsdl"), new QName("http://server.ws.two.agh.edu.pl/", "CloudStorage"));
		CloudStorage storage = service.getPort(CloudStorage.class);
		CloudFile cl = new CloudFile();
		cl.setContent("ala123".getBytes());
		cl.setName("name");
		storage.addFile(cl);
		List<CloudFile> list = storage.getAllFilesWithContent();
		System.out.println(list.get(0).getName());
		RSSItem item = new RSSItem();
//		RSSReader.test();
		item.setChannelAddress("ala123");
		item.setDate(new Date(123123));
		item.setDescription("ala12354562");
		item.setGuid("guid");
		item.setLink("liiiink");
		item.setReaded(false);
		storage.addRSSItem(item);
		List<RSSItem> list2 = storage.getRSSItems();
		System.out.println(list2.get(0).getChannelAddress());
 		//CloudFile file = storage.getFiles().get(0);
		//System.out.format("%s%n", file.getContent().length);
	}

}
