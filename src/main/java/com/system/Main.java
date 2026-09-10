package com.system;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EligibilityEvaluator evaluator = new EligibilityEvaluator();

        System.out.println("=== Employee Access Eligibility System ===");
        
        while (true) {
            try {
                System.out.print("\nEnter Employee ID (or type 'exit' to quit): ");
                String id = scanner.nextLine();
                if (id.equalsIgnoreCase("exit")) break;

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Age: ");
                int age = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter Department (IT, HR, Finance, Administration, etc.): ");
                String dept = scanner.nextLine();

                System.out.print("Enter Employment Type (Active/Inactive): ");
                String type = scanner.nextLine();

                System.out.print("Enter Security Clearance Level (1-3): ");
                int clearance = Integer.parseInt(scanner.nextLine());

                System.out.print("Is ID Valid? (true/false): ");
                boolean isIdValid = Boolean.parseBoolean(scanner.nextLine());

                System.out.print("Enter Required Resource Clearance Level for matching (1-3): ");
                int resourceLevel = Integer.parseInt(scanner.nextLine());

                Employee emp = new Employee(id, name, age, dept, type, clearance, isIdValid);
                EligibilityEvaluator.EvaluationResult res = evaluator.evaluate(emp, resourceLevel);

                System.out.println("\n-----------------------------");
                System.out.println("Result Status: " + res.status);
                if (!res.reasons.isEmpty()) {
                    System.out.println("Reasons/Failures:");
                    res.reasons.forEach(reason -> System.out.println(" - " + reason));
                }
                System.out.println("-----------------------------");

            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid input type. Age and Clearance levels must be numeric.");
            } catch (IllegalArgumentException e) {
                System.out.println("[Error] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[Unexpected Error] " + e.getMessage());
            }
        }
        scanner.close();
    }
}
