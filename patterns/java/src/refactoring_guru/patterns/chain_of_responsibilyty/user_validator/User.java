package refactoring_guru.patterns.chain_of_responsibilyty.user_validator;

public class User {
    private String name;
    private String email;
    private String password;
    private String type = "localUser";

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }
}
