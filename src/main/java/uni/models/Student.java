package uni.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {

    private Long id;
    private String name;
    private String lastName;
    private Long dni;
    private int numSemester = 1;
    private HashMap<Long, Float> averageList;
    private ArrayList<Long> coursesList;
    private HashMap<Long, ArrayList<Float>> dictNotes;


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

    public ArrayList<Long> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(ArrayList<Long> coursesList) {
        this.coursesList = coursesList;
    }

    public HashMap<Long, ArrayList<Float>> getNoteList() {
        return dictNotes;
    }

    public void setNoteList(HashMap<Long, ArrayList<Float>> dictNotes) {
        this.dictNotes = dictNotes;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public HashMap<Long, Float> getAverageList() {
        return averageList;
    }

    public void setAverageList(HashMap<Long, Float> averageList) {
        this.averageList = averageList;
    }

    public HashMap<Long, ArrayList<Float>> getDictNotes() {
        return dictNotes;
    }

    public void setDictNotes(HashMap<Long, ArrayList<Float>> dictNotes) {
        this.dictNotes = dictNotes;
    }

    public Float courseAverage(Long courseId, HashMap<Long, ArrayList<Float>> dictNotes) {
        Float average = 0.0F;
        for (Long key: dictNotes.keySet()) {

            if(key.equals(courseId)) {
                ArrayList<Float> values = dictNotes.get(key);
                for (Float note: values) {
                    average += note;
                }
                average = average/values.size();
            }
        }
        System.out.println("Average: " + average);
        return average;
    }
}
