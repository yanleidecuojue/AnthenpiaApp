package club.licona.anthenpiaapp.bean;

import java.util.Date;

public class ReplyDetailBean {
    private int id;
    private int cid;
    private int reply_type;
    private String fromUsername;
    private String fromHeaddir;
    private String toUsername;
    private String toHeaddir;
    private Date time;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getReply_type() {
        return reply_type;
    }

    public void setReply_type(int reply_type) {
        this.reply_type = reply_type;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getFromHeaddir() {
        return fromHeaddir;
    }

    public void setFromHeaddir(String fromHeaddir) {
        this.fromHeaddir = fromHeaddir;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getToHeaddir() {
        return toHeaddir;
    }

    public void setToHeaddir(String toHeaddir) {
        this.toHeaddir = toHeaddir;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReplyDetailBean{" +
                "id=" + id +
                ", cid=" + cid +
                ", reply_type=" + reply_type +
                ", fromUsername='" + fromUsername + '\'' +
                ", fromHeaddir='" + fromHeaddir + '\'' +
                ", toUsername='" + toUsername + '\'' +
                ", toHeaddir='" + toHeaddir + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
