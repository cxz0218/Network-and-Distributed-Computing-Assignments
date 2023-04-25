package server;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@javax.jws.WebService(name = "WebService", targetNamespace = "http://www.service.com")
public interface WebService {
    /**
     * To register a user with username and password.
     *
     * @param username username
     * @param password password
     * @return corresponding message.
     */
    @WebMethod
    @WebResult
    @RequestWrapper(localName = "register", targetNamespace = "http://www.service.com", className = "Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://www.service.com", className = "RegisterResponse")
    @Action(input = "http://www.service.com/WebService/registerRequest", output = "http://www.service.com/WebService/registerResponse")
    String register(String username, String password);

    /**
     * To add an item to a user's item list.
     *
     * @param begin       begin date of the item
     * @param end         end date of the item
     * @param description item description
     * @param username    username
     * @return corresponding message.
     */
    @WebMethod
    @WebResult
    @RequestWrapper(localName = "addItem", targetNamespace = "http://www.service.com", className = "AddItem")
    @ResponseWrapper(localName = "addItemResponse", targetNamespace = "http://www.service.com", className = "AddItemResponse")
    @Action(input = "http://www.service.com/WebService/addItemRequest", output = "http://www.service.com/WebService/addItemResponse")
    String addItem(String begin, String end, String description, String username);

    /**
     * To query items in given time internal and username.
     *
     * @param begin    begin date
     * @param end      end date
     * @param username username
     * @return corresponding message.
     */
    @WebMethod
    @WebResult
    @RequestWrapper(localName = "queryItem", targetNamespace = "http://www.service.com", className = "QueryItem")
    @ResponseWrapper(localName = "queryItemResponse", targetNamespace = "http://www.service.com", className = "QueryItemResponse")
    @Action(input = "http://www.service.com/WebService/queryItemRequest", output = "http://www.service.com/WebService/queryItemResponse")
    String queryItem(String begin, String end, String username);

    /**
     * To delete an item by its id.
     *
     * @param id id of the item
     * @return corresponding message.
     */
    @WebMethod
    @WebResult
    @RequestWrapper(localName = "deleteItem", targetNamespace = "http://www.service.com", className = "DeleteItem")
    @ResponseWrapper(localName = "deleteItemResponse", targetNamespace = "http://www.service.com", className = "DeleteItemResponse")
    @Action(input = "http://www.service.com/WebService/deleteItemRequest", output = "http://www.service.com/WebService/deleteItemResponse")
    String deleteItem(int id);

    /**
     * To clear up a user's item list.
     *
     * @param username username
     * @return corresponding message.
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "clearAll", targetNamespace = "http://www.service.com", className = "ClearAll")
    @ResponseWrapper(localName = "clearAllResponse", targetNamespace = "http://www.service.com", className = "ClearAllResponse")
    @Action(input = "http://www.service.com/WebService/queryItemRequest", output = "http://www.service.com/WebService/queryItemResponse")
    String clearAll(String username);

}
