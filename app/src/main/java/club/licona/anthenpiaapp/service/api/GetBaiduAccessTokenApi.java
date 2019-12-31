package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取百度AccessTokenAPI
 *
 * @author licona
 */

public interface GetBaiduAccessTokenApi {
    /**
     * 获取百度AccessToken
     *
     * @param grantType 必须参数，固定为client_credentials
     * @param clientId 必须参数，应用的API Key
     * @param clientSecret 必须参数，应用的Secret Key
     * @return Observable 获取百度AccessToken返回json字符串，具体内容见接口文档
     */
    @POST("/oauth/2.0/token")
    @FormUrlEncoded
    Observable<String> getBaiduAccessToken(@Field("grant_type") String grantType, @Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret);

}
