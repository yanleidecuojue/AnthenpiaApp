package club.licona.anthenpiaapp.entity.vo;

import okhttp3.MultipartBody;

public class HeadPortraitSaveVO {
    private String token;
    private MultipartBody.Part part;

    public HeadPortraitSaveVO(String token, MultipartBody.Part part) {
        this.token = token;
        this.part = part;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }
}
