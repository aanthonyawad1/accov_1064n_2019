package si.isae.edu.lb.accov_1064n_2019.client.Exceptions;
/**
 *
 * @author Aanthony
 */
public class MessageNotRecievedException extends Exception {
    public String error = "Message not Recieved yet from server exception";
    public MessageNotRecievedException(){
    }
    public String toString(){
        return error;
    }
}
