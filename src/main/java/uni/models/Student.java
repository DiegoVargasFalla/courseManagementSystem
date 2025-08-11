package uni.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Long dni;

    @Column(nullable = false)
    private int numSemester = 1;

    @Column(nullable = false)
    private Float average = 0.0F;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listStudent")
    private List<Course> courseList;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Note> notesList;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumSemester() {
        return numSemester;
    }

    public void setNumSemester(int numSemester) {
        this.numSemester = numSemester;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public Float calculateAverage(List<Note> notes) {
        float sum = 0;
        for (Note note : notes) {
            System.out.println(note.getNote());
            sum += note.getNote();
        }
        return sum / notes.size();
    }

//    @Override
//    public boolean equals(Object obj) {
//
//        if(this == obj) return true;
//        if(obj == null) return false;
//        if(getClass() != obj.getClass()) return false;
//
//        Student student = (Student) obj;
//
//        return Objects.equals(student.getDni(), dni);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(dni);
//    }
}
