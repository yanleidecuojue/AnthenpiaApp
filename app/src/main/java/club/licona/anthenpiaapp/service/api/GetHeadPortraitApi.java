package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取用户头像API
 *
 * @author licona
 */
public interface GetHeadPortraitApi {
    /**
     * 获取用户头像
     *
     * @param token 登录用户token
     * @return Observable 获取用户头像返回字符串，具体内容见接口文档
     */
    @POST("headPortrait/getHeadPortrait")
    @FormUrlEncoded
    Observable<String> getHeadPortrait(@Field("token") String token);

}
