package uni;

import uni.controller.AppController;
import uni.models.Course;
import uni.repository.AppRepository;
import uni.view.AppUi;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //instance of view
        AppUi app = new AppUi();

        //instance of course class
        Course course = new Course();

        //instance of data persistence
        AppRepository repository = new AppRepository();

        //instance of controller
        AppController controller = new AppController(app, course, repository);

        app.showApp();
        controller.init();
    }
}