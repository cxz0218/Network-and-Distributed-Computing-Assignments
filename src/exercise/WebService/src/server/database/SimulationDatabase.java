package server.database;

import server.bean.Item;
import server.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a simulated database to maintain all the information of user and item
 * in this system. Only the class in the {@code DAO} layer is accessible to its parameters.
 * And it is implemented in singleton mode.
 *
 * @author Li Changjun
 */
public class SimulationDatabase {
    private volatile static SimulationDatabase simulationDatabase;
    protected AtomicInteger userIncreasingId = new AtomicInteger(0);
    protected AtomicInteger itemIncreasingId = new AtomicInteger(0);
    protected List<Item> items = new ArrayList<>();
    protected List<User> users = new ArrayList<>();
    protected Map<String, User> usernameUserMap = new HashMap<>();
    protected Map<String, Integer> usernameIdMap = new HashMap<>();
    protected Map<Integer, Item> idItemMap = new HashMap<>();
    protected Map<String, List<Item>> usernameItemsMap = new HashMap<>();

    private SimulationDatabase() {
    }

    public static SimulationDatabase getInstance() {
        if (simulationDatabase == null) {
            simulationDatabase = new SimulationDatabase();
        }
        return simulationDatabase;
    }

}
