package sis.lanes.conditions;

import org.junit.jupiter.api.Test;
import sis.lanes.Lane;
import sis.lanes.TestLane;
import sis.lanes.conditions.AndCondition;
import sis.lanes.conditions.Condition;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndConditionTest {

    @Test
    public void isFulfilledTest() {
        Lane lane = new TestLane(
                Set.of(),
                null,
                Set.of());
        Condition falseCondition = l -> false;
        Condition trueCondition1 = l -> true;
        Condition trueCondition2 = l -> true;

        Condition allTrue = new AndCondition(trueCondition1, trueCondition2);
        Condition mixed = new AndCondition(trueCondition1, falseCondition);
        Condition allFalse = new AndCondition(falseCondition);
        Condition empty = new AndCondition();


        assertTrue(allTrue.isFulfilled(lane));
        assertTrue(empty.isFulfilled(lane));

        assertFalse(allFalse.isFulfilled(lane));
        assertFalse(mixed.isFulfilled(lane));
    }
}
