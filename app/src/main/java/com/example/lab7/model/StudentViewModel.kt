package com.example.lab7.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class StudentViewModel(initialStudents: List<Student> = emptyList()) : ViewModel() {
    private val _students = initialStudents.toMutableStateList()
    val students: List<Student>
        get() = _students

    fun addStudent(newStudent: Student) {
        for (i in 0..<_students.size) {
            if (_students[i].studentID == newStudent.studentID) {
                _students[i] = newStudent
                return
            }
        }
        _students.add(newStudent)
    }

    fun removeStudent(studentID: String) {
        _students.removeAll {it.studentID == studentID}
    }
}