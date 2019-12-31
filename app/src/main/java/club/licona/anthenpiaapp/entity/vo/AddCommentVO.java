package club.licona.anthenpiaapp.entity.vo;

public class AddCommentVO {
    private String token;
    private int bid;
    private int uid;
    private String content;

    public AddCommentVO(String token, int bid, int uid, String content) {
        this.token = token;
        this.bid = bid;
        this.uid = uid;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AddCommentVO{" +
                "token='" + token + '\'' +
                ", bid=" + bid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                '}';
    }
}
