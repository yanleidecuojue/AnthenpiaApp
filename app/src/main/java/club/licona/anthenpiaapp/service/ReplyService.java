package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.AddReplyVO;
import club.licona.anthenpiaapp.service.api.AddReplyApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class ReplyService {
    /**
     * 评论回复
     * <p>
     * 调用服务器评论回复接口
     *
     * @param addReplyVO 评论回复VO
     * @return 评论回复操作返回json结果
     */
    public Observable<String> addReply(AddReplyVO addReplyVO) {
        return RetrofitProvider.get().create(AddReplyApi.class)
                .addReply(addReplyVO.getToken(), addReplyVO.getCid(),
                        addReplyVO.getReplyType(), addReplyVO.getToUsername(),
                        addReplyVO.getContent())
                .subscribeOn(Schedulers.io());
    }
}