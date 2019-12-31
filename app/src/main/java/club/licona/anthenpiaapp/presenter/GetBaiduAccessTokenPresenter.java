package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.licona.anthenpiaapp.entity.vo.BaiduAccessTokenResponseVO;
import club.licona.anthenpiaapp.entity.vo.GetBaiduAccessTokenVO;
import club.licona.anthenpiaapp.service.GetBaiduAccessTokenService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class GetBaiduAccessTokenPresenter {

    private GetBaiduAccessTokenService getBaiduAccessTokenService;

    public GetBaiduAccessTokenPresenter() {
        this.getBaiduAccessTokenService = new GetBaiduAccessTokenService();
    }

    /**
     * 获取百度AccessToken操作
     *
     * @param getBaiduAccessTokenObservable 获取百度AccessToken事件Observable
     * @return 获取百度AccessToken操作结果Observable
     */
    public Observable<BaiduAccessTokenResponseVO> getBaiduAccessToken(Observable<GetBaiduAccessTokenVO> getBaiduAccessTokenObservable) {
        return getBaiduAccessTokenObservable.observeOn(Schedulers.io())
                .flatMap(getBaiduAccessTokenService::getBaiduAccessToken)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    String expiresIn = head.get("expires_in").asText();
                    String accessToken = head.get("access_token").asText();
                    return new BaiduAccessTokenResponseVO(expiresIn, accessToken);
                });
    }
}