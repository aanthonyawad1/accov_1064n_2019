package si.isae.edu.lb.accov_1064n_2019.server.exceptions;

public class RoomCantCreateAnotherRoomException extends Exception {
    public String error = "Room cannot create another room, only the main server can use this functionality";
    public RoomCantCreateAnotherRoomException(){
    }
    public String toString(){
        return error;
    }}
