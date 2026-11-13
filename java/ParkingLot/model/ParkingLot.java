package ParkingLot.model;

import java.util.ArrayList;
import java.util.HashMap;

import ParkingLot.model.vehicle.Vehicle;

public class ParkingLot {

    private static ParkingLot parkingLot;
    private ArrayList<ParkingFloor> parkingFloors;
    private HashMap<String, ParkingSpot> vehicleSpotMap;
    private HashMap<String, ParkingTicket> parkingTicketMap;

    private ParkingLot() {
        this.parkingFloors = new ArrayList<ParkingFloor>();
        this.vehicleSpotMap = new HashMap<String, ParkingSpot>();
        this.parkingTicketMap = new HashMap<String, ParkingTicket>();
    }

    public static ParkingLot getParkingLot() {}

    public void addFloor(ParkingFloor parkingFloor) {}

    public void parkVehicle(Vehicle vehicle) {}

    public void unParkVehicle(Vehicle vehicle) {}
}
