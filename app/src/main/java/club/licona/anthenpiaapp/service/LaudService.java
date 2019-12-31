package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.CancelLaudVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudStatusVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudUserVO;
import club.licona.anthenpiaapp.entity.vo.GiveLaudVO;
import club.licona.anthenpiaapp.service.api.CancelLaudApi;
import club.licona.anthenpiaapp.service.api.GetHeadPortraitApi;
import club.licona.anthenpiaapp.service.api.GetLaudCountApi;
import club.licona.anthenpiaapp.service.api.GetLaudStatusApi;
import club.licona.anthenpiaapp.service.api.GetLaudUserApi;
import club.licona.anthenpiaapp.service.api.GiveLaudApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class LaudService {
    /**
     * 获取点赞状态
     * <p>
     * 调用服务器获取点赞状态接口
     *
     * @param getLaudStatusVO 获取点赞状态VO
     * @return 获取点赞状态操作返回json结果
     */
    public Observable<String> getLaudStatus(GetLaudStatusVO getLaudStatusVO) {
        return RetrofitProvider.get().create(GetLaudStatusApi.class)
                .getLaudStatus(getLaudStatusVO.getToken(), getLaudStatusVO.getBid(),
                        getLaudStatusVO.getUid())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 点赞
     * <p>
     * 调用服务器点赞接口
     *
     * @param giveLaudVO 点赞VO
     * @return 点赞操作返回json结果
     */
    public Observable<String> giveLaud(GiveLaudVO giveLaudVO) {
        return RetrofitProvider.get().create(GiveLaudApi.class)
                .giveLaud(giveLaudVO.getToken(), giveLaudVO.getBid(), giveLaudVO.getStatus())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 取消点赞
     * <p>
     * 调用服务器取消点赞接口
     *
     * @param cancelLaudVO 取消点赞VO
     * @return 取消点赞操作返回json结果
     */
    public Observable<String> cancelLaud(CancelLaudVO cancelLaudVO) {
        return RetrofitProvider.get().create(CancelLaudApi.class)
                .cancelLaud(cancelLaudVO.getToken(), cancelLaudVO.getBid(), cancelLaudVO.getUid())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取博客点赞数量
     * <p>
     * 调用服务器获取博客点赞数量接口
     *
     * @param getLaudCountVO 获取博客点赞数量VO
     * @return 获取博客点赞数量操作返回json结果
     */
    public Observable<String> getLaudCount(GetLaudCountVO getLaudCountVO) {
        return RetrofitProvider.get().create(GetLaudCountApi.class)
                .getLaudCount(getLaudCountVO.getToken(), getLaudCountVO.getBid())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取博客点赞用户信息
     * <p>
     * 调用服务器获取博客点赞用户信息接口
     *
     * @param getLaudUserVO 获取博客点赞用户信息VO
     * @return 获取博客点赞用户信息操作返回json结果
     */
    public Observable<String> getLaudUser(GetLaudUserVO getLaudUserVO) {
        return RetrofitProvider.get().create(GetLaudUserApi.class)
                .getLaudUser(getLaudUserVO.getToken(), getLaudUserVO.getBid())
                .subscribeOn(Schedulers.io());
    }
}
