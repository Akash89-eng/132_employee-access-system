package com.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EligibilityEvaluator {
    private static final List<String> AUTHORIZED_DEPTS = Arrays.asList("IT", "HR", "FINANCE", "ADMINISTRATION");

    public static class EvaluationResult {
        public String status;
        public List<String> reasons = new ArrayList<>();
    }

    public EvaluationResult evaluate(Employee emp, int requiredResourceLevel) {
        EvaluationResult result = new EvaluationResult();
        
        // Input Validation Rule
        if (emp == null || emp.getId() == null || emp.getName() == null) {
            throw new IllegalArgumentException("Employee data cannot be null or incomplete.");
        }

        // Rule 1: Age check
        if (emp.getAge() < 21) {
            result.reasons.add("Employee must be at least 21 years old (Current: " + emp.getAge() + ").");
        }

        // Rule 2: Department check
        if (emp.getDepartment() == null || !AUTHORIZED_DEPTs.contains(emp.getDepartment().toUpperCase())) {
            result.reasons.add("Department '" + emp.getDepartment() + "' is not authorized.");
        }

        // Rule 3: Active employment status check
        if (!"ACTIVE".equalsIgnoreCase(emp.getEmploymentType())) {
            result.reasons.add("Employment status must be Active (Current: " + emp.getEmploymentType() + ").");
        }

        // Rule 4: ID validity status check
        if (!emp.isIdValid()) {
            result.reasons.add("Employee ID status is invalid.");
        }

        // Rule 5: Security clearance tier classification
        boolean clearanceFailed = emp.getSecurityClearanceLevel() < requiredResourceLevel;

        // Final Classification Mapping
        if (!result.reasons.isEmpty()) {
            result.status = "Not Eligible";
        } else if (clearanceFailed) {
            result.status = "Conditionally Eligible";
            result.reasons.add("Insufficient security clearance (Required: " + requiredResourceLevel + ", Has: " + emp.getSecurityClearanceLevel() + ").");
        } else {
            result.status = "Eligible";
        }

        return result;
    }
}
