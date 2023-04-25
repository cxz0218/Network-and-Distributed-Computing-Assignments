package server;

import javax.xml.ws.Endpoint;

/**
 * The driver of server.
 *
 * @author Li Changjun
 */
@javax.jws.WebService(name = "WebService", portName = "servicePort", targetNamespace = "http://www.service.com")
public class WebServiceServer {
    public static void main(String[] args) {
        WebService webService = new WebServiceImpl();
        Endpoint.publish("http://localhost:8080/web-service", webService);
        System.out.println("Server published.");
    }
}
