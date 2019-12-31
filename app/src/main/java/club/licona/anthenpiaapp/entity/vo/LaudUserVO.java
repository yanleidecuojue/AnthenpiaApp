package club.licona.anthenpiaapp.entity.vo;

public class LaudUserVO {
    private String username;
    private String email;
    private String headdir;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeaddir() {
        return headdir;
    }

    public void setHeaddir(String headdir) {
        this.headdir = headdir;
    }

    @Override
    public String toString() {
        return "LaudUserVO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", headdir='" + headdir + '\'' +
                '}';
    }
}
