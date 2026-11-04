package com.vignesh.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage cannot be below 0")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private Double percentage;

    public Student() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }

    @Transient
    public boolean isEligible() {
        if (percentage == null || course == null) return false;
        double required = switch (course) {
            case "BCA" -> 50;
            case "BSc" -> 60;
            case "BCom" -> 45;
            case "Engineering" -> 70;
            default -> Double.POSITIVE_INFINITY;
        };
        return percentage >= required;
    }

    @Transient
    public double getRequiredPercentage() {
        if (course == null) return 0;
        return switch (course) {
            case "BCA" -> 50;
            case "BSc" -> 60;
            case "BCom" -> 45;
            case "Engineering" -> 70;
            default -> 0;
        };
    }
}
