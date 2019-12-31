package club.licona.anthenpiaapp.entity.vo;

import java.util.List;

public class ImageLiteralResponseVO<T> {
    private long logId;
    private long wordsResultNum;
    private List wordsResult;

    public ImageLiteralResponseVO(long logId, long wordsResultNum, List wordsResult) {
        this.logId = logId;
        this.wordsResultNum = wordsResultNum;
        this.wordsResult = wordsResult;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public long getWordsResultNum() {
        return wordsResultNum;
    }

    public void setWordsResultNum(long wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }

    public List getWordsResult() {
        return wordsResult;
    }

    public void setWordsResult(List wordsResult) {
        this.wordsResult = wordsResult;
    }

    @Override
    public String toString() {
        return "ImageLiteralResponseVO{" +
                "logId=" + logId +
                ", wordsResultNum=" + wordsResultNum +
                ", wordsResult=" + wordsResult +
                '}';
    }
}
