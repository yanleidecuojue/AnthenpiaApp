package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.GetBaiduAccessTokenVO;
import club.licona.anthenpiaapp.service.api.GetBaiduAccessTokenApi;
import club.licona.anthenpiaapp.util.BaiduRetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author licona
 */
public class GetBaiduAccessTokenService {
    /**
     * 获取百度AccessToken
     * <p>
     * 调用服务器获取百度AccessToken接口
     *
     * @param getBaiduAccessTokenVO 获取百度AccessTokenVO
     * @return 获取百度AccessToken操作返回json结果
     */
    public Observable<String> getBaiduAccessToken(GetBaiduAccessTokenVO getBaiduAccessTokenVO) {
        return BaiduRetrofitProvider.get().create(GetBaiduAccessTokenApi.class)
                .getBaiduAccessToken(getBaiduAccessTokenVO.getGrantType(), getBaiduAccessTokenVO.getClientId(),
                        getBaiduAccessTokenVO.getClientSecret())
                .subscribeOn(Schedulers.io());
    }
}
