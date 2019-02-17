/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server.util;

/**
 *
 * @author Aanthony
 */
public class Constants {
    public static final int MAIN_SERVER_PORT = 2000;
    public static String SERVER_WELCOME_STRING ="Hello Server\nmanual:\n"+
            "1.\t_shutdown : pour arrêter le serveur..\n" +
            "2.\t_kill <name> : coupe la connexion du client correspondant au <surnom> et en informe les\n" +
            "clients restants.\n" +
            "3.\t_who : demande la liste des utilisateurs connectés";

    public static String SERVER_WELCOME_STRING_MANUAL ="manual:\n"+
            "1.\t_shutdown : pour arrêter le serveur..\n" +
            "2.\t_kill <name> : coupe la connexion du client correspondant au <surnom> et en informe les\n" +
            "clients restants.\n" +
            "3.\t_who : demande la liste des utilisateurs connectés";

}
