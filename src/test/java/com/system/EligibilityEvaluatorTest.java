package com.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EligibilityEvaluatorTest {
    private final EligibilityEvaluator evaluator = new EligibilityEvaluator();

    @Test
    public void testNormalEligibleScenario() {
        Employee emp = new Employee("E001", "Alice", 25, "IT", "Active", 3, true);
        EligibilityEvaluator.EvaluationResult res = evaluator.evaluate(emp, 2);
        assertEquals("Eligible", res.status);
        assertTrue(res.reasons.isEmpty());
    }

    @Test
    public void testAgeBoundaryScenario() {
        // Exactly 21 -> Should pass age check
        Employee emp1 = new Employee("E002", "Bob", 21, "HR", "Active", 2, true);
        assertEquals("Eligible", evaluator.evaluate(emp1, 2).status);

        // Under 21 (20) -> Should fail
        Employee emp2 = new Employee("E003", "Charlie", 20, "HR", "Active", 2, true);
        assertEquals("Not Eligible", evaluator.evaluate(emp2, 2).status);
    }

    @Test
    public void testConditionallyEligibleScenario() {
        // Fits all base rules but lacks high clearance tier
        Employee emp = new Employee("E004", "David", 30, "Finance", "Active", 1, true);
        EligibilityEvaluator.EvaluationResult res = evaluator.evaluate(emp, 3);
        assertEquals("Conditionally Eligible", res.status);
        assertEquals(1, res.reasons.size());
    }

    @Test
    public void testMultipleFailureScenario() {
        // Underage, wrong department, and invalid ID simultaneously
        Employee emp = new Employee("E005", "Eve", 19, "Marketing", "Active", 2, false);
        EligibilityEvaluator.EvaluationResult res = evaluator.evaluate(emp, 2);
        
        assertEquals("Not Eligible", res.status);
        // Asserts that all 3 independent failures are listed together without early-stopping
        assertEquals(3, res.reasons.size());
    }

    @Test
    public void testInvalidInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            evaluator.evaluate(null, 1);
        });
    }
}
