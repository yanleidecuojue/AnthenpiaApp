package club.licona.anthenpiaapp.entity.vo;

/**
 * @author licona
 */
public class UserRegisterVO {

    private String username;
    private String password;
    private String nickname;
    private String email;

    public UserRegisterVO() {
    }

    public UserRegisterVO(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname() {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }
}
