/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client;

/**
 *
 * @author Aanthony
 */
public enum ClientCommands {
    _UNKNOWN("error"),
    _CONNECT("_connect"),
    _QUIT("_quit"),
    _TALK("_talk"),
    _WHO("_who"),
    _ROOM_JOIN("_join");
    
    private final String command;

    /**
     * @param c the base command the user wants to execute
     */
    ClientCommands(final String c) {
        this.command = c;
    }


}
