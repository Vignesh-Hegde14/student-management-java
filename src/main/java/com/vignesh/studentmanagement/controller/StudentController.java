package com.vignesh.studentmanagement.controller;

import com.vignesh.studentmanagement.model.Student;
import com.vignesh.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) { this.service = service; }

    @GetMapping("/")
    public String dashboard(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", service.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("totalStudents", service.count());
        model.addAttribute("eligibleStudents", service.eligibleCount());
        model.addAttribute("student", new Student());
        return "index";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add student");
        return "student-form";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.getStudent(id));
        model.addAttribute("pageTitle", "Edit student");
        return "student-form";
    }

    @PostMapping("/students/save")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult result,
                              Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", student.getId() == null ? "Add student" : "Edit student");
            return "student-form";
        }
        service.save(student);
        redirect.addFlashAttribute("successMessage", "Student saved successfully.");
        return "redirect:/";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("successMessage", "Student deleted.");
        return "redirect:/";
    }
}
