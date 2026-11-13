package MovieTicketBookingSystem.model;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private final String id;
    private final String name;
    private final List<Screen> screens;

    public Theater(String id, String name) {
        this.id = id;
        this.name = name;
        screens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }
}