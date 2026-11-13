Core requirements:

1. There will be one Parking Lot with multiple floors. Each floor will have multiple parking spots.
2. These are the supported vehicle types: Car (medium), Bike (small), and Truck (large).
3. There will be entry and exit gates in the Parking Lot.
4. At entry gate: Parking spot will be assigned and Parking ticket will be issued.
5. Parking ticket will track the vehicle, parking spot assigned to this vehicle, entry time, exit time and fee paid.
6. At exit gate: Fee will be calculated based on the no. of hours parked and the fee strategy.
7. Different fee strategies are supported like: flat fee (based on an hourly rate), or vehicle based fee (different hourly rates for different vehicle sizes).
8. Proper error should be handled for:

- Parking full
- Invalid ticket

Core entities:

- Parking Lot
- Parking Floor
- Parking Spot
- Parking Ticket
- Vehicle
- Entry Gate
- Exit Gate

Enums:

- Vehicle Size

Strategies:

- Fee strategy
