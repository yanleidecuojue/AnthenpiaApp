package club.licona.anthenpiaapp.entity.vo;

public class BaiduAccessTokenResponseVO {
    private String expiresIn;
    private String accessToken;

    public BaiduAccessTokenResponseVO(String expiresIn, String accessToken) {
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "BaiduAccessTokenResponseVO{" +
                "expiresIn='" + expiresIn + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
