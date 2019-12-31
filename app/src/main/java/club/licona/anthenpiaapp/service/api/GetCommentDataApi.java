package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取评论数据API
 *
 * @author licona
 */

public interface GetCommentDataApi {
    /**
     * 获取评论数据
     *
     * @param token 用户token
     * @param bid 博客id
     * @return Observable 获取评论数据返回json字符串，具体内容见接口文档
     */
    @POST("comment/getCommentData")
    @FormUrlEncoded
    Observable<String> getCommentData(@Field("token") String token, @Field("bid") int bid);
}