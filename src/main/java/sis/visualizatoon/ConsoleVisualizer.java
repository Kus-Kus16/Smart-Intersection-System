package sis.visualizatoon;

import sis.lanes.Direction;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.users.RoadUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleVisualizer implements Visualizer {
    @Override
    public void visualize(Intersection intersection) {
        System.out.println(getIntersectionString(intersection));
        System.out.println("\n\n");
        System.out.println(getExitedString(intersection));
    }

    public String getIntersectionString(Intersection intersection) {
        StringBuilder sb = new StringBuilder();

        Map<Direction, List<Lane>> lanesMap = new HashMap<>();

        for (Direction direction : Direction.values()) {
            lanesMap.put(direction, intersection.getIntersectionSide(direction).getEntryLanes());
        }

        String north = getLanesString(lanesMap.get(Direction.NORTH));
        String east = getLanesString(lanesMap.get(Direction.EAST));
        String south = getLanesString(lanesMap.get(Direction.SOUTH));
        String west = getLanesString(lanesMap.get(Direction.WEST));

        int nsSpace = Math.max(getVisibleLength(north), getVisibleLength(south));

        sb.append(" ".repeat(getVisibleLength(west) + 1)).append(north).append("\n\n");
        sb.append(west).append(" ".repeat(nsSpace + 2)).append(east).append("\n\n");
        sb.append(" ".repeat(getVisibleLength(west) + 1)).append(south);

        return sb.toString();
    }

    private String getLanesString(List<Lane> lanes) {
        return lanes.stream()
                .map((lane) ->
                        getStringColor(lane.getColor())
                        + lane
                        + "\u001B[0m")
                .collect(java.util.stream.Collectors.joining(" "));
    }

    public String getExitedString(Intersection intersection) {
        StringBuilder sb = new StringBuilder();

        for (RoadUser user : intersection.getExitedUsers()) {
            sb.append(user.toString()).append("\n");
        }

        return sb.toString();
    }

    private int getVisibleLength(String s) {
        return s
                .replaceAll("\u001B\\[[;\\d]*m", "")
                .length();
    }

    private String getStringColor(Color color) {
        return switch (color) {
            case RED -> "\u001B[31m";
            case YELLOW -> "\u001B[93m";
            case ORANGE -> "\u001B[33m";
            case GREEN -> "\u001B[32m";
            case BLUE -> "\u001B[34m";
            default -> "\u001B[0m";
        };
    }
}
