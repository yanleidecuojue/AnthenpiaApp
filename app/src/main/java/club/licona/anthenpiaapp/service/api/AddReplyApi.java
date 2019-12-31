package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 评论回复API
 *
 * @author licona
 */

public interface AddReplyApi {
    /**
     * 评论回复
     * @param token 用户token
     * @param cid 评论id
     * @param reply_type 回复类型，0为回复评论，1为回复回复
     * @param toUsername 目标用户账号
     * @param content 回复内容
     * @return Observable 评论回复返回json字符串，具体内容见接口文档
     */
    @POST("reply/addReply")
    @FormUrlEncoded
    Observable<String> addReply(@Field("token") String token, @Field("cid") int  cid,
                                @Field("reply_type") int reply_type, @Field("toUsername") String toUsername,
                                @Field("content") String content);
}
