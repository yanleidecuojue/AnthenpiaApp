package club.licona.anthenpiaapp.entity.vo;

import java.util.Date;

public class CommentInfoVO {
    private int id;
    private int bid;
    private int uid;
    private Date time;
    private String content;

    public CommentInfoVO() {
    }

    public CommentInfoVO(int id, int bid, int uid, Date time, String content) {
        this.id = id;
        this.bid = bid;
        this.uid = uid;
        this.time = time;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentInfoVO{" +
                "id=" + id +
                ", bid=" + bid +
                ", uid=" + uid +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
