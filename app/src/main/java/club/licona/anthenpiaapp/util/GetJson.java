package club.licona.anthenpiaapp.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 读取assets文件夹下的json数据
 *
 * @author licona
 */
public class GetJson {

    public static String getJson(String fileName, Context context) {
        /**
         * 将json数据变为字符串
         */
        StringBuilder stringBuilder = new StringBuilder();
        try {
            /**
             * 获取assets资源管理器
             */
            AssetManager assetManager = context.getAssets();
            /**
             * 通过管理器打开文件并获取
             */
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
