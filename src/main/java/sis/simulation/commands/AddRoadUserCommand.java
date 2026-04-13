package sis.simulation.commands;

import sis.lanes.Direction;
import sis.simulation.Simulation;
import sis.users.Car;
import sis.users.Pedestrian;
import sis.users.RoadUser;

public class AddRoadUserCommand extends Command {
    private String userId;
    private String userType;
    private String startRoad;
    private String endRoad;

    @Override
    public void execute(Simulation simulation) {
        Direction start = Direction.valueOf(startRoad.toUpperCase());
        Direction end = Direction.valueOf(endRoad.toUpperCase());

        RoadUser user = switch(userType) {
            case "car" -> new Car(start, end, userId);
            case "pedestrian" -> new Pedestrian(start, end, userId);
            default -> throw new IllegalArgumentException("Unexpected RoadUser: " + userType);
        };

        simulation.addUser(user);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setStartRoad(String startRoad) {
        this.startRoad = startRoad;
    }

    public void setEndRoad(String endRoad) {
        this.endRoad = endRoad;
    }
}
