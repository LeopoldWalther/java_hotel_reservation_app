package model;

public class Room implements IRoom{

    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;
    private final boolean isFree;

    public Room(String roomNumber, Double price, RoomType roomType, boolean isFree){
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
        this.isFree = isFree;
    }

    @Override
    public String getRoomNumber(){
        return roomNumber;
    }

    @Override
    public Double getRoomPrice(){
        return price;
    }

  @Override
    public RoomType getRoomType(){
        return roomType;
    }

    @Override
    public boolean isFree(){
        return isFree;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + ", Price: " + price + ", Room Type: " + roomType;
    }

}
