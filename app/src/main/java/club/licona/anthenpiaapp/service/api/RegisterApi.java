package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 注册API
 *
 * @author licona
 */

public interface RegisterApi {

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param email 邮箱
     * @return Observable 注册返回json字符串，具体内容见接口文档
     */
    @POST("register")
    @FormUrlEncoded
    Observable<String> register(@Field("username") String username, @Field("password") String password,
                                @Field("nickname") String nickname, @Field("email") String email);

}
