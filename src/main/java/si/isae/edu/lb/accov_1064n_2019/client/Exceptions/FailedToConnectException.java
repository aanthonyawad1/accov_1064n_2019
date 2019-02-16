/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.Exceptions;

/**
 *
 * @author Aanthony
 */
public class FailedToConnectException extends Exception{
       public String error = "failed to connect to server";
    public FailedToConnectException(){
    }
    public String toString(){
        return error;
    }
}
