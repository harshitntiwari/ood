package MovieTicketBookingSystem.model;

public class Movie {
    private String id;
    private String name;
    private int durationInMinutes;

    public Movie(String id, String name, int durationInMinutes) {
        this.id = id;
        this.name = name;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}