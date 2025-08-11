package uni.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private  Long id;
    private String name;
    private float average;

    @ManyToMany(fetch = FetchType.EAGER, cascade ={ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> listStudent;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    @JsonManagedReference
    private List<Note> notes;

    public Course(Long id, String name, float average, ArrayList<Student> listStudent) {
        this.id = id;
        this.name = name;
        this.average = average;
        this.listStudent = listStudent;
    }

    public Course(String name, float average, ArrayList<Student> listStudent) {
        this.name = name;
        this.average = average;
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

    public List<Student> getListStudent() {
        return listStudent;
    }

    public void setListStudent(List<Student> listStudent) {
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return name;
    }
}
