package TPBank.repo;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Login implements ILogin{
      Scanner in = new Scanner(System.in);

    private final String ACCOUNT_NUMBER_VALID = "^\\d{10}$";
    private final char[] chars = { '1', 'A', 'a', 'B', 'b', 'C',
            'c', '2', 'D', 'd', 'E', 'e', 'F', 'f', '3', 'G', 'g', 'H', 'h',
            'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', '4', 'M', 'm', 'N', 'n',
            'O', 'o', '5', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't',
            '6', '7', 'U', 'u', 'V', 'v', 'U', 'u', 'W', 'w', '8', 'X', 'x',
            'Y', 'y', 'Z', 'z', '9' };

    int checkInputIntLimit(int min, int max, Locale language) {
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException ex) {
                getWordLanguage(language, "errorCheckInputIntLimit");
                System.out.println();
            }
        }
    }

    String checkInputString(Locale language) {
        while (true) {
            String result = in.nextLine();
            if (result.length() == 0) {
                getWordLanguage(language, "errCheckInputIntLimit");
                System.out.println();
            } else {
                return result;
            }
        }
    }

    @Override
    public int checkInputAccount(Locale language) {
        while (true) {
            String result = in.nextLine();
            if (result.matches(ACCOUNT_NUMBER_VALID)) {
                return Integer.parseInt(result);
            }
            getWordLanguage(language, "errCheckInputAccount");
            System.out.println();
        }
    }

    @Override
    public String checkInputPassword(Locale language) {
        String result;
        while (true) {
            result = checkInputString(language);
            if (isValidPassword(result, language)) {
                return result;
            }
        }
    }

    boolean isValidPassword(String password, Locale language) {
        int lengthPassword = password.length();
        if (lengthPassword < 8 || lengthPassword > 31) {
            getWordLanguage(language, "errCheckLengthPassword");
            System.out.println();
            return false;
        } else {
            int countDigit = 0;
            int countLetter = 0;
            for (int i = 0; i < lengthPassword - 1; i++) {
                if (Character.isDigit(password.charAt(i))) {
                    countDigit++;
                } else if (Character.isLetter(password.charAt(i))) {
                    countLetter++;
                }
            }
            if (countDigit < 1 || countLetter < 1) {
                getWordLanguage(language, "errCheckAlphanumericPassword");
                System.out.println();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkInputCaptcha(String captchaGenerated, Locale language) {
        System.out.println(captchaGenerated);
        getWordLanguage(language, "enterCaptcha");
        String captchaInput = checkInputString(language);
        for (int i = 0; i < captchaInput.length(); i++) {
            if (!captchaGenerated.contains(Character.toString(captchaInput.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    String generateCaptchaText() {
        String randomStrValue = "";
        final int LENGTH = 6;
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (int i = 0; i < LENGTH; i++) {
            index = (int) (Math.random() * (chars.length - 1));
            sb.append(chars[index]);
        }
        return sb.toString();
    }

    void getWordLanguage(Locale curLocate, String key) {
        ResourceBundle words = ResourceBundle.getBundle("Resources/" + curLocate, curLocate);
        String value = words.getString(key);
        System.out.printf(value);
    }

    public void login(Locale language) {
        getWordLanguage(language, "enterAccountNumber");
        int accountNumber = checkInputAccount(language);
        getWordLanguage(language, "enterPassword");
        String passString = checkInputPassword(language);
        String captchaGenerated = generateCaptchaText();
        while (true) {
            if (checkInputCaptcha(captchaGenerated, language)) {
                getWordLanguage(language, "loginSuccess");
                System.out.println();
                return;
            } else {
                getWordLanguage(language, "errCaptchaIncorrect");
                System.out.println();
            }
        }
    }
}
