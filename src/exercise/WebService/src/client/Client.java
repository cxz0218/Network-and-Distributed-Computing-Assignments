package client;

import java.util.Scanner;

/**
 * This is client end to use the service published.
 *
 * @author Li Changjun
 * @see WebService
 * @see WebServiceImpl
 */
public class Client {
    private static WebService service;
    private static String[] cmd;

    public static void main(String[] args) {
        WebServiceImpl webService = new WebServiceImpl();
        service = webService.getWebServiceImpl();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (command.equals("quit")) {
                break;
            }
            cmd = command.split(" ");
            run();
        }
    }

    /**
     * This is the main method to handler the request initiated by the client.
     */
    private static void run() {
        switch (cmd[0]) {
            case "register": {
                if (cmd.length != 3) {
                    System.out.println("Invalid command.");
                    break;
                }
                System.out.println(service.register(cmd[1], cmd[2]));
            }
            case "add": {
                if (cmd.length != 5) {
                    System.out.println("Invalid command.");
                    break;
                }
                System.out.println(service.addItem(cmd[1], cmd[2], cmd[3], cmd[4]));
            }
            case "clear": {
                if (cmd.length != 2) {
                    System.out.println("Invalid command.");
                    break;
                }
                System.out.println(service.clearAll(cmd[1]));
            }
            case "query": {
                if (cmd.length != 4) {
                    System.out.println("Invalid command.");
                    break;
                }
                System.out.println(service.queryItem(cmd[1], cmd[2], cmd[3]));
            }
            case "delete": {
                if (cmd.length != 2) {
                    System.out.println("Invalid command.");
                    break;
                }
                try {
                    int id = Integer.parseInt(cmd[1]);
                    System.out.println(service.deleteItem(id));
                } catch (Exception e) {
                    System.out.println("Invalid item id.");
                }
                break;
            }
            case "help": {
                help();
                break;
            }
        }
    }

    private static void help() {
        System.out.println("1. register: to register a user with username and password\n" +
                "\targs: register <username> <password>\n" +
                "2. add: to add an item to specified user\n" +
                "\targs: add <begin date> <end date> <description> <username>\n" +
                "3. clear: to clear a user's item list\n" +
                "\targs: clear <username>\n" +
                "4. query: to query an item of a user between specified begin date and end date\n" +
                "\targs: query <begin date> <end date> <username>\n" +
                "5. delete: to delete a specified item by its id\n" +
                "\targs: delete <item id>\n" +
                "6. quit: to disconnect with server\n" +
                "\targs: quit\n" +
                "7. help: to print help menu\n" +
                "\targs: help");
    }
}
