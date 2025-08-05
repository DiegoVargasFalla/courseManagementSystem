package uni.view;

import uni.models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class AppUi {

    JFrame frame;
    JPanel sideBar;
    JPanel logoPanel;
    JLabel logoLabel;
    JLabel imgLabel;
    ImageIcon imgUbOriginal;
    JTextField fieldSearch;
    JLabel labelSearch;
    JPanel panelWidgetsSearch;
    JButton buttonSearch;
    JPanel panelInfo;
    JPanel showInfoPanel;
    JPanel containerInfo;
    JPanel optionsSideBarPanel;
    JButton buttonAddCourse;
    JButton buttonStatistics;
    JComboBox<Course> courseComboBox;
    JPanel majorPanel;
    JLabel textCoursesLabel;
    JPanel panelAddCourse;
    JLabel titleAddCourse;
    JLabel textIdCourse;
    JTextField inputIdCourse;
    JTextField inputNameCourse;
    JLabel textNameCourse;
    JButton buttonSaveCourse;
    JOptionPane messageInfo;
    JButton buttonAddStudent;
    JPanel panelStudents;
    JPanel containerInfoStudents;
    JTable usersTable;
    DefaultTableModel tableModel;
    JScrollPane usersScrollPane;
    TableRowSorter<DefaultTableModel> sorter;


    // widgets user form
    JPanel userFormPanel;
    JLabel formTitleLabel;
    JLabel nameUserLabel;
    JLabel lastNameUserLabel;
    JLabel nameCourseLabel;
    JLabel userDniLabel;
    JTextField nameUserInput;
    JTextField lastNameUserInput;
    JTextField userDniInput;
    JComboBox<Course> nameCourseInput;
    JButton buttonSaveUser;


    //show form student info
    JPanel userFormStudentInfo;
    JLabel TitleStudentInfoLabel;
    JLabel idStudentInfoLabel;
    JLabel userNameStudentInfoLabel;
    JLabel lastNameUserStudentInfoLabel;
    JLabel userDniStudentInfoLabel;
    JLabel numSemesterStudentInfoLabel;
    JLabel averageStudentInfoLabel;
    JLabel nameCourseStudentInfoLabel;
    JLabel listNotesStudentInfoLabel;

    JTextField idStudentInput;
    JTextField userNameStudentInput;
    JTextField lastNameStudentInput;
    JTextField userDniStudentInput;
    JTextField numSemesterStudentInput;
    JTextField averageStudentInfoInput;
    JComboBox<Course> nameCourseStudentInfo;
    JComboBox<Float> listNotesStudentInfo;

    //panels inputs show info Student
    JPanel panelContentPanelsInfo;
    JPanel panelInfoStudents1;
    JPanel panelInfoStudents2;

    //buttons add note, edit and save
    JPanel panelContentButtonAddCourseStudent;
    JPanel panelButtonsSaveAndEdit;
    JButton buttonEditStudent;
    JButton buttonSaveEditStudent;
    JButton buttonAddNote;
    JButton buttonAddCourseStudent;
    JButton buttonDeleteStudent;
    JComboBox<Course> cascadeCourseByAdd;


    public AppUi() {
        frame = new JFrame("UB University");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        majorPanel = new JPanel(new GridBagLayout());
        majorPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();

        //container info students
        containerInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //user table model
        tableModel = new DefaultTableModel();

        //sorter filter
        sorter = new TableRowSorter<>(tableModel);

        //buttons and panels edit and save
        panelButtonsSaveAndEdit = new JPanel();
        panelButtonsSaveAndEdit.setLayout(new BoxLayout(panelButtonsSaveAndEdit, BoxLayout.X_AXIS));
        //panelButtonsSaveAndEdit.setBackground(Color.RED);
        panelButtonsSaveAndEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelButtonsSaveAndEdit.setMaximumSize(new Dimension(250, 30));

        panelContentButtonAddCourseStudent = new JPanel();
        panelContentButtonAddCourseStudent.setLayout(new BoxLayout(panelContentButtonAddCourseStudent, BoxLayout.X_AXIS));
        //panelContentButtonAddCourseStudent.setBackground(Color.ORANGE);
        panelContentButtonAddCourseStudent.setMaximumSize(new Dimension(250, 30));

        //button by add new course a student
        buttonAddCourseStudent = new JButton("Add Course");
        buttonAddCourseStudent.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonAddCourseStudent.setMaximumSize(new Dimension(100, 30));
        //buttonAddCourseStudent.setBackground(new Color(130, 238, 56));

        buttonDeleteStudent = new JButton("Delete");
        buttonDeleteStudent.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonDeleteStudent.setMaximumSize(new Dimension(100, 30));

        //cascade by select new course
        cascadeCourseByAdd = new JComboBox<>();
        cascadeCourseByAdd.setMaximumSize(new Dimension(250, 25));

        buttonEditStudent = new JButton("Edit");
        //buttonAddCourseStudent.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonEditStudent.setMaximumSize(new Dimension(70, 30));
        buttonEditStudent.setBackground(new Color(46, 164, 209));

        buttonSaveEditStudent = new JButton("Save");
        //buttonSaveEditStudent.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonSaveEditStudent.setMaximumSize(new Dimension(70, 30));
        buttonSaveEditStudent.setBackground(new Color(130, 238, 56));

        buttonAddNote = new JButton("Add Note");
        buttonAddNote.setMaximumSize(new Dimension(88, 30));

        //panels inputs show info Student
        panelInfoStudents1 = new JPanel();
        panelInfoStudents1.setLayout(new BoxLayout(panelInfoStudents1, BoxLayout.Y_AXIS));
        panelInfoStudents1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        panelInfoStudents2 = new JPanel();
        panelInfoStudents2.setLayout(new BoxLayout(panelInfoStudents2, BoxLayout.Y_AXIS));
        panelInfoStudents2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        panelContentPanelsInfo = new JPanel();
        panelContentPanelsInfo.setLayout(new BoxLayout(panelContentPanelsInfo, BoxLayout.X_AXIS));
        panelContentPanelsInfo.setBackground(Color.WHITE);
        panelContentPanelsInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContentPanelsInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));


        //displaying the list of student of a specific course
        usersTable = new JTable();

        //scroll users list
        usersScrollPane = new JScrollPane(usersTable);

        // button add student
        buttonAddStudent = new JButton("Add Student");
        buttonAddStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonAddStudent.setMaximumSize(new Dimension(100, 30));

        // panel where show the student list
        panelStudents = new JPanel(new BorderLayout());

        //widgets where show all info of students and edit info
        userFormStudentInfo = new JPanel();
        userFormStudentInfo.setLayout(new BoxLayout(userFormStudentInfo, BoxLayout.Y_AXIS));
        userFormStudentInfo.setBackground(Color.WHITE);
        userFormStudentInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        userFormStudentInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        TitleStudentInfoLabel = new JLabel("Information Student");
        TitleStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        TitleStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idStudentInfoLabel = new JLabel("ID");
        idStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        idStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userNameStudentInfoLabel = new JLabel("Name Student");
        userNameStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        userNameStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lastNameUserStudentInfoLabel = new JLabel("Last Name Student");
        lastNameUserStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        lastNameUserStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userDniStudentInfoLabel = new JLabel("Dni");
        userDniStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        userDniStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        numSemesterStudentInfoLabel = new JLabel("Num Semester");
        numSemesterStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        numSemesterStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        averageStudentInfoLabel = new JLabel("Average");
        averageStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        averageStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameCourseStudentInfoLabel = new JLabel("Courses");
        nameCourseStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameCourseStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        listNotesStudentInfoLabel = new JLabel("Notes");
        listNotesStudentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        listNotesStudentInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idStudentInput = new JTextField();
        idStudentInput.setMaximumSize(new Dimension(250, 20));
        idStudentInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        idStudentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        idStudentInput.setEditable(false);

        userNameStudentInput = new JTextField();
        userNameStudentInput.setMaximumSize(new Dimension(250, 20));
        userNameStudentInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        userNameStudentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameStudentInput.setEditable(false);

        lastNameStudentInput = new JTextField();
        lastNameStudentInput.setMaximumSize(new Dimension(250, 20));
        lastNameStudentInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lastNameStudentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        lastNameStudentInput.setEditable(false);

        userDniStudentInput = new JTextField();
        userDniStudentInput.setMaximumSize(new Dimension(250, 20));
        userDniStudentInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        userDniStudentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        userDniStudentInput.setEditable(false);

        numSemesterStudentInput = new JTextField();
        numSemesterStudentInput.setMaximumSize(new Dimension(250, 20));
        numSemesterStudentInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        numSemesterStudentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        numSemesterStudentInput.setEditable(false);

        averageStudentInfoInput = new JTextField();
        averageStudentInfoInput.setMaximumSize(new Dimension(250, 20));
        averageStudentInfoInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        averageStudentInfoInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageStudentInfoInput.setEditable(false);

        nameCourseStudentInfo = new JComboBox<>();
        nameCourseStudentInfo.setMaximumSize(new Dimension(250, 25));

        listNotesStudentInfo = new JComboBox<>();
        listNotesStudentInfo.setMaximumSize(new Dimension(250, 25));

        //message info
        messageInfo = new JOptionPane();

        //Button save course
        buttonSaveCourse = new JButton("Save course");
        buttonSaveCourse.setAlignmentX(Component.CENTER_ALIGNMENT);

        //panel courses
        panelAddCourse = new JPanel();
        panelAddCourse.setLayout(new BoxLayout(panelAddCourse, BoxLayout.Y_AXIS));
        panelAddCourse.setBackground(Color.WHITE);

        //widgets user form

        //title user form
        formTitleLabel = new JLabel("Create new Student");
        formTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        formTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameUserInput = new JTextField();
        lastNameUserInput = new JTextField();
        nameCourseInput = new JComboBox<>();
        userDniInput = new JTextField();

        nameUserLabel = new JLabel("Name: ");
        nameUserLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameUserInput.setMaximumSize(new Dimension(250, 20));
        nameUserInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        nameUserInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        lastNameUserLabel = new JLabel("Last Name: ");
        lastNameUserLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        lastNameUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lastNameUserInput.setMaximumSize(new Dimension(250, 20));
        lastNameUserInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lastNameUserInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameCourseLabel = new JLabel("Course: ");
        nameCourseLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameCourseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Course course = new Course();
        course.setName("Select Course");
        nameCourseInput.addItem(course);
        nameCourseInput.setMaximumSize(new Dimension(250, 20));
        nameCourseInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        nameCourseInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        // button add user
        buttonSaveUser = new JButton("Save user");
        buttonSaveUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSaveUser.setMaximumSize(new Dimension(100, 30));

        //widget add student
        //panel form user
        userFormPanel = new JPanel();
        userFormPanel.setLayout(new BoxLayout(userFormPanel, BoxLayout.Y_AXIS));
        userFormPanel.setBackground(Color.WHITE);

        // widgets create course
        titleAddCourse = new JLabel("Create New Course");
        titleAddCourse.setFont(new Font("SansSerif", Font.BOLD, 25));
        titleAddCourse.setAlignmentX(Component.CENTER_ALIGNMENT);

        //label text courseId
        textIdCourse = new JLabel("Course ID: ");
        textIdCourse.setFont(new Font("SansSerif", Font.BOLD, 15));
        textIdCourse.setAlignmentX(Component.CENTER_ALIGNMENT);

        //input id course
        inputIdCourse = new JTextField();
        inputIdCourse.setMaximumSize(new Dimension(250, 20));
        inputIdCourse.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputIdCourse.setEditable(false);
        inputIdCourse.setAlignmentX(Component.CENTER_ALIGNMENT);

        userDniLabel = new JLabel("Dni: ");
        userDniLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        userDniLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userDniInput = new JTextField();
        userDniInput.setMaximumSize(new Dimension(250, 20));
        userDniInput.setFont(new Font("SansSerif", Font.PLAIN, 15));
        userDniInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        //label text name course
        textNameCourse = new JLabel("Course Name: ");
        textNameCourse.setFont(new Font("SansSerif", Font.BOLD, 15));
        textNameCourse.setAlignmentX(Component.CENTER_ALIGNMENT);

        //input name course
        inputNameCourse = new JTextField();
        inputNameCourse.setMaximumSize(new Dimension(250, 20));
        inputNameCourse.setFont(new Font("SansSerif", Font.PLAIN, 15));
        inputNameCourse.setAlignmentX(Component.CENTER_ALIGNMENT);


        //img panel
        imgUbOriginal = new ImageIcon("src/main/java/uni/assets/imgUb.jpg");
        Image image = imgUbOriginal.getImage().getScaledInstance(100, 110, Image.SCALE_SMOOTH);
        ImageIcon imgUb = new ImageIcon(image);
        imgLabel = new JLabel(imgUb);

        // Sidebar (left)
        logoLabel = new JLabel("UB University", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(220, 53, 69)); // rojo suave
        logoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0,0, 0, 0),
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY)
        ));
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        //logoPanel.add(logoLabel, BorderLayout.NORTH);
        logoPanel.add(imgLabel, BorderLayout.SOUTH);

        // text top sidebar
        textCoursesLabel = new JLabel("Courses");
        textCoursesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textCoursesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        // cascading list of courses
        courseComboBox = new JComboBox<>();
        courseComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        Course voidCourse = new Course();
        voidCourse.setName("Select Course");
        courseComboBox.addItem(voidCourse);

        // button add course
        buttonAddCourse = new JButton("Add Course");
        buttonAddCourse.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonAddCourse.setMaximumSize(new Dimension(100, 30));

        buttonStatistics = new JButton("Statistics");
        buttonStatistics.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonStatistics.setMaximumSize(new Dimension(100, 30));

        // container panel sidebar
        optionsSideBarPanel = new JPanel();
        optionsSideBarPanel.setLayout(new BoxLayout(optionsSideBarPanel, BoxLayout.Y_AXIS));
        optionsSideBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        optionsSideBarPanel.setBackground(Color.WHITE);
        optionsSideBarPanel.add(textCoursesLabel);
        optionsSideBarPanel.add(Box.createVerticalStrut(5));
        optionsSideBarPanel.add(courseComboBox);
        optionsSideBarPanel.add(Box.createVerticalStrut(20));
        optionsSideBarPanel.add(buttonAddCourse);
        optionsSideBarPanel.add(Box.createVerticalStrut(10));
        optionsSideBarPanel.add(buttonAddStudent);
        optionsSideBarPanel.add(Box.createVerticalStrut(10));
//        optionsSideBarPanel.add(buttonStatistics);

        // sidebar panel
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(Color.WHITE);
        sideBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
        sideBar.add(logoPanel);
        sideBar.add(optionsSideBarPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.10;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        majorPanel.add(sideBar, gbc);

        // text of search
        labelSearch = new JLabel("Search");
        labelSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // input for search students
        fieldSearch = new JTextField();
        fieldSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fieldSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        // button to perform the search
        buttonSearch = new JButton("Search");
        buttonSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // panel widgets search
        panelWidgetsSearch = new JPanel();
        panelWidgetsSearch.setLayout(new BoxLayout(panelWidgetsSearch, BoxLayout.Y_AXIS));
        panelWidgetsSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelWidgetsSearch.setBackground(Color.WHITE);
        panelWidgetsSearch.add(labelSearch);
        panelWidgetsSearch.add(Box.createVerticalStrut(5));
        panelWidgetsSearch.add(fieldSearch);
        panelWidgetsSearch.add(Box.createVerticalStrut(10));
        panelWidgetsSearch.add(buttonSearch);

        // panel for show the info course
        showInfoPanel = new JPanel(new BorderLayout());
        showInfoPanel.setBackground(Color.WHITE);
        showInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.add(panelWidgetsSearch);
        panelInfo.add(showInfoPanel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.90;
        gbc.fill = GridBagConstraints.BOTH;
        majorPanel.add(panelInfo, gbc);

        frame.add(majorPanel);
    }

    public void showApp() {
        frame.setVisible(true);
    }

    public JComboBox<Course> getCourseComboBox() {
        return courseComboBox;
    }

    public JButton getButtonAddCourse() {
        return buttonAddCourse;
    }

    public  JLabel getLabelCourse() {
        return textCoursesLabel;
    }

    public JTextField getInputIdCourse() {
        return inputIdCourse;
    }

    public JTextField getInputNameCourse() {
        return inputNameCourse;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanelAddCourse() {
        return panelAddCourse;
    }

    public JPanel getShowInfoPanel() {
        return showInfoPanel;
    }

    public JLabel getTextIdCourse() {
        return textIdCourse;
    }

    public JLabel getTextNameCourse() {
        return textNameCourse;
    }

    public JButton getButtonSaveCourse() {
        return buttonSaveCourse;
    }

    public JOptionPane getMessageInfo(String message) {
        return new JOptionPane(
                message,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null
        );
    }

    public JPanel getPanelStudents() {
        return panelStudents;
    }

    public JPanel getContainerInfoStudents() {
        return containerInfoStudents;
    }

    public JTable getTableStudents() {
        return usersTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JPanel getSideBar() {
        return sideBar;
    }

    public JTextField getNameUserInput() {
        return nameUserInput;
    }

    public JTextField getLastNameUserInput() {
        return lastNameUserInput;
    }

    public JLabel getTitleAddCourse() {
        return titleAddCourse;
    }

    public JComboBox<Course> getNameCourseInput() {
        return nameCourseInput;
    }

    public JLabel getUserDniLabel() {
        return userDniLabel;
    }

    public JTextField getUserDniInput() {
        return userDniInput;
    }

    public JPanel getLogoPanel() {
        return logoPanel;
    }

    public JLabel getFormTitleLabel() {
        return formTitleLabel;
    }

    public JLabel getNameUserLabel() {
        return nameUserLabel;
    }

    public JLabel getLastNameUserLabel() {
        return lastNameUserLabel;
    }

    public JLabel getNameCourseLabel() {
        return nameCourseLabel;
    }

    public JPanel getUserFormPanel() {
        return userFormPanel;
    }

    public JButton getButtonAddStudent() {
        return buttonAddStudent;
    }

    public JButton getButtonSaveUser() {
        return buttonSaveUser;
    }

    public JScrollPane getUsersScrollPane() {
        return usersScrollPane;
    }

    public JLabel getLogoLabel() {
        return logoLabel;
    }

    public JComboBox<Float> getListNotesStudentInfo() {
        return listNotesStudentInfo;
    }

    public JComboBox<Course> getNameCourseStudentInfo() {
        return nameCourseStudentInfo;
    }

    public JTextField getAverageStudentInfoInput() {
        return averageStudentInfoInput;
    }

    public JTextField getNumSemesterStudentInput() {
        return numSemesterStudentInput;
    }

    public JTextField getUserDniStudentInput() {
        return userDniStudentInput;
    }

    public JTextField getLastNameStudentInput() {
        return lastNameStudentInput;
    }

    public JTextField getUserNameStudentInput() {
        return userNameStudentInput;
    }

    public JTextField getIdStudentInput() {
        return idStudentInput;
    }

    public JLabel getListNotesStudentInfoLabel() {
        return listNotesStudentInfoLabel;
    }

    public JLabel getNameCourseStudentInfoLabel() {
        return nameCourseStudentInfoLabel;
    }

    public JLabel getAverageStudentInfoLabel() {
        return averageStudentInfoLabel;
    }

    public JLabel getNumSemesterStudentInfoLabel() {
        return numSemesterStudentInfoLabel;
    }

    public JLabel getUserDniStudentInfoLabel() {
        return userDniStudentInfoLabel;
    }

    public JLabel getLastNameUserStudentInfoLabel() {
        return lastNameUserStudentInfoLabel;
    }

    public JLabel getUserNameStudentInfoLabel() {
        return userNameStudentInfoLabel;
    }

    public JLabel getIdStudentInfoLabel() {
        return idStudentInfoLabel;
    }

    public JLabel getTitleStudentInfoLabel() {
        return TitleStudentInfoLabel;
    }

    public JPanel getUserFormStudentInfo() {
        return userFormStudentInfo;
    }

    public JPanel getPanelInfoStudents1() {
        return panelInfoStudents1;
    }

    public JPanel getPanelInfoStudents2() {
        return panelInfoStudents2;
    }

    public JPanel getPanelContentPanelsInfo() {
        return panelContentPanelsInfo;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }

    public JLabel getLabelSearch() {
        return labelSearch;
    }

    public JTextField getFieldSearch() {
        return fieldSearch;
    }

    public JButton getButtonSearch() {
        return buttonSearch;
    }

    public JPanel getPanelButtonsSaveAndEdit() {
        return panelButtonsSaveAndEdit;
    }

    public JButton getButtonEditStudent() {
        return buttonEditStudent;
    }

    public JButton getButtonSaveEditStudent() {
        return buttonSaveEditStudent;
    }

    public JPanel getPanelContentButtonAddCourseStudent() {
        return panelContentButtonAddCourseStudent;
    }

    public JButton getButtonAddNote() {
        return buttonAddNote;
    }

    public JButton getButtonAddCourseStudent() {
        return buttonAddCourseStudent;
    }

    public JComboBox<Course> getCascadeCourseByAdd() {
        return cascadeCourseByAdd;
    }

    public JButton getButtonDeleteStudent() {
        return buttonDeleteStudent;
    }
}
