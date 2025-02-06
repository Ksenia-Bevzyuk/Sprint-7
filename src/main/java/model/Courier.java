package model;

import java.util.Random;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public static String generationLogin() {
        Random random = new Random();

        StringBuilder login = new StringBuilder();
        for(int i = 0; i< 7;i++) {
            char character = (char) (random.nextInt(26) + 'a');
            login.append(character);
        }

        return login.toString();
    }
}