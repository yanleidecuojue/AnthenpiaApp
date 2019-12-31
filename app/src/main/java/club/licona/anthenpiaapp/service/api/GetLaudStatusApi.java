package club.licona.anthenpiaapp.service.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取点赞状态API
 *
 * @author licona
 */

public interface GetLaudStatusApi {
    /**
     * 获取点赞状态
     *
     * @param token 用户token
     * @param bid 博客id
     * @param uid 用户id
     * @return Observable 获取点赞状态返回json字符串，具体内容见接口文档
     */
    @POST("laud/getLaudStatus")
    @FormUrlEncoded
    Observable<String> getLaudStatus(@Field("token") String token, @Field("bid") int bid,
                                     @Field("uid") int uid);
}
