package club.licona.widget.banner.indicator;

public interface Indicator {

    int CIRCLE = 1;
    int RECTANGLE = 2;

    void setCellCount(int cellCount);

    void setCurrentPosition(int currentPosition);
}
