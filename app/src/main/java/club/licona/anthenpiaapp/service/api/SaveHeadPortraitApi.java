package club.licona.anthenpiaapp.service.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author licona
 */
public interface SaveHeadPortraitApi {
    @Multipart
    @POST("headPortrait/saveHeadPortrait")
    Observable<String> saveHeadPortrait(@Header("token") String token, @Part MultipartBody.Part header);
}
