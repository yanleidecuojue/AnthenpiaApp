package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取图片文字API
 *
 * @author licona
 */

public interface GetImageLiteralApi {
    /**
     * 获取图片文字
     *
     * @param accessToken 通过API Key和Secret Key获取的access_token
     * @param image 图像数据base64编码后进行urlencode后的String
     * @return Observable 获取图片文字返回json字符串，具体内容见接口文档
     */
    @POST("/rest/2.0/ocr/v1/general_basic")
    @FormUrlEncoded
    Observable<String> getImageLiteral(@Field("access_token") String accessToken, @Field("image") String image);
}