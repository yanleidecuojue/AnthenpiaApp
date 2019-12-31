package club.licona.anthenpiaapp.entity.vo;

public class GetCommentDataVO {
    private String token;
    private int bid;

    public GetCommentDataVO(String token, int bid) {
        this.token = token;
        this.bid = bid;
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

    @Override
    public String toString() {
        return "GetCommentDataVO{" +
                "token='" + token + '\'' +
                ", bid=" + bid +
                '}';
    }
}
