/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server;
import java.util.Scanner;
import si.isae.edu.lb.accov_1064n_2019.server.model.ServerModel;
import si.isae.edu.lb.accov_1064n_2019.server.util.Constants;

/**
 *
 * @author Aanthony
 */
public class ServerSide {
    // this is my server 
    public static void main(String [] args) {
        final ServerModel serverModel = new ServerModel("server",Constants.MAIN_SERVER_PORT);
        System.out.println("Start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverModel.listen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start(); 
        System.out.println("end");
//          new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                serverModel.talk(new Scanner(System.in));
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();

         
         
        
        
       
    }
}
