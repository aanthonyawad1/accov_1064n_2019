/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.util;

import si.isae.edu.lb.accov_1064n_2019.client.ClientCommands;

/**
 *
 * @author Aanthony
 */
public class ClientUtils {
    public static ClientCommands checkClientCommand( String command){
        if(command.toUpperCase().equals(ClientCommands._QUIT.toString().toUpperCase())){
            return ClientCommands._QUIT;
        }else if(command.toUpperCase().equals(ClientCommands._WHO.toString().toUpperCase())) {
            return ClientCommands._WHO;
        }else if(command.toUpperCase().equals(ClientCommands._CONNECT.toString().toUpperCase())) {
            return ClientCommands._CONNECT;
        }
        return ClientCommands._UNKNOWN ;
    }
    
    public static String CLIENT_WELCOME_STRING ="Hello Client\nmanual:\n"+ 
                "1.\t_connect : se connecte au serveur et se déclare avec le surnom fourni en paramètre.\n" +
                "2.\t_quit : quitte. Le client quitte et le signale au serveur.\n" +
                "3.\t_who : demande au serveur la liste des utilisateurs connectés";
    
}
