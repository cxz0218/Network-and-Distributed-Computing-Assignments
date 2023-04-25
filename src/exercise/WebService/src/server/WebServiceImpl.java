package server;

import server.bean.Item;
import server.bean.User;
import server.database.DatabaseOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@javax.jws.WebService(endpointInterface = "server.WebService")
public class WebServiceImpl implements WebService {

    private final DatabaseOperation dao;

    private final SimpleDateFormat sdf = new SimpleDateFormat("M-d-H:m");

    public WebServiceImpl() {
        dao = new DatabaseOperation();
    }

    @Override
    public String register(String username, String password) {
        if (dao.checkIfUserExists(username)) {
            return "Use already exists.";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        dao.saveUser(user);
        return "Registration succeeded.";
    }

    @Override
    public String addItem(String begin, String end, String description, String username) {
        if (!dao.checkIfUserExists(username)) {
            return "User not exists.";
        }
        Item item = new Item();
        Date start;
        Date finish;
        try {
            start = sdf.parse(begin);
            finish = sdf.parse(end);
            item.setBegin(start);
            item.setEnd(finish);
            item.setDescription(description);
            dao.saveItem(item);
        } catch (Exception e) {
            return "Invalid date format.";
        }
        return "Succeeded added.";
    }

    @Override
    public String queryItem(String begin, String end, String username) {
        Date start;
        Date finish;
        StringBuilder sb = new StringBuilder();
        try {
            start = sdf.parse(begin);
            finish = sdf.parse(end);
            List<Item> itemList = dao.getItemsByUsername(username);

            if (itemList == null) {
                return "User not exists.";
            }
            for (Item item : itemList) {
                if(item.getBegin().after(start) && item.getEnd().before(finish)){
                    sb.append(item.toString()).append("\n");
                }
            }
        } catch (Exception e) {
            return "Invalid date format.";
        }
        return sb.toString();
    }

    @Override
    public String deleteItem(int id) {
        Item item = dao.getItemById(id);
        if(item == null){
            return "Item not exists.";
        }
        dao.deleteItem(id);
        return "Deletion succeeded.";
    }

    @Override
    public String clearAll(String username) {
        if(!dao.checkIfUserExists(username)){
            return "User not exists.";
        }
        dao.clearItemsByUsername(username);
        return "Items cleared.";
    }
}
