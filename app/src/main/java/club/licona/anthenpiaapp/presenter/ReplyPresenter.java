package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.licona.anthenpiaapp.bean.ReplyDetailBean;
import club.licona.anthenpiaapp.entity.vo.AddReplyVO;
import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.service.ReplyService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class ReplyPresenter {

    private ReplyService replyService;

    public ReplyPresenter() {
        this.replyService = new ReplyService();
    }

    /**
     * 回复评论操作
     *
     * @param addReplyObservable 回复评论事件Observable
     * @return 回复评论操作结果Observable
     */
    public Observable<ResponseVO<ReplyDetailBean>> addReply(Observable<AddReplyVO> addReplyObservable) {
        return addReplyObservable.observeOn(Schedulers.io())
                .flatMap(replyService::addReply)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if(code == 0) {
                        ReplyDetailBean replyDetailBean = objectMapper.readValue(head.get("data").toString(), ReplyDetailBean.class);
                        return new ResponseVO<>(code, msg, replyDetailBean);
                    } else return  new ResponseVO<>(code, msg);
                });
    }
}
