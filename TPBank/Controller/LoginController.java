package TPBank.Controller;

import java.util.Locale;
import java.util.Scanner;

import TPBank.repo.Login;
import TPBank.view.Menu;

public class LoginController extends Menu<String> {
    private Login login;
    static String[] options = {
        "Vietnamese",
        "English",
        "Exit"
    };
    Scanner sc = new Scanner(System.in);

    public LoginController() {
        super("========= LOGIN PROGRAM  =========", options);
        login = new Login();
    }

    @Override
    public void execute(int choice) {
        switch (choice) {
            case 1:
                login.login(new Locale("messages_vi"));
                break;

            case 2:
                login.login(new Locale("messages_en"));
                break;

            case 3:
                System.exit(0);
        }
    }
}
