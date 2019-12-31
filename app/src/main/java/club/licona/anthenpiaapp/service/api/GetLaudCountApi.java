package club.licona.anthenpiaapp.service.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取博客点赞数量API
 *
 * @author licona
 */

public interface GetLaudCountApi {
    /**
     * 获取博客点赞数量
     *
     * @param token 用户token
     * @param bid 博客id
     * @return Observable 获取博客点赞数量返回json字符串，具体内容见接口文档
     */
    @POST("laud/getLaudCount")
    @FormUrlEncoded
    Observable<String> getLaudCount(@Field("token") String token, @Field("bid") int bid);
}