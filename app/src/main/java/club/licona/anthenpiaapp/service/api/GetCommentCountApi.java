package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取评论数量API
 *
 * @author licona
 */

public interface GetCommentCountApi {
    /**
     * 获取评论数量
     *
     * @param token 用户token
     * @param bid 博客id
     * @return Observable 获取评论数量返回json字符串，具体内容见接口文档
     */
    @POST("comment/getCommentCount")
    @FormUrlEncoded
    Observable<String> getCommentCount(@Field("token") String token, @Field("bid") int bid);
}