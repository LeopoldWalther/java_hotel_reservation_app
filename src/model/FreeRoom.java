package model;

public class FreeRoom extends Room{


    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.00, roomType, true);
    }

    @Override
    public String toString(){
        return "test";
    }
}
