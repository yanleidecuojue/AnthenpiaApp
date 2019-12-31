package club.licona.anthenpiaapp.entity.vo;

import java.util.Date;

/**
 * @author licona
 */
public class BlogInfoVO {
    private int id;
    private int uid;
    private Date time;
    private String text;
    private String content;
    private String username;
    private String headdir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeaddir() {
        return headdir;
    }

    public void setHeaddir(String headdir) {
        this.headdir = headdir;
    }

    @Override
    public String toString() {
        return "BlogInfoVO{" +
                "id=" + id +
                ", uid=" + uid +
                ", time=" + time +
                ", text='" + text + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", headdir='" + headdir + '\'' +
                '}';
    }
}
