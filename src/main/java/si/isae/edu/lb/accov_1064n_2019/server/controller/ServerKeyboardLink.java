package si.isae.edu.lb.accov_1064n_2019.server.controller;

import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;
import si.isae.edu.lb.accov_1064n_2019.server.model.RoomServerModel;
import si.isae.edu.lb.accov_1064n_2019.server.model.ServerModel;
import si.isae.edu.lb.accov_1064n_2019.server.util.Constants;

import java.util.Scanner;

import static si.isae.edu.lb.accov_1064n_2019.server.util.Constants.SERVER_WELCOME_STRING;
import static si.isae.edu.lb.accov_1064n_2019.server.util.Constants.SERVER_WELCOME_STRING_MANUAL;

/**
 *
 * @author Aanthony
 */
public class ServerKeyboardLink implements  Runnable {
    private Object sync = new Object();
    private String command="";
    private Scanner keyboard;
    private ServerModel serverModel;

    // room static params
    private static int roomPortCounter = Constants.MAIN_SERVER_PORT;

    public ServerKeyboardLink(ServerModel serverModel){
        this.serverModel = serverModel;
    }
    @Override
    public void run() {
        //logic of the function
        keyboard = new Scanner(System.in);
        System.out.println(SERVER_WELCOME_STRING);
        while(true){
            command = keyboard.next();
            if(command.equals(ServerCommands._KILL.toString().toLowerCase())){
                System.out.println("you monster who are you killing ?");
                command = keyboard.next();
                serverModel.killClientByName(command);
                continue;
            }
            else if(command.equals(ServerCommands._SHUTDOWN.toString().toLowerCase())){
                serverModel.disconnectAllActiveThreadsThenDie();
                System.exit(-1);
                continue;
            }
            else if(command.equals(ServerCommands._WHO.toString().toLowerCase())){
                String message = serverModel.informAboutWho();
                System.out.println(message);
                continue;
            }
            else if(command.equals(ServerCommands._ROOM_CREATE.toString().toLowerCase())){
                int roomPort = ++roomPortCounter ;
                String roomName = keyboard.next();
                if(roomName != null) {
                    System.out.println("room name cant't be null");
                }
                RoomServerModel roomServerModel = new RoomServerModel(roomName,roomPort);
                String rooms = serverModel.addRoom(roomServerModel);
                if(rooms != null && rooms.length() > 0)
                    System.out.println("\n\n available rooms:\n "+rooms);
                else
                    System.out.println("\n\n available rooms:\n "+rooms);
                continue;
            }
            else {
                System.out.println("UNKNOWN COMMAND");
                System.out.println(SERVER_WELCOME_STRING_MANUAL);
            }
        }

    }
}
