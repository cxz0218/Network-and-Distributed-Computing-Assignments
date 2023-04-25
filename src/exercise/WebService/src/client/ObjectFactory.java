
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Register_QNAME = new QName("http://www.service.com", "register");
    private final static QName _ClearAll_QNAME = new QName("http://www.service.com", "clearAll");
    private final static QName _ClearAllResponse_QNAME = new QName("http://www.service.com", "clearAllResponse");
    private final static QName _DeleteItemResponse_QNAME = new QName("http://www.service.com", "deleteItemResponse");
    private final static QName _AddItemResponse_QNAME = new QName("http://www.service.com", "addItemResponse");
    private final static QName _DeleteItem_QNAME = new QName("http://www.service.com", "deleteItem");
    private final static QName _RegisterResponse_QNAME = new QName("http://www.service.com", "registerResponse");
    private final static QName _QueryItem_QNAME = new QName("http://www.service.com", "queryItem");
    private final static QName _QueryItemResponse_QNAME = new QName("http://www.service.com", "queryItemResponse");
    private final static QName _AddItem_QNAME = new QName("http://www.service.com", "addItem");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddItem }
     * 
     */
    public AddItem createAddItem() {
        return new AddItem();
    }

    /**
     * Create an instance of {@link QueryItem }
     * 
     */
    public QueryItem createQueryItem() {
        return new QueryItem();
    }

    /**
     * Create an instance of {@link QueryItemResponse }
     * 
     */
    public QueryItemResponse createQueryItemResponse() {
        return new QueryItemResponse();
    }

    /**
     * Create an instance of {@link DeleteItem }
     * 
     */
    public DeleteItem createDeleteItem() {
        return new DeleteItem();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link AddItemResponse }
     * 
     */
    public AddItemResponse createAddItemResponse() {
        return new AddItemResponse();
    }

    /**
     * Create an instance of {@link DeleteItemResponse }
     * 
     */
    public DeleteItemResponse createDeleteItemResponse() {
        return new DeleteItemResponse();
    }

    /**
     * Create an instance of {@link ClearAllResponse }
     * 
     */
    public ClearAllResponse createClearAllResponse() {
        return new ClearAllResponse();
    }

    /**
     * Create an instance of {@link ClearAll }
     * 
     */
    public ClearAll createClearAll() {
        return new ClearAll();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "clearAll")
    public JAXBElement<ClearAll> createClearAll(ClearAll value) {
        return new JAXBElement<ClearAll>(_ClearAll_QNAME, ClearAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "clearAllResponse")
    public JAXBElement<ClearAllResponse> createClearAllResponse(ClearAllResponse value) {
        return new JAXBElement<ClearAllResponse>(_ClearAllResponse_QNAME, ClearAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "deleteItemResponse")
    public JAXBElement<DeleteItemResponse> createDeleteItemResponse(DeleteItemResponse value) {
        return new JAXBElement<DeleteItemResponse>(_DeleteItemResponse_QNAME, DeleteItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "addItemResponse")
    public JAXBElement<AddItemResponse> createAddItemResponse(AddItemResponse value) {
        return new JAXBElement<AddItemResponse>(_AddItemResponse_QNAME, AddItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "deleteItem")
    public JAXBElement<DeleteItem> createDeleteItem(DeleteItem value) {
        return new JAXBElement<DeleteItem>(_DeleteItem_QNAME, DeleteItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "queryItem")
    public JAXBElement<QueryItem> createQueryItem(QueryItem value) {
        return new JAXBElement<QueryItem>(_QueryItem_QNAME, QueryItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "queryItemResponse")
    public JAXBElement<QueryItemResponse> createQueryItemResponse(QueryItemResponse value) {
        return new JAXBElement<QueryItemResponse>(_QueryItemResponse_QNAME, QueryItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.service.com", name = "addItem")
    public JAXBElement<AddItem> createAddItem(AddItem value) {
        return new JAXBElement<AddItem>(_AddItem_QNAME, AddItem.class, null, value);
    }

}
