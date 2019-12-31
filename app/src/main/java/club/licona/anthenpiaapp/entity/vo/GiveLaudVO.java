package club.licona.anthenpiaapp.entity.vo;

public class GiveLaudVO {
    private String token;
    private int bid;
    private int status;

    public GiveLaudVO(String token, int bid, int status) {
        this.token = token;
        this.bid = bid;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GiveLaudVO{" +
                "token='" + token + '\'' +
                ", bid=" + bid +
                ", status=" + status +
                '}';
    }
}
