package TPBank.repo;

import java.util.Locale;

public interface ILogin {
    public int checkInputAccount(Locale language);
    String checkInputPassword(Locale language);
    boolean checkInputCaptcha(String captchaGenerated, Locale language);
}
