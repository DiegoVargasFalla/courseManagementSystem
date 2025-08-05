package uni.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uni.models.Course;
import uni.models.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AppRepository {

    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<Student> studentArrayList = new ArrayList<>();

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    public ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public void setStudentArrayList(ArrayList<Student> studentArrayList) {
        this.studentArrayList = studentArrayList;
    }

    public boolean fileChecker(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void saveCourseFile(ArrayList<Course> courseArrayList) {
        System.out.println("inside saveCourseFile");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("src/main/java/uni/assets/courses.json"), courseArrayList);
            System.out.println("saved info courses");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCourseFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file =  new File("src/main/java/uni/assets/courses.json");
            if (file.exists()) {
                ArrayList<Course> courses = mapper.readValue( file, new TypeReference<>() {});
                for (Course course : courses) {
                    System.out.println(course.getName());
                    ArrayList<Student> students = course.getListStudent();
                    studentArrayList.addAll(students);
                    setCourseArrayList(courses);
                }
            } else {
                System.out.println("file not found");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
