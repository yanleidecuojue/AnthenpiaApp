package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 登陆API
 *
 * @author licona
 */

public interface LoginApi {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return Observable 登陆返回json字符串，具体内容见接口文档
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<String> login(@Field("username") String username, @Field("password") String password);

}
