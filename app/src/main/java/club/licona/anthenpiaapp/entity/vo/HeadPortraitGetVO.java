package club.licona.anthenpiaapp.entity.vo;

public class HeadPortraitGetVO {
    private String token;

    public HeadPortraitGetVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "HeadPortraitGetVO{" +
                "token='" + token + '\'' +
                '}';
    }
}
