package ParkingLot.model;

import ParkingLot.model.vehicle.Vehicle;
import java.util.Date;

public class ParkingTicket {

    private final String id;
    private final Vehicle vehicle;
    private final ParkingSpot Vehicle;
    private final Date entryTimeStamp;
    private Date exitTimeStamp;
}
