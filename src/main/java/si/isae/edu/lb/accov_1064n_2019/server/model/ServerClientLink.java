package si.isae.edu.lb.accov_1064n_2019.server.model;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Anthony
 *will be creating threads from the serverModel each thread keeps the conx alive leets tryy
 * and will contain the logic
 */
public class ServerClientLink extends Thread{
    private ServerModel serverModel;
    private ClientSocket clientSocket;
    private boolean isConnected = true;
    public ServerClientLink(ServerModel serverModel, ClientSocket clientSocket){
        this.serverModel = serverModel;
        this.clientSocket = clientSocket;
        this.start();
    }
    @Override
    public void run() {
        //logic after creating the socket with the client on io
        while (isConnected) {
            ClientModel currentModel = clientSocket.getClientModel();

            if (currentModel.getCommand().equals(ServerCommands._CONNECT.toString())) {
                System.out.println("connecting client "+ clientSocket.getClientModel().getName());
                try {
                    clientSocket.getSocket().setKeepAlive(true);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                serverModel.informAboutNewClient(clientSocket.getClientModel().getName());
                serverModel.getClientsLinks().add(this);
                System.out.println("connecting client "+ currentModel.getName()+" success");
                currentModel.setCommand(ServerCommands._UNKNOWN.toString());
                continue;
            }

            if (currentModel.getCommand().equals(ServerCommands._WHO.toString())) {
                System.out.println("who client");
                String message = serverModel.informAboutWho();
                currentModel.setMessageFromServer(message);
                sendDataToClient();
                System.out.println("end who client");
                currentModel.setCommand(ServerCommands._UNKNOWN.toString());
                continue;
            }

            if (currentModel.getCommand().equals(ServerCommands._QUIT.toString())) {
                clientSocket.closeSocket();
                serverModel.removeClosedSocket(clientSocket.getClientModel().getName());
                serverModel.informAbouteftClient(clientSocket.getClientModel().getName());
                continue;
            }
            if (currentModel.getCommand().equals(ServerCommands._SERVER_TO_CLIENT.toString())) {
                System.out.println("sending message to client");
                String message = serverModel.informAboutWho();
                currentModel.setMessageFromServer(message);
                sendDataToClient();
                System.out.println("end sending message client");
                currentModel.setCommand(ServerCommands._UNKNOWN.toString());
                continue;
            }

            //we set the status to unknown so we don't enter in the other statuses
            //else we stay in the while connected
        }
    }


    public void sendMessageFromServer(String message){
        clientSocket.getClientModel().setCommand(ServerCommands._SERVER_TO_CLIENT.toString());
        clientSocket.getClientModel().setMessageFromServer(message);
    }

    //get socket readers and writers from DR pascal course youtube for Client Model
    private ClientModel getInput(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        ois = new  ObjectInputStream(clientSocket.getInputStream());
        return  (ClientModel) ois.readObject();
    }
    //changed in it so i can send the object ClientModel
    public ObjectOutputStream getOutput(Socket clientSocket)throws IOException{
        return new ObjectOutputStream(clientSocket.getOutputStream());
    }


    private void sendDataToClient(){
        try {
            ObjectOutputStream oos = getOutput(clientSocket.getSocket());
            oos.writeObject(clientSocket.getClientModel());
            oos.flush();// never forget to flush kids
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public ClientSocket getClientSocket() {
        return clientSocket;
    }
}
