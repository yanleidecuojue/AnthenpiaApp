package club.licona.anthenpiaapp.entity.vo;

public class ImageLiteralInfoVO {
    String words;


    public ImageLiteralInfoVO() {
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "ImageLiteralInfoVO{" +
                "words='" + words + '\'' +
                '}';
    }
}
