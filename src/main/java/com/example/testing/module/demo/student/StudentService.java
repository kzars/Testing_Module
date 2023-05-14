package com.example.testing.module.demo.student;

import com.example.testing.module.demo.student.exception.BadRequestException;
import com.example.testing.module.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    //Get all students in list
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    //Add new student and validate if email is not taken
    public void addStudents(Student student){
        Boolean existsEmail = studentRepository.selectExistsEmail(student.getEmail());
        if(existsEmail){
            throw new BadRequestException("Email " + student.getEmail() + " is already taken");
        }

        studentRepository.save(student);
    }

    //Delete student by id and check if there is such student to delete
    public void deleteStudent(Long studentId){
        Boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists){
            throw new StudentNotFoundException("Student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }



}
