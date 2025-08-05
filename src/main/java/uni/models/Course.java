package uni.models;

import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Course {

    private  Long id;
    private String name;
    private float average;
    private ArrayList<Student> approvedStudents;
    private ArrayList<Student> listStudent;

    public Course(Long id, String name, float average, ArrayList<Student> approvedStudents, ArrayList<Student> listStudent) {
        this.id = id;
        this.name = name;
        this.average = average;
        this.approvedStudents = approvedStudents;
        this.listStudent = listStudent;
    }

    public Course() {}

    //ArrayList<Student> listStudent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public ArrayList<Student> getApprovedStudents() {
        return approvedStudents;
    }

    public void setApprovedStudents(ArrayList<Student> approvedStudents) {
        this.approvedStudents = approvedStudents;
    }

    public ArrayList<Student> getListStudent() {
        return listStudent;
    }

    public void setListStudent(ArrayList<Student> listStudent) {
        this.listStudent = listStudent;
    }

    public void addStudent(Student student) {
        listStudent.add(student);
    }

    public void deleteStudent(Long id) {
        listStudent.removeIf(student -> student.getId().equals(id));
    }

    public Long generateId() {
        RandomGenerator  rgn = RandomGenerator.getDefault();
        return rgn.nextLong(1000000, 10000000);
    }



    @Override
    public String toString() {
        return name;
    }
}
