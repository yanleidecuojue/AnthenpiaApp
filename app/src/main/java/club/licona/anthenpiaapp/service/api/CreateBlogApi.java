package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 创建博客API
 *
 * @author licona
 */

public interface CreateBlogApi {

    /**
     * 创建博客
     *
     * @param token 登录用户token
     * @param text 博客标题
     * @param content 博客内容
     * @return Observable 创建博客返回字符串，具体内容见接口文档
     */
    @POST("blog/createBlog")
    @FormUrlEncoded
    Observable<String> createBlog(@Field("token") String token, @Field("text") String text,
                                  @Field("content") String content);

}