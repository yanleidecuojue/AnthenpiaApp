package club.licona.anthenpiaapp.entity.vo;

public class GetImageLiteralVO {
    private String accessToken;
    private String image;

    public GetImageLiteralVO(String accessToken, String image) {
        this.accessToken = accessToken;
        this.image = image;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "GetImageLiteralVO{" +
                "accessToken='" + accessToken + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
