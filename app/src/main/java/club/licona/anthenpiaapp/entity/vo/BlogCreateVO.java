package club.licona.anthenpiaapp.entity.vo;

/**
 * @author licona
 */
public class BlogCreateVO {
    private String token;
    private String text;
    private String content;

    public BlogCreateVO(String token, String text, String content) {
        this.token = token;
        this.text = text;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
