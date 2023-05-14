package com.example.testing.module.demo.student;

import com.example.testing.module.demo.student.exception.BadRequestException;
import com.example.testing.module.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();

        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {

        //given
        Student student = new Student("Gary","gary@gmail.com",Gender.OTHER);

        //when
        underTest.addStudents(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);

    }

    @Test
    void willThrowExceptionWhenEmailIsTaken(){

        //given
        Student student = new Student("Gary","gary@gmail.com",Gender.OTHER);

        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> underTest.addStudents(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " is already taken");

        verify(studentRepository, never()).save(any());

    }

    @Test
    void canDeleteStudent() {
        //given
        long id = 10;
        given(studentRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteStudent(id);

        //then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionWhenDeleteStudentNotFound(){
        //given
        long id = 10;
        given(studentRepository.existsById(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exist");

        verify(studentRepository, never()).deleteById(any());

    }


}