package com.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EligibilityEvaluator {

    // Defined in uppercase to avoid compilation symbol issues
    private static final List<String> AUTHORIZED_DEPTS = Arrays.asList(
        "IT", "HR", "FINANCE", "ADMINISTRATION"
    );

    public static class EvaluationResult {
        public final String status;
        public final List<String> reasons;

        public EvaluationResult(String status, List<String> reasons) {
            this.status = status;
            this.reasons = reasons;
        }
    }

    public EvaluationResult evaluate(Employee employee, int requiredClearance) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee record cannot be null.");
        }

        List<String> reasons = new ArrayList<>();

        // 1. Age condition (at least 21)
        if (employee.getAge() < 21) {
            reasons.add("Employee must be at least 21 years old (Current: " + employee.getAge() + ").");
        }

        // 2. Department condition (must belong to authorized list)
        if (employee.getDepartment() == null || !AUTHORIZED_DEPTS.contains(employee.getDepartment().trim().toUpperCase())) {
            reasons.add("Department '" + employee.getDepartment() + "' is not authorized.");
        }

        // 3. Employment status condition (must be active)
        if (employee.getEmploymentStatus() == null || !"ACTIVE".equalsIgnoreCase(employee.getEmploymentStatus().trim())) {
            reasons.add("Employment status is not active.");
        }

        // 4. ID validity condition
        if (!employee.isIdValid()) {
            reasons.add("Employee ID is invalid or expired.");
        }

        // 5. Security clearance condition
        boolean clearanceSufficient = employee.getSecurityClearance() >= requiredClearance;
        if (!clearanceSufficient) {
            reasons.add("Insufficient security clearance (Required: " + requiredClearance 
                        + ", Current: " + employee.getSecurityClearance() + ").");
        }

        // If no rules failed
        if (reasons.isEmpty()) {
            return new EvaluationResult("Eligible", reasons);
        }

        // Check if only the security clearance failed while all basic conditions passed
        boolean passedBaseRules = (employee.getAge() >= 21)
                && (employee.getDepartment() != null && AUTHORIZED_DEPTS.contains(employee.getDepartment().trim().toUpperCase()))
                && ("ACTIVE".equalsIgnoreCase(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus().trim() : ""))
                && employee.isIdValid();

        if (passedBaseRules && !clearanceSufficient) {
            return new EvaluationResult("Conditionally Eligible", reasons);
        }

        return new EvaluationResult("Not Eligible", reasons);
    }
}
