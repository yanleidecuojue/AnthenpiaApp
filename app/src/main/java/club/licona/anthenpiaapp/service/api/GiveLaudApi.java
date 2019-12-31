package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 点赞API
 *
 * @author licona
 */
public interface GiveLaudApi {
    /**
     * 点赞
     *
     * @param token 登录用户token
     * @param bid 博客id
     * @param status 点赞状态
     * @return Observable 点赞返回字符串，具体内容见接口文档
     */
    @POST("laud/giveLaud")
    @FormUrlEncoded
    Observable<String> giveLaud(@Field("token") String token, @Field("bid") int bid,
                                @Field("status") int status);

}
