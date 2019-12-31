package club.licona.anthenpiaapp.entity.vo;

public class LaudInfoVO {
    private int id;
    private int bid;
    private int uid;
    private int status;

    public LaudInfoVO() {
    }

    public LaudInfoVO(int id, int bid, int uid, int status) {
        this.id = id;
        this.bid = bid;
        this.uid = uid;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LaudInfoVO{" +
                "id=" + id +
                ", bid=" + bid +
                ", uid=" + uid +
                ", status=" + status +
                '}';
    }
}
