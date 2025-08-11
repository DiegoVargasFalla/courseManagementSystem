package uni.controller;

import uni.models.Course;
import uni.models.Note;
import uni.models.Student;
import uni.repository.AppRepository;
import uni.view.AppUi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
        initPersistence();
        showAddCourse();
        showAddStudent();
        showStudentsTable();
        saveCourse();
        // updateItemsCourses();
        saveStudent();
        showInfoUser();
        searchStudent();
        activateEdition();
        addNote();
        addNewCourseStudent();
        changeInfoByCourse();
        saveEdition();
        deleteStudent();
    }

    public void saveStudent() {

        appUi.getButtonSaveUser().addActionListener(e -> {

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

                List<Student> studentListFromCourse = course.getListStudent();
                for (Student s : studentListFromCourse) {
                    System.err.println("-> Name student: " + s.getName());
                }
                List<Course> courseListFromStudent = new ArrayList<>();

                try {
                    student.setDni(Long.parseLong(dni));
                } catch (NumberFormatException ex) {
                    showMessage("¡¡Enter only numbers in dni!!", 2000);
                    return;
                }

                //
                if(appRepository.checkExistStudent(Long.parseLong(dni))) {

                    //set all student data
                    student.setName(name);
                    student.setLastName(lastName);
                    student.setAverage(0.0F);

                    course.getListStudent().add(student);

                    if (appRepository.updateCourse(course)) {
                        appUi.getNameCourseInput().removeAllItems();
                        appUi.getNameCourseInput().repaint();
                        appUi.getNameCourseInput().revalidate();

                        for(Course c : appRepository.getCourses()) {
                            appUi.getNameCourseInput().addItem(c);
                        }

                        appUi.getNameUserInput().setText("");
                        appUi.getLastNameUserInput().setText("");
                        appUi.getUserDniInput().setText("");

                        appUi.getNameUserInput().requestFocus();
                        showMessage("User saved", 1500);
                    } else {
                        showMessage("User not saved", 1500);
                    }
                } else {
                    showMessage("¡¡ User already exist", 2000);
                }
            }
        });
    }

    public void saveCourse() {
        appUi.getButtonSaveCourse().addActionListener(e -> {

            if (appUi.getInputNameCourse().getText().length() > 1) {
                String name = appUi.getInputNameCourse().getText();

                Course course = new Course();
                course.setName(name);
                course.setAverage(0.0F);

                if(appRepository.createCourse(course)) {
                    addItemsCourses(course);
                    appUi.getInputNameCourse().requestFocus();
                    showMessage("Course save", 2000);
                    updateIdCourse();
                } else {
                    showMessage("Course was not saved", 2000);
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

    public List<Course> updateListCourse(Course course) {

        List<Course> listCourse =  appRepository.getCourses();
        ArrayList<String> listNameCourses = new ArrayList<>();

        if (!listCourse.isEmpty()) {

            for (Course c: listCourse) {
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
        List<Course> listCourses = appRepository.getCourses();

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
                List<Course> listCourses = appRepository.getCourses();
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

                Optional<Course> courseExisting = appRepository.getCourse(selectionCourse.getId());

                if(courseExisting.isPresent()) {
                    Course courseUpdate = courseExisting.get();
                    deleteComponentsInfo();
                    addStudentsPanel(courseUpdate.getListStudent());
                }
            }
        });
    }

    public void addStudentsPanel(List<Student> studentList) {

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
                Optional<Student> studentExist = appRepository.getStudent(id);

                if(studentExist.isPresent()) {

                    Student student = studentExist.get();

                    //add widgets to panel student info
                    System.out.println(student.getId());
                    appUi.getIdStudentInput().setText(Long.toString(student.getId()));
                    appUi.getUserNameStudentInput().setText(student.getName());
                    appUi.getLastNameStudentInput().setText(student.getLastName());
                    appUi.getUserDniStudentInput().setText(Long.toString(student.getDni()));
                    appUi.getNumSemesterStudentInput().setText(Integer.toString(student.getNumSemester()));

                    appUi.getNameCourseStudentInfo().removeAllItems();
                    appUi.getNameCourseStudentInfo().revalidate();
                    appUi.getNameCourseStudentInfo().repaint();

                    appUi.getListNotesStudentInfo().removeAllItems();
                    appUi.getListNotesStudentInfo().revalidate();
                    appUi.getListNotesStudentInfo().repaint();


                    for (Course course : student.getCourseList()) {
                        appUi.getNameCourseStudentInfo().addItem(course);
                    }

                    Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();

                    if (course != null) {

                        ArrayList<Note> noteArrayList = new ArrayList<>();

                        if(!student.getNotesList().isEmpty()) {

                            appUi.getListNotesStudentInfo().removeAllItems();
                            appUi.getListNotesStudentInfo().repaint();

                            for (Note note: student.getNotesList()) {
                                if (note.getCourse().equals(course)) {
                                    noteArrayList.add(note);
                                    appUi.getListNotesStudentInfo().addItem(note.getNote());
                                }
                            }
                            appUi.getAverageStudentInfoInput().setText(student.calculateAverage(noteArrayList).toString());
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
                    Course course = (Course) appUi.getNameCourseStudentInfo().getSelectedItem();
                    Note newNote = new Note();

                    JComboBox<Float> comBoxNotes = appUi.getListNotesStudentInfo();

                    if(course != null) {
                        for (Student student: course.getListStudent()) {
                            if(student.getId().equals(Long.parseLong(appUi.getIdStudentInput().getText()))) {

                                course.getNotes().add(newNote);
                                student.getNotesList().add(newNote);
                                newNote.setCourse(course);
                                newNote.setNote(parseNote);
                                newNote.setStudent(student);

                                if(appRepository.createNote(newNote)) {
                                    comBoxNotes.addItem(parseNote);

                                    ArrayList<Note> noteList = new ArrayList<>();

                                    for (Note n: student.getNotesList()) {
                                        if(n.getCourse().getId().equals(course.getId())) {
                                            noteList.add(n);
                                        }
                                    }
                                    appUi.getAverageStudentInfoInput().setText(student.calculateAverage(noteList).toString());
                                    break;
                                } else {
                                    showMessage("Note was not saved", 2000);
                                }
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
            // ArrayList<Student> studentList = appRepository.getStudentArrayList();
            List<Course> courseList = appRepository.getCourses();
            JComboBox<Course> courseCascade = appUi.getCascadeCourseByAdd();
            courseCascade.removeAllItems();
            courseCascade.revalidate();
            courseCascade.repaint();

            Optional<Student> studentExist = appRepository.getStudent(id);

            if(studentExist.isPresent()) {
                Student student = studentExist.get();

                if(!courseList.isEmpty()) {
                    for (Course course: courseList) {
                        if(!student.getCourseList().contains(course)) {
                            courseCascade.addItem(course);
                        }
                    }
                }

                int selectedCourse = JOptionPane.showConfirmDialog(null, courseCascade, "Select course", JOptionPane.OK_CANCEL_OPTION);

                if (selectedCourse == JOptionPane.OK_OPTION) {
                    Course course = (Course) courseCascade.getSelectedItem();
                    if(course != null) {
                        course.getListStudent().add(student);
                        if(appRepository.updateCourse(course)) {
                            appUi.getNameCourseStudentInfo().addItem(course);
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
                Optional<Student> studentExist = appRepository.getStudent(id);
                if(studentExist.isPresent()) {
                    Student student = studentExist.get();

                    appUi.getAverageStudentInfoInput().setText("");
                    appUi.getListNotesStudentInfo().removeAllItems();
                    ArrayList<Note> notesList = new ArrayList<>();

                    for(Note note: student.getNotesList()) {
                        if(note.getCourse().getId().equals(course.getId())) {
                            appUi.getListNotesStudentInfo().addItem(note.getNote());
                            notesList.add(note);
                        }
                    }

                    int count = 0;
                    for(Note n: student.getNotesList()) {
                        if(n.getCourse().getId().equals(course.getId())) {
                            count++;
                        }
                    }

                    if(count > 0) {
                        System.out.println("-> Ingrwsando a ver notas curso");
                        appUi.getAverageStudentInfoInput().setText(student.calculateAverage(notesList).toString());
                    } else {
                        System.out.println("-> no hay notas");
                        appUi.getAverageStudentInfoInput().setText("0.0");
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
                            appRepository.updateCourse(course);

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
                    course.getListStudent().removeIf(s ->  s.getId().equals(id));
                    appRepository.updateCourse(course);
                    deleteComponentsInfo();
                    deletePanelsUserInfo();
                }
            }
        });
    }

    public void showCoursesSideBar(List<Course> courses) {
        for (Course course: courses) {
            appUi.getCourseComboBox().addItem(course);
        }
    }

    public void initPersistence(){
        System.out.println(" --- INIT PERSISTENCE ---");
        // appRepository.readCourseFile();
        showCoursesSideBar(appRepository.getCourses());
    }

    public boolean verificar(Scanner entrada, String mensaje) {
        return entrada.hasNextInt();
    }


}
