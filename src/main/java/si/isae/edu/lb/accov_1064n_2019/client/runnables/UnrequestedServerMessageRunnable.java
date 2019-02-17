package si.isae.edu.lb.accov_1064n_2019.client.runnables;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;

public class UnrequestedServerMessageRunnable implements Runnable{

    private ClientSocket clientSocket;

    public UnrequestedServerMessageRunnable(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while(true){
            synchronized (clientSocket.getSync()){
                clientSocket.readUnrequestedServerMessage();
            }
        }
    }
}