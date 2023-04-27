package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class StudentRepository {
    HashMap<String, Student> studentHashMap = new HashMap<>();
    HashMap<String, Teacher> teacherHashMap = new HashMap<>();
    HashMap<String, String> studentTeacherHashMap = new HashMap<>();
    HashMap<String, List<String>> teacherStudentsHashMap = new HashMap<>();

    public void addStudent(Student student){
        String studentName = student.getName();
        studentHashMap.put(studentName, student);
    }
    public void  addTeacher(Teacher teacher){
        String teacherName = teacher.getName();
        teacherHashMap.put(teacherName, teacher);
    }
    public void addStudentTeacherPair(String studentName, String teacherName){
        if(studentHashMap.containsKey(studentName) && teacherHashMap.containsKey(teacherName)){
            studentTeacherHashMap.put(studentName, teacherName);
            List<String> currentStudents = new ArrayList<>();
            if(teacherStudentsHashMap.containsKey(teacherName)){
                currentStudents = teacherStudentsHashMap.get(teacherName);
            }
            currentStudents.add(studentName);
            teacherStudentsHashMap.put(teacherName, currentStudents);
            Teacher teacher = teacherHashMap.get(teacherName);
            teacher.setNumberOfStudents(currentStudents.size());
        }
    }
    public  Student getStudentByName(String studentName){
        for(String sName : studentHashMap.keySet()){
            if(sName.equals(studentName)){
                return  studentHashMap.get(sName);
            }
        }
        return null;
    }
    public Teacher getTeacherByName(String teacherName){
        for(String tName : teacherHashMap.keySet()){
            if(tName.equals(teacherName)){
                return teacherHashMap.get(tName);
            }
        }
        return null;
    }
    public List<String> getStudentsByTeacherName(String teacherName){
        List<String> studentsList = new ArrayList<>();
        if(teacherStudentsHashMap.containsKey(teacherName)){
            return teacherStudentsHashMap.get(teacherName);
        }
        return studentsList;
    }
    public List<String> getAllStudents(){
        List<String> studentsList = new ArrayList<>();
         for(String students : studentHashMap.keySet()){
             studentsList.add(students);
         }
         return studentsList;
    }
    public void deleteTeacherByName(String teacherName){
        List<String> studentsList = new ArrayList<>();
        if(teacherHashMap.containsKey(teacherName)){
            teacherHashMap.remove(teacherName);
        }
        if(teacherStudentsHashMap.containsKey(teacherName)){
            studentsList = teacherStudentsHashMap.get(teacherName);
            for(String student : studentsList){
                if(studentHashMap.containsKey(student)){
                    studentHashMap.remove(student);
                }
            }
        }
        teacherStudentsHashMap.remove(teacherName);
    }
    public void deleteAllTeachers(){
        teacherHashMap = new HashMap<>();
        HashSet<String> studentSet = new HashSet<>();
        for(String tName : teacherStudentsHashMap.keySet()){
            for(String sName : teacherStudentsHashMap.get(tName)){
                studentSet.add(sName);
            }
        }
        for(String sName : studentSet){
            if(studentHashMap.containsKey(sName)){
                studentHashMap.remove(sName);
            }
        }
        teacherStudentsHashMap = new HashMap<>();
    }
}
