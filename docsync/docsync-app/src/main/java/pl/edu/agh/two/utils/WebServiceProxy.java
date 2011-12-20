package pl.edu.agh.two.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;

public class WebServiceProxy<T> implements InvocationHandler {

	private static final ILogger LOGGER = LoggerFactory.getLogger(WebServiceProxy.class, DocSyncGUI.getFrame());
	private URL wsdlDocumentLocation;
	private QName serviceName;
	private Class<T> serviceEndpointInterface;
	private T serviceProxy;

	public static <T> T create(URL wsdlDocumentLocation, QName serviceName, Class<T> serviceEndpointInterface) {
		WebServiceProxy wsp = new WebServiceProxy<T>(wsdlDocumentLocation, serviceName, serviceEndpointInterface);
		return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { serviceEndpointInterface }, wsp);
	}

	public WebServiceProxy(URL wsdlDocumentLocation, QName serviceName,
			Class<T> serviceEndpointInterface) {
		this.wsdlDocumentLocation = wsdlDocumentLocation;
		this.serviceName = serviceName;
		this.serviceEndpointInterface = serviceEndpointInterface;
		try {
			this.serviceProxy = createServiceProxy();
		} catch (WebServiceException ex) {
			// We don't propagate web service exceptions until methods are
			// invoked
			LOGGER.warn("Error connecting the server.\n Check your connection.", ex, true);
			this.serviceProxy = null;
		}
	}

	private T getServiceProxy() throws WebServiceException {
		if (serviceProxy == null) {
			serviceProxy = createServiceProxy();
		}
		return serviceProxy;
	}

	private T createServiceProxy() throws WebServiceException {
		Service service = Service.create(wsdlDocumentLocation, serviceName);
		return service.getPort(serviceEndpointInterface);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return method.invoke(getServiceProxy(), args);
	}
}
