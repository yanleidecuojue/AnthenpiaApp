package club.licona.anthenpiaapp.entity.vo;

public class AddReplyVO {
    private String token;
    private int cid;
    private int replyType;
    private String toUsername;
    private String content;

    public AddReplyVO(String token, int cid, int replyType, String toUsername, String content) {
        this.token = token;
        this.cid = cid;
        this.replyType = replyType;
        this.toUsername = toUsername;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getReplyType() {
        return replyType;
    }

    public void setReplyType(int replyType) {
        this.replyType = replyType;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AddReplyVO{" +
                "token='" + token + '\'' +
                ", cid=" + cid +
                ", replyType=" + replyType +
                ", toUsername='" + toUsername + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
