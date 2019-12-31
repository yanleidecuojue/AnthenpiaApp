package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取博客点赞用户信息API
 *
 * @author licona
 */
public interface GetLaudUserApi {
    /**
     * 获取博客点赞用户信息
     *
     * @param token 登录用户token
     * @param bid 博客id
     * @return Observable 获取博客点赞用户信息返回字符串，具体内容见接口文档
     */
    @POST("laud/getLaudUser")
    @FormUrlEncoded
    Observable<String> getLaudUser(@Field("token") String token, @Field("bid") int bid);
}
