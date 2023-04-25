package server.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * This class represents the entity of to do item.
 *
 * @author Li Changjun
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoList")
public class Item {
    private int id;
    private String username;
    private String description;
    private Date begin;
    private Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
