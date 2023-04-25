package server.database;

import server.Encoder;
import server.bean.Item;
import server.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the {@code DAO} layer, This is a class defined in
 * the dao layer, which indicates that it will be responsible for direct
 * interaction with the database.
 *
 * @author Li Changjun
 * @see SimulationDatabase
 */
public class DatabaseOperation {
    private final SimulationDatabase database;

    public DatabaseOperation() {
        database = SimulationDatabase.getInstance();
    }

    /**
     * To get items of a specified user.
     *
     * @param username The user's username
     * @return Item list of the user.
     */
    public List<Item> getItemsByUsername(String username) {
        return database.usernameItemsMap.getOrDefault(username, null);
    }

    /**
     * To check whether the given username is registered.
     *
     * @param username username
     * @return {@code true} if registered, {@code false} if not.
     */
    public boolean checkIfUserExists(String username) {
        return database.usernameUserMap.containsKey(username);
    }

    /**
     * To get a user by his username.
     *
     * @param username username.
     * @return Specified user or {@code null} if he doesn't exist.
     */
    public User getUserByUsername(String username) {
        return database.usernameUserMap.getOrDefault(username, null);
    }

    /**
     * To save a user.
     *
     * @param user user.
     */
    public void saveUser(User user) {
        user.setId(database.userIncreasingId.addAndGet(1));
        user.setPassword(Encoder.encode(user.getPassword()));
        database.usernameUserMap.put(user.getUsername(), user);
        database.users.add(user);
    }

    /**
     * To save am item.
     *
     * @param item item.
     */
    public void saveItem(Item item) {
        item.setId(database.itemIncreasingId.addAndGet(1));
        database.usernameItemsMap.get(item.getUsername()).add(item);
    }

    /**
     * To validate whether the password matches the username.
     *
     * @param username username.
     * @param password password.
     * @return {@code false} if username is not registered or password mismatches.
     * {@code true} if it does.
     */
    public boolean validateUser(String username, String password) {
        if (!checkIfUserExists(username)) {
            return false;
        }
        User user = getUserByUsername(username);
        return Encoder.match(password, user.getPassword());
    }

    /**
     * To get an item by its id.
     *
     * @param id item id
     * @return {@code null} if id doesn't exist, the corresponding item if it does.
     */
    public Item getItemById(int id) {
        for (Item item : database.items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * To delete an item by its id
     *
     * @param id id
     */
    public void deleteItem(int id) {
        Item item = getItemById(id);
        if (item != null) {
            database.items.remove(item);
            String username = item.getUsername();
            List<Item> itemList = getItemsByUsername(username);
            itemList.remove(item);
        }
    }

    /**
     * To clear up a user's item list by his username
     *
     * @param username username
     */
    public void clearItemsByUsername(String username) {
        if (checkIfUserExists(username)) {
            List<Item> itemList = database.usernameItemsMap.get(username);
            database.items.removeAll(itemList);
            database.usernameItemsMap.put(username, new ArrayList<>());
        }
    }
}
