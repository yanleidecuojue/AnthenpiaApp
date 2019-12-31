package club.licona.anthenpiaapp.entity.vo;

public class GetLaudCountVO {
    private String token;
    private int bid;

    public GetLaudCountVO(String token, int bid) {
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
        return "GetLaudCountVO{" +
                "token='" + token + '\'' +
                ", bid=" + bid +
                '}';
    }
}
