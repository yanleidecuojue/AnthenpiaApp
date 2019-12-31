package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 列出所有博客API
 *
 * @author licona
 */

public interface ListAllBlogApi {

    /**
     * 列出所有博客
     *
     * @param token 登录用户token
     * @return Observable 列出所有博客返回字符串，具体内容见接口文档
     */
    @POST("blog/listAllBlog")
    @FormUrlEncoded
    Observable<String> listAllBlog(@Field("token") String token);

}