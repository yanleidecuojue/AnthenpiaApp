package club.licona.anthenpiaapp.entity.vo;

public class CancelLaudVO {
    private String token;
    private int bid;
    private int uid;

    public CancelLaudVO(String token, int bid, int uid) {
        this.token = token;
        this.bid = bid;
        this.uid = uid;
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

    @Override
    public String toString() {
        return "CancelLaudVO{" +
                "token='" + token + '\'' +
                ", bid=" + bid +
                ", uid=" + uid +
                '}';
    }
}
