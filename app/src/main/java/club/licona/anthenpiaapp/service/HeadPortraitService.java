package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.HeadPortraitGetVO;
import club.licona.anthenpiaapp.entity.vo.HeadPortraitSaveVO;
import club.licona.anthenpiaapp.service.api.GetHeadPortraitApi;
import club.licona.anthenpiaapp.service.api.SaveHeadPortraitApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class HeadPortraitService {
    /**
     * 上传用户头像
     * <p>
     * 调用服务器上传用户头像接口
     *
     * @param headPortraitSaveVO 上传用户头像VO
     * @return 上传用户头像操作返回json结果
     */
    public Observable<String> saveHeadPortrait(HeadPortraitSaveVO headPortraitSaveVO) {
        return RetrofitProvider.get().create(SaveHeadPortraitApi.class)
                .saveHeadPortrait(headPortraitSaveVO.getToken(), headPortraitSaveVO.getPart())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户头像路径
     * <p>
     * 调用服务器获取用户头像路径接口
     *
     * @param headPortraitGetVO 获取用户头像VO
     * @return 获取用户头像操作返回json结果
     */
    public Observable<String> getHeadPortrait(HeadPortraitGetVO headPortraitGetVO) {
        return RetrofitProvider.get().create(GetHeadPortraitApi.class)
                .getHeadPortrait(headPortraitGetVO.getToken())
                .subscribeOn(Schedulers.io());
    }
}
