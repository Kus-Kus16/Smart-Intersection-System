package sis.util.visualization;

import sis.lanes.Direction;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.users.RoadUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleVisualizer implements Visualizer {
    private final boolean colorText;

    public ConsoleVisualizer(boolean colorText) {
        this.colorText = colorText;
    }

    @Override
    public void beforeStep(Intersection intersection) {
        System.out.println("Before step:\n");
        visualize(intersection);
    }

    @Override
    public void afterStep(Intersection intersection) {
        System.out.println("After step:\n");
        visualize(intersection);
    }

    private void visualize(Intersection intersection) {
        System.out.println(getIntersectionString(intersection));
        System.out.println("\n");
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
                       formatText(lane.toString(), lane.getColor()))
                .collect(java.util.stream.Collectors.joining(" | "));
    }

    private String formatText(String text, Color color) {
        if (!colorText) {
            return text;
        }

        return getStringColor(color) + text + "\u001B[0m";
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
