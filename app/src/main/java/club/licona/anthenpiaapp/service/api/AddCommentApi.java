package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 添加博客评论API
 *
 * @author licona
 */

public interface AddCommentApi {
    /**
     * 添加博客评论
     * @param token 用户token
     * @param bid 博客id
     * @param uid 用户id
     * @param content 评论内容
     * @return Observable 添加博客评论返回json字符串，具体内容见接口文档
     */
    @POST("comment/addComment")
    @FormUrlEncoded
    Observable<String> addComment(@Field("token") String token, @Field("bid") int  bid,
                             @Field("uid") int uid, @Field("content") String content);
}
