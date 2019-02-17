/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client;

import java.util.Scanner;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.AlreadyConnected;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.FailedToConnectException;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.NotConnected;
import si.isae.edu.lb.accov_1064n_2019.client.interfaces.CommandsIface;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.client.util.ClientUtils;

/**
 *
 * @author Aanthony
 */
public class KeyboardHandler implements Runnable{
    private CommandsIface commandsIface;
    private Object sync = new Object(); ;
    private String command="";
    private Scanner keyboard;
    private ClientSocket clientSocket;
    
    
    public KeyboardHandler(CommandsIface commandsIface,ClientSocket clientSocket) {
        this.commandsIface = commandsIface;
        this.clientSocket = clientSocket;
    }
    
    
    @Override
    public void run() {
        keyboard = new Scanner(System.in);
        System.out.println(ClientUtils.CLIENT_WELCOME_STRING);
        while(true){
            synchronized(sync){
                command = keyboard.next();
                ClientCommands theCommand= ClientUtils.checkClientCommand(command);
                if(theCommand.equals(ClientCommands._WHO)){
                    this.who();
                }else if(theCommand.equals(ClientCommands._QUIT)){
                   this.quit();
                }else if(theCommand.equals(ClientCommands._CONNECT)){
                   this.connect();
                }else {
                    this.elseCommand();
                }
               
            }
        }
    }
    
    //KEYBOARD TYPED FUNCTIONS
    public void connect(){
    try {
        commandsIface._checkIfConnected(clientSocket.getClientModel());
        System.out.println("quelle est ton nom?");
        String name = keyboard.next();
        System.out.println("quelle est ta machine?");
        String machine = keyboard.next();
        int port = 0;
        while(true){//todo fix keyboard infinite next int  System.out.println
            System.out.println("quelle est ta port (integer please)?");
            try{
                port = keyboard.nextInt();
                break;
            }catch(Exception e){
                //do nothing retry if not int 
            }
        }
        clientSocket.getClientModel().build(name, machine , ""+port);
        commandsIface._connect(clientSocket);
    } catch (AlreadyConnected ex) {
        System.out.println(ex.toString());
        clientSocket.getClientModel().setIsConnected(true);
    }
    catch(FailedToConnectException ex){
        System.out.println(ex.toString());
        clientSocket.getClientModel().setIsConnected(false);
    }
    }
    
    public void elseCommand(){
        System.out.println("UNKNOWN COMMAND");
    }

    private void who() {
        try {
        commandsIface._who(clientSocket);
        } catch (NotConnected ex) {
            System.out.println(ex.toString());
        }
    }

    private void quit() {
        try {
            commandsIface._quit(clientSocket);
        } catch (NotConnected ex) {
            System.out.println(ex.toString());
        }
    }

    
}
