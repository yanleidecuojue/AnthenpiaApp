package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 取消点赞API
 *
 * @author licona
 */

public interface CancelLaudApi {

    /**
     * 取消点赞
     *
     * @param token 登录用户token
     * @param bid 博客id
     * @param uid 用户id
     * @return Observable 取消点赞返回字符串，具体内容见接口文档
     */
    @POST("laud/cancelLaud")
    @FormUrlEncoded
    Observable<String> cancelLaud(@Field("token") String token, @Field("bid") int bid,
                                  @Field("uid") int uid);

}