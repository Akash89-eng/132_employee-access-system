package com.system;

public class Employee {
    private String id;
    private String name;
    private int age;
    private String department;
    private String employmentStatus; // e.g., "Active"
    private int securityClearance;
    private boolean idValid;

    public Employee(String id, String name, int age, String department, 
                    String employmentStatus, int securityClearance, boolean idValid) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.employmentStatus = employmentStatus;
        this.securityClearance = securityClearance;
        this.idValid = idValid;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public String getEmploymentStatus() { return employmentStatus; }
    public int getSecurityClearance() { return securityClearance; }
    public boolean isIdValid() { return idValid; }
}
