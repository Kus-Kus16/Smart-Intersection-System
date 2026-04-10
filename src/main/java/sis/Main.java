package sis;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import sis.conditions.Condition;
import sis.intersection.Intersection;
import sis.lanes.Lane;
import sis.lanes.LeftLane;
import sis.lanes.PedestrianLane;
import sis.lanes.StraightRightLane;

import java.util.*;

public class Main {
    static void main() {
        Intersection intersection = new Intersection();

        for (Direction direction : Direction.values()) {
            Lane leftLane = new LeftLane(direction, intersection);
            intersection.addLane(leftLane);
            Lane straightRightLane = new StraightRightLane(direction, intersection);
            intersection.addLane(straightRightLane);
            Lane pedestrianLane = new PedestrianLane(direction, intersection);
            intersection.addLane(pedestrianLane);
        }

        for (int i = 0; i < 5; i++) {


            System.out.println(greenLanes);
            System.out.println("\n");
        }
    }
}
