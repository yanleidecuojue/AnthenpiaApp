package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.GetImageLiteralVO;
import club.licona.anthenpiaapp.service.api.GetImageLiteralApi;
import club.licona.anthenpiaapp.util.BaiduRetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author licona
 */
public class GetImageLiteralService {
    /**
     * 获取图片文字
     * <p>
     * 调用服务器获取图片文字接口
     *
     * @param getImageLiteralVO 获取图片文字VO
     * @return 登陆操作返回json结果
     */
    public Observable<String> getImageLiteral(GetImageLiteralVO getImageLiteralVO) {
        return BaiduRetrofitProvider.get().create(GetImageLiteralApi.class)
                .getImageLiteral(getImageLiteralVO.getAccessToken(), getImageLiteralVO.getImage())
                .subscribeOn(Schedulers.io());
    }
}
