package club.licona.anthenpiaapp.entity.vo;

import android.graphics.Bitmap;

/**
 * @author licona
 */
public class OccupationInfoVO {
    private String name;
    private String desc;
    private Bitmap bitmap;

    public OccupationInfoVO(String name, String desc, Bitmap bitmap) {
        this.name = name;
        this.desc = desc;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
