/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.interfaces;

import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.AlreadyConnected;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.FailedToConnectException;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.NotConnected;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;

/**
 *
 * @author Aanthony
 */
public interface CommandsIface {
    public void _connect(ClientSocket clientSocket) throws AlreadyConnected, FailedToConnectException;
    public void _quit(ClientSocket clientSocket) throws NotConnected;
    public void _who(ClientSocket clientSocket) throws NotConnected;
    public void _checkIfConnected(ClientModel clientModel)throws AlreadyConnected ;
    public void _talk(ClientSocket clientSocket, String message) throws NotConnected;
}
