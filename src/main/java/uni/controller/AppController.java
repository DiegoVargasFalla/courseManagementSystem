package uni.controller;

import uni.models.Course;
import uni.models.Student;
import uni.repository.AppRepository;
import uni.view.AppUi;

import com.fasterxml.jackson.databind.ext.CoreXMLDeserializers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class AppController {

    AppUi appUi;
    Course course;
    AppRepository appRepository;


    public AppController(AppUi appUi, Course course, AppRepository repository) {
        this.appUi = appUi;
        this.course = course;
        this.appRepository = repository;
    }


    public void init() {
        showAddCourse();
        showAddStudent();
        showStudentsTable();
        saveCourse();
        updateItemsCourses();
        saveStudent();
        showInfoUser();
        searchStudent();
        activateEdition();
        addNote();
        addNewCourseStudent();
        changeInfoByCourse();
        saveEdition();
        deleteStudent();
        initPersistence();
    }

    public void saveStudent() {

        appUi.getButtonSaveUser().addActionListener(e -> {

            Long id = course.generateId();
            String name = appUi.getNameUserInput().getText();
            String lastName = appUi.getLastNameUserInput().getText();
            String dni = appUi.getUserDniInput().getText();
            Course course =  (Course) appUi.getNameCourseInput().getSelectedItem();


            if (name.isEmpty()) {
                showMessage("¡¡Enter your name!!", 2000);
            } else if (lastName.isEmpty()) {
                showMessage("¡¡Enter your last name!!", 2000);
            } else if (course == null || course.getId() == null) {
                showMessage("¡¡Select a course!!", 2000);
            } else if (dni.isEmpty()) {
                showMessage("¡¡Enter your dni!!", 2000);
            } else {


                //create student instance
                Student student = new Student();

                ArrayList<Student> studentListFromCourse = course.getListStudent();
                ArrayList<Long> courseListFromStudent = new ArrayList<>();
                ArrayList<Student> studentList = appRepository.getStudentArrayList();

                try {
                    student.setDni(Long.parseLong(dni));
                } catch (NumberFormatException ex) {
                    showMessage("¡¡Enter only numbers in dni!!", 2000);
                    return;
                }

                //set all data
                student.setId(id);
                student.setName(name);
                student.setLastName(lastName);
                student.setNoteList(new HashMap<>());
                student.setAverageList(new HashMap<Long, Float>());

                // add lists for each
                courseListFromStudent.add(0, course.getId());
                studentListFromCourse.add(0, student);

                //set list for each of the object
                studentList.add(0, student);
                course.setListStudent(studentListFromCourse);
                student.setCoursesList(courseListFromStudent);
                appRepository.setStudentArrayList(studentList);


                if (course.getListStudent().contains(student)) {
                    appUi.getNameUserInput().setText("");
                    appUi.getLastNameUserInput().setText("");
                    appUi.getUserDniInput().setText("");

                    appUi.getNameUserInput().requestFocus();
                    showMessage("User saved", 1000);
                    appRepository.saveCourseFile(appRepository.getCourseArrayList());
                }
            }
        });


    }

    public void saveCourse() {
        appUi.getButtonSaveCourse().addActionListener(e -> {

            if (appUi.getInputNameCourse().getText().length() > 1) {
                String id = appUi.getInputIdCourse().getText();
                String name = appUi.getInputNameCourse().getText();
                float average = 0;
                ArrayList<Student> aproverList = new ArrayList<>();
                ArrayList<Student> listStudent = new ArrayList<>();

                Course course = new Course(Long.parseLong(id), name, average, aproverList, listStudent);
                appRepository.setCourseArrayList(updateListCourse(course));

                updateIdCourse();
                if (appRepository.getCourseArrayList().contains(course)) {
                    addItemsCourses(course);
                    appUi.getInputNameCourse().requestFocus();
                    showMessage("Course save", 500);
                    appRepository.saveCourseFile(appRepository.getCourseArrayList());
                }
            }
            else {
                showMessage("Write course on the text field", 2000);
            }
        });
    }

    public void searchStudent() {
        JTable table = appUi.getTableStudents();
        TableRowSorter<DefaultTableModel> sorter = appUi.getSorter();
        table.setRowSorter(sorter);

        appUi.getButtonSearch().addActionListener(e -> {

            String id = appUi.getFieldSearch().getText().trim();

            if (id.isEmpty()) {
                sorter.setRowFilter(null);
                appUi.getButtonSearch().setText("Search");
            }
            else {
                sorter.setRowFilter(RowFilter.regexFilter("^" + id + "$", 0, 1, 2, 3, 4, 5, 6));
                appUi.getFieldSearch().setText("");
                appUi.getButtonSearch().setText("Clean Table");
            }
        });

    }

    public ArrayList<Course> updateListCourse(Course course) {

        ArrayList<Course> listCourse =  appRepository.getCourseArrayList();
        ArrayList<String> listNameCourses = new ArrayList<>();

        if (!listCourse.isEmpty()) {

            for (Course c : listCourse) {
                listNameCourses.add(c.getName().replace(" " , ""));
            }

            if (!listNameCourses.contains(course.getName().replace(" " , ""))) {
                listCourse.add(course);
            }
            else {
                showMessage("course already exist", 2000);
            }
            return listCourse;
        }
        listCourse.add(course);
        return listCourse;
    }

    public void updateIdCourse() {

        appUi.getInputIdCourse().setText(Long.toString(course.generateId()));
        appUi.getInputNameCourse().setText("");
    }

    public void updateItemsCourses() {
        ArrayList<Course> listCourses = appRepository.getCourseArrayList();

        if (!listCourses.isEmpty()) {
            appUi.getCourseComboBox().removeAllItems();
            for (Course course : listCourses) {
                appUi.getCourseComboBox().addItem(course);
            }
        }
    }

    public void addItemsCourses(Course course) {
        appUi.getCourseComboBox().addItem(course);
    }

    public void showMessage(String message, int miliseconds) {
        JDialog dialog = appUi.getMessageInfo(message).createDialog("Message");
        dialog.setModal(false);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);

        new Timer(miliseconds, e -> dialog.dispose()).start();
    }

    public void deleteComponentsInfo() {
        appUi.getShowInfoPanel().removeAll();
        appUi.getShowInfoPanel().revalidate();
        appUi.getShowInfoPanel().repaint();
    }

    public boolean verifyExistingComponent(Component component) {
        Component[] componentsList = appUi.getShowInfoPanel().getComponents();
        for (Component c : componentsList) {
            if (component.equals(c)) {
                return false;
            }
        }
        return true;
    }

    public void showAddCourse() {

        appUi.getButtonAddCourse().addActionListener(e -> {

            if (verifyExistingComponent(appUi.getPanelAddCourse())) {
                deleteComponentsInfo();

                appUi.getPanelAddCourse().removeAll();
                appUi.getPanelAddCourse().revalidate();
                appUi.getPanelAddCourse().repaint();

                updateIdCourse();

                appUi.getPanelAddCourse().add(appUi.getTitleAddCourse());

                appUi.getPanelAddCourse().add(Box.createVerticalStrut(20));

                appUi.getPanelAddCourse().add(appUi.getTextIdCourse());
                appUi.getPanelAddCourse().add(appUi.getInputIdCourse());

                appUi.getPanelAddCourse().add(Box.createVerticalStrut(15));

                appUi.getPanelAddCourse().add(appUi.getTextNameCourse());
                appUi.getPanelAddCourse().add(appUi.getInputNameCourse());

                appUi.getPanelAddCourse().add(Box.createVerticalStrut(15));

                appUi.getPanelAddCourse().add(appUi.getButtonSaveCourse());

                appUi.getShowInfoPanel().add(appUi.getPanelAddCourse(), BorderLayout.CENTER);

                appUi.getInputNameCourse().requestFocus();

                appUi.getPanelAddCourse().revalidate();
                appUi.getPanelAddCourse().repaint();

                appUi.getShowInfoPanel().revalidate();
                appUi.getShowInfoPanel().repaint();
            }
        });
    }

    public void showAddStudent() {

        appUi.getButtonAddStudent().addActionListener(e -> {

            if (verifyExistingComponent(appUi.getUserFormPanel())) {

                deleteComponentsInfo();

                JPanel form = appUi.getUserFormPanel();
                ArrayList<Course> listCourses = appRepository.getCourseArrayList();
                JComboBox<Course> courseCascade = appUi.getNameCourseInput();

                courseCascade.removeAllItems();
                courseCascade.revalidate();
                courseCascade.repaint();

                for (Course course : listCourses) {
                    courseCascade.addItem(course);
                }

                form.removeAll();

                form.revalidate();
                form.repaint();

                form.add(appUi.getFormTitleLabel());
                form.add(Box.createVerticalStrut(20));

                form.add(appUi.getNameUserLabel());
                form.add(appUi.getNameUserInput());

                form.add(Box.createVerticalStrut(15));

                form.add(appUi.getLastNameUserLabel());
                form.add(appUi.getLastNameUserInput());

                form.add(Box.createVerticalStrut(15));

                form.add(appUi.getUserDniLabel());
                form.add(appUi.getUserDniInput());

                form.add(Box.createVerticalStrut(15));

                form.add(appUi.getNameCourseLabel());
                form.add(appUi.getNameCourseInput());

                form.add(Box.createVerticalStrut(15));

                form.add(appUi.getButtonSaveUser());

                appUi.getShowInfoPanel().add(appUi.getUserFormPanel());

                appUi.getNameUserInput().requestFocus();

                form.revalidate();
                form.repaint();

                appUi.getShowInfoPanel().revalidate();
                appUi.getShowInfoPanel().repaint();

            }

        });
    }

    public void showStudentsTable() {

        appUi.getCourseComboBox().addActionListener(e -> {


            Course selectionCourse = (Course) appUi.getCourseComboBox().getSelectedItem();

            if (selectionCourse != null && selectionCourse.getId() != null) {

                ArrayList<Course> listCourses = appRepository.getCourseArrayList();

                Course actuallyCourse = new Course();

                for (Course course : listCourses) {

                    if (course.getId().equals(selectionCourse.getId())) {
                        deleteComponentsInfo();
                        actuallyCourse = course;
                    }
                }
                ArrayList<Student> listStudents = actuallyCourse.getListStudent();
                addStudentsPanel(listStudents);
            }
        });
    }


    public void addStudentsPanel(ArrayList<Student> studentList) {

        DefaultTableModel model = appUi.getTableModel();
        JTable table = appUi.getTableStudents();

        JTableHeader header =  table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(new Color(238, 30, 71));

        model.setRowCount(0);

        String[] columns = {"ID", "Name", "Last Name", "DNI", "Semester"};
        model.setColumnIdentifiers(columns);

        for (Student student : studentList) {
            model.addRow(new Object[] {
                    student.getId(),
                    student.getName(),
                    student.getLastName(),
                    student.getDni(),
                    student.getNumSemester()});
        }

        table.setModel(model);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        appUi.getPanelStudents().add(appUi.getUsersScrollPane(), BorderLayout.CENTER);
        appUi.getShowInfoPanel().add(appUi.getPanelStudents(), BorderLayout.CENTER);
    }

    public void showInfoUser() {
        JTable table = appUi.getTableStudents();

        table.getSelectionModel().addListSelectionListener(e -> {

            JPanel panel1 = appUi.getPanelInfoStudents1();
            JPanel panel2 = appUi.getPanelInfoStudents2();
            JPanel panelContentPanels = appUi.getPanelContentPanelsInfo();
            JPanel panelInfo = appUi.getUserFormStudentInfo();

            appUi.getPanelButtonsSaveAndEdit().removeAll();
            appUi.getPanelButtonsSaveAndEdit().revalidate();
            appUi.getPanelButtonsSaveAndEdit().repaint();

            deleteComponentsInfo();
            panel1.removeAll();
            panel1.revalidate();
            panel1.repaint();

            panel2.removeAll();
            panel2.revalidate();
            panel2.repaint();

            panelInfo.removeAll();
            panelInfo.revalidate();
            panelInfo.repaint();

            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {

                Long id = (Long) table.getValueAt(selectedRow, 0);
                ArrayList<Student> studentList = appRepository.getStudentArrayList();


                Student newStudent = new Student();
                System.out.println("lista: " + studentList);
                System.out.println(id);
                for (Student student : studentList) {
                    System.out.println(student.getId());
                }

                for (Student student: studentList) {

                    if (student.getId().equals(id)) {
                        newStudent.setId(student.getId());
                        newStudent.setName(student.getName());
                        newStudent.setLastName(student.getLastName());
                        newStudent.setDni(student.getDni());
                        newStudent.setAverageList(student.getAverageList());
                        newStudent.setNumSemester(student.getNumSemester());
                        newStudent.setCoursesList(student.getCoursesList());
                        newStudent.setNoteList(student.getNoteList());
                        break;
                    }
                }


                //add widgets to panel student info
                System.out.println(newStudent.getId());
                appUi.getIdStudentInput().setText(Long.toString(newStudent.getId()));
                appUi.getUserNameStudentInput().setText(newStudent.getName());
                appUi.getLastNameStudentInput().setText(newStudent.getLastName());
                appUi.getUserDniStudentInput().setText(Long.toString(newStudent.getDni()));
                appUi.getNumSemesterStudentInput().setText(Integer.toString(newStudent.getNumSemester()));

                appUi.getNameCourseStudentInfo().removeAllItems();
                appUi.getNameCourseStudentInfo().revalidate();
                appUi.getNameCourseStudentInfo().repaint();

                appUi.getListNotesStudentInfo().removeAllItems();
                appUi.getListNotesStudentInfo().revalidate();
                appUi.getListNotesStudentInfo().repaint();


                for (Long idCourse : newStudent.getCoursesList()) {
                    for (Course course: appRepository.getCourseArrayList()) {
                        if (course.getId().equals(idCourse)) {
                            appUi.getNameCourseStudentInfo().addItem(course);
                            break;
                        }
                    }
                }

                if (newStudent.getNoteList() == null) {
                    System.out.println(" ");
                } else {
                    for (Long courseKey: newStudent.getNoteList().keySet()) {
                        if (courseKey.equals(getIdNotesCourse())) {
                            for (Float note: newStudent.getNoteList().get(courseKey)) {
                                appUi.getListNotesStudentInfo().addItem(note);
                            }
                        }
                    }
                }

                Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();

                if (course != null) {
                    Long courseId = course.getId();
                    if (!newStudent.getAverageList().isEmpty()) {
                        for (Long key: newStudent.getAverageList().keySet()) {
                            if (key.equals(courseId)) {
                                appUi.getAverageStudentInfoInput().setText(newStudent.getAverageList().get(key).toString());
                            }
                        }
                    } else {
                        appUi.getAverageStudentInfoInput().setText("0.0");
                    }
                }

                panelInfo.add(appUi.getTitleStudentInfoLabel());
                panelInfo.add(Box.createVerticalStrut(5));

                panel1.add(appUi.getIdStudentInfoLabel());
                panel1.add(appUi.getIdStudentInput());

                panel1.add(Box.createVerticalStrut(15));

                panel1.add(appUi.getUserNameStudentInfoLabel());
                panel1.add(appUi.getUserNameStudentInput());

                panel1.add(Box.createVerticalStrut(15));

                panel1.add(appUi.getLastNameUserLabel());
                panel1.add(appUi.getLastNameStudentInput());

                panel1.add(Box.createVerticalStrut(15));

                panel1.add(appUi.getUserDniStudentInfoLabel());
                panel1.add(appUi.getUserDniStudentInput());

                panel1.add(Box.createVerticalStrut(27));

                panel1.add(appUi.getPanelContentButtonAddCourseStudent());
                appUi.getPanelContentButtonAddCourseStudent().add(Box.createHorizontalGlue());
                appUi.getPanelContentButtonAddCourseStudent().add(appUi.getButtonDeleteStudent());
                appUi.getPanelContentButtonAddCourseStudent().add(Box.createHorizontalStrut(10));
                appUi.getPanelContentButtonAddCourseStudent().add(appUi.getButtonAddCourseStudent());

                panelInfo.add(Box.createVerticalStrut(15));


                panel2.add(appUi.getNumSemesterStudentInfoLabel());
                panel2.add(appUi.getNumSemesterStudentInput());

                panel2.add(Box.createVerticalStrut(15));

                panel2.add(appUi.getAverageStudentInfoLabel());
                panel2.add(appUi.getAverageStudentInfoInput());

                panel2.add(Box.createVerticalStrut(15));

                panel2.add(appUi.getNameCourseLabel());
                panel2.add(appUi.getNameCourseStudentInfo());

                panel2.add(Box.createVerticalStrut(15));

                panel2.add(appUi.getListNotesStudentInfoLabel());
                panel2.add(appUi.getListNotesStudentInfo());

                panel2.add(Box.createVerticalStrut(15));

                appUi.getPanelButtonsSaveAndEdit().add(appUi.getButtonAddNote());
                appUi.getPanelButtonsSaveAndEdit().add(Box.createHorizontalStrut(10));
                appUi.getPanelButtonsSaveAndEdit().add(appUi.getButtonEditStudent());
                appUi.getPanelButtonsSaveAndEdit().add(Box.createHorizontalStrut(10));
                appUi.getPanelButtonsSaveAndEdit().add(appUi.getButtonSaveEditStudent());
                panel2.add(appUi.getPanelButtonsSaveAndEdit());

                panelContentPanels.add(panel1);
                panelContentPanels.add(panel2);

                panelInfo.add(panelContentPanels);

                appUi.getShowInfoPanel().add(panelInfo);
            }
        });
    }

    public Long getIdNotesCourse() {
        AtomicLong id = new AtomicLong();
        appUi.getNameCourseStudentInfo().addActionListener(e -> {
            Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();
            if (course != null) {
                id.set(course.getId());
            }
        });
        return id.get();
    }

    public void addNote() {
        appUi.getButtonAddNote().addActionListener(e -> {
            String note = JOptionPane.showInputDialog(null, "Enter a number", "Note", JOptionPane.PLAIN_MESSAGE);

            if (note != null && Float.parseFloat(note) <= 10 && Float.parseFloat(note) >= 0) {
                try {

                    Float parseNote = Float.parseFloat(note);
                    Long studentId = Long.parseLong(appUi.getIdStudentInput().getText());
                    Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();

                    JComboBox<Float> comBoxNotes = appUi.getListNotesStudentInfo();
                    ArrayList<Student> studentList = appRepository.getStudentArrayList();

                    if (course != null) {
                        Long courseId = course.getId();

                        for (Student student: studentList) {
                            if (student.getId().equals(studentId)) {

                                ArrayList<Float> listNotes = student.getNoteList().get(courseId);

                                if (listNotes == null) {
                                    listNotes = new ArrayList<>();
                                }

                                listNotes.add(parseNote);
                                student.getNoteList().put(courseId, listNotes);
                                System.out.println("id course" + courseId);
                                System.out.println(student.getNoteList());

                                Float sumNotes = 0.0F;
                                int counter = 0;
                                for (Float note1: listNotes) {
                                    sumNotes += note1;
                                    counter++;
                                }
                                student.getAverageList().put(courseId, (sumNotes/counter));

                                comBoxNotes.addItem(parseNote);

                                appUi.getAverageStudentInfoInput().setText(getAverageCourse(courseId, student.getAverageList()).toString());
                                appRepository.saveCourseFile(appRepository.getCourseArrayList());
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Enter decimal num", 2000);
                }
            } else {
                showMessage("Enter a note of 10 or less", 2000);
            }
        });
    }

    public Float getAverageCourse(Long id, HashMap<Long, Float> listAverages) {
        Float avergae = 0.0F;
        for (Long key: listAverages.keySet()) {
            if (key.equals(id)) {
                avergae += listAverages.get(key);
            }
        }
        return avergae;
    }

    public void activateEdition() {
        JButton editButton = appUi.getButtonEditStudent();

        editButton.addActionListener( e -> {

            if(!appUi.getUserNameStudentInput().isEditable()) {
                appUi.getUserNameStudentInput().setEditable(true);
                appUi.getLastNameStudentInput().setEditable(true);
                appUi.getNumSemesterStudentInput().setEditable(true);
                appUi.getUserDniStudentInput().setEditable(true);
            }
            else {
                appUi.getUserNameStudentInput().setEditable(false);
                appUi.getLastNameStudentInput().setEditable(false);
                appUi.getNumSemesterStudentInput().setEditable(false);
                appUi.getUserDniStudentInput().setEditable(false);
            }

        });
    }

    public void addNewCourseStudent() {

        appUi.getButtonAddCourseStudent().addActionListener(e -> {

            Long id = Long.parseLong(appUi.getIdStudentInput().getText());
            ArrayList<Student> studentList = appRepository.getStudentArrayList();
            ArrayList<Course> courseList = appRepository.getCourseArrayList();
            JComboBox<Course> courseCascade = appUi.getCascadeCourseByAdd();
            courseCascade.removeAllItems();
            courseCascade.revalidate();
            courseCascade.repaint();

            for (Student student : studentList) {
                if (student.getId().equals(id)) {
                    for (Course course: courseList) {
                        courseCascade.addItem(course);
                    }

                    int selectedCourse = JOptionPane.showConfirmDialog(null, courseCascade, "Select course", JOptionPane.OK_CANCEL_OPTION);

                    if (selectedCourse == JOptionPane.OK_OPTION) {
                        Course course = (Course) courseCascade.getSelectedItem();


                        if (course != null) {
                            ArrayList<Long> courseListNow = student.getCoursesList();
                            if (!courseListNow.contains(course.getId())) {
                                courseListNow.add(course.getId());
                                course.getListStudent().add(0, student);
                                student.getNoteList().put(course.getId(), new ArrayList<>());
                                appUi.getNameCourseStudentInfo().addItem(course);
                                appRepository.saveCourseFile(appRepository.getCourseArrayList());
                            } else {
                                showMessage("Course already exist", 2000);
                            }
                        }
                    }

                }
            }
        });
    }

    public void changeInfoByCourse() {

        appUi.getNameCourseStudentInfo().addActionListener(e -> {

            Long id = Long.parseLong(appUi.getIdStudentInput().getText());
            System.out.println("id student: " + id);
            Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();
            System.out.println("course: " + course);

            if (course != null) {
                for (Student student: course.getListStudent()) {
                    if (student.getId().equals(id)) {
                        HashMap<Long, ArrayList<Float>> notesDict = student.getNoteList();
                        System.out.println("-> diccionario: " + notesDict);
                        for (Long coursekey: notesDict.keySet()) {
                            System.out.println("key: " + coursekey);
                            if (coursekey.equals(course.getId())) {
                                System.out.println("-> ingresando al for de cada nota");
                                System.out.println("-> lista de notas: " + notesDict.get(coursekey));
                                appUi.getAverageStudentInfoInput().setText(" ");

                                appUi.getAverageStudentInfoInput().setText("");
                                appUi.getListNotesStudentInfo().removeAllItems();
                                for (Float note: notesDict.get(coursekey)) {
                                    appUi.getListNotesStudentInfo().addItem(note);
                                }

                                if (!student.getAverageList().isEmpty() && !(student.getAverageList().get(coursekey) == null) ) {
                                    appUi.getAverageStudentInfoInput().setText(student.getAverageList().get(coursekey).toString());
                                } else {
                                    appUi.getAverageStudentInfoInput().setText("0.0");
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void saveEdition() {
        appUi.getButtonSaveEditStudent().addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to save?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if(option == JOptionPane.YES_OPTION) {
                Long id = Long.parseLong(appUi.getIdStudentInput().getText());
                Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();
                String name = appUi.getUserNameStudentInput().getText();
                String lastName = appUi.getLastNameStudentInput().getText();
                Long dni = Long.parseLong(appUi.getUserDniStudentInput().getText());
                int semester = Integer.parseInt(appUi.getNumSemesterStudentInput().getText());

                if (course != null) {
                    for (Student student: course.getListStudent()) {
                        if (student.getId().equals(id)) {
                            student.setName(name);
                            student.setLastName(lastName);
                            student.setDni(dni);
                            student.setNumSemester(semester);
                            appRepository.saveCourseFile(appRepository.getCourseArrayList());

                            appUi.getUserNameStudentInput().setEditable(false);
                            appUi.getLastNameStudentInput().setEditable(false);
                            appUi.getNumSemesterStudentInput().setEditable(false);
                            appUi.getUserDniStudentInput().setEditable(false);
                        }
                    }
                }
            }
        });
    }

    public void deletePanelsUserInfo() {
        appUi.getUserFormStudentInfo().removeAll();
        appUi.getPanelContentPanelsInfo().removeAll();
        appUi.getPanelContentButtonAddCourseStudent().removeAll();
        appUi.getPanelButtonsSaveAndEdit().removeAll();
    }

    public void deleteStudent() {
        appUi.getButtonDeleteStudent().addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want delete this student?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                Long id = Long.parseLong(appUi.getIdStudentInput().getText());
                Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();

                if (course != null) {
                    course.getListStudent().forEach(student -> {
                        if (student.getId().equals(id)) {
                            student.getCoursesList().removeIf(c -> c.equals(course.getId()));
                            student.getNoteList().remove(course.getId());
                            System.out.println("List Courses: " + student.getCoursesList());
                            System.out.println("notes: " + student.getNoteList());
                        }
                    });
                    course.getListStudent().removeIf(student -> student.getId().equals(id));
                    appRepository.saveCourseFile(appRepository.getCourseArrayList());
                    deleteComponentsInfo();
                    deletePanelsUserInfo();
                }
            }
        });
    }

    public void showCoursesSideBar(ArrayList<Course> courses) {
        for (Course course: courses) {
            appUi.getCourseComboBox().addItem(course);
        }
    }

    public void initPersistence(){
        System.out.println(" --- INIT PERSISTENCE ---");
        appRepository.readCourseFile();
        showCoursesSideBar(appRepository.getCourseArrayList());
        //appRepository.readStudentsFile();
    }

    public boolean verificar(Scanner entrada, String mensaje) {
        System.out.println(mensaje);
        return entrada.hasNextInt();
    }


}
