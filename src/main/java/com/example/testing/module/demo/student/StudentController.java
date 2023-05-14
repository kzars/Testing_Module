package com.example.testing.module.demo.student;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    //Get a list of all students
    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    //Save new student
    @PostMapping
    public void addStudent(@Valid @RequestBody Student student){
        studentService.addStudents(student);
    }

    //Delete student
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

}
