
package client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WebServiceImpl", targetNamespace = "http://server", wsdlLocation = "http://localhost:8080/web-service")
public class WebServiceImpl
    extends Service
{

    private final static URL WEBSERVICEIMPL_WSDL_LOCATION;
    private final static WebServiceException WEBSERVICEIMPL_EXCEPTION;
    private final static QName WEBSERVICEIMPL_QNAME = new QName("http://server", "WebServiceImpl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/web-service");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WEBSERVICEIMPL_WSDL_LOCATION = url;
        WEBSERVICEIMPL_EXCEPTION = e;
    }

    public WebServiceImpl() {
        super(__getWsdlLocation(), WEBSERVICEIMPL_QNAME);
    }

    public WebServiceImpl(WebServiceFeature... features) {
        super(__getWsdlLocation(), WEBSERVICEIMPL_QNAME, features);
    }

    public WebServiceImpl(URL wsdlLocation) {
        super(wsdlLocation, WEBSERVICEIMPL_QNAME);
    }

    public WebServiceImpl(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WEBSERVICEIMPL_QNAME, features);
    }

    public WebServiceImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebServiceImpl(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WebService
     */
    @WebEndpoint(name = "WebServiceImpl")
    public WebService getWebServiceImpl() {
        return super.getPort(new QName("http://server", "WebServiceImpl"), WebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebService
     */
    @WebEndpoint(name = "WebServiceImpl")
    public WebService getWebServiceImpl(WebServiceFeature... features) {
        return super.getPort(new QName("http://server", "WebServiceImpl"), WebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WEBSERVICEIMPL_EXCEPTION!= null) {
            throw WEBSERVICEIMPL_EXCEPTION;
        }
        return WEBSERVICEIMPL_WSDL_LOCATION;
    }

}