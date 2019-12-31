package club.licona.anthenpiaapp.util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Baidu Retrofit单例类
 *
 * 提供Retrofit实例
 *
 * @author licona
 */

public class BaiduRetrofitProvider {

    private static final String BASE_URL = "https://aip.baidubce.com";
    private static volatile Retrofit retrofitInstance;

    public static Retrofit get() {
        if (retrofitInstance == null) {
            synchronized (RetrofitProvider.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(JacksonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();
                }
            }
        }
        return retrofitInstance;
    }
}
