package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.AddCommentVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentCountVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentDataVO;
import club.licona.anthenpiaapp.service.api.AddCommentApi;
import club.licona.anthenpiaapp.service.api.GetCommentCountApi;
import club.licona.anthenpiaapp.service.api.GetCommentDataApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class CommentService {
    /**
     * 添加博客评论
     * <p>
     * 调用服务器添加博客评论接口
     *
     * @param addCommentVO 添加博客评论VO
     * @return 添加博客评论操作返回json结果
     */
    public Observable<String> addComment(AddCommentVO addCommentVO) {
        return RetrofitProvider.get().create(AddCommentApi.class)
                .addComment(addCommentVO.getToken(), addCommentVO.getBid(),
                        addCommentVO.getBid(), addCommentVO.getContent())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取评论数量
     * <p>
     * 调用服务器获取评论数量接口
     *
     * @param getCommentCountVO 获取评论数量VO
     * @return 获取评论数量操作返回json结果
     */
    public Observable<String> getCommentCount(GetCommentCountVO getCommentCountVO) {
        return RetrofitProvider.get().create(GetCommentCountApi.class)
                .getCommentCount(getCommentCountVO.getToken(), getCommentCountVO.getBid())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取评论数据
     * <p>
     * 调用服务器获取评论数据接口
     *
     * @param getCommentDataVO 获取评论数据VO
     * @return 获取评论数据操作返回json结果
     */
    public Observable<String> getCommentData(GetCommentDataVO getCommentDataVO) {
        return RetrofitProvider.get().create(GetCommentDataApi.class)
                .getCommentData(getCommentDataVO.getToken(), getCommentDataVO.getBid())
                .subscribeOn(Schedulers.io());
    }
}