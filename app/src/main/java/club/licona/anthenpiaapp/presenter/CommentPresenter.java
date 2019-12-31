package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import club.licona.anthenpiaapp.bean.CommentDetailBean;
import club.licona.anthenpiaapp.entity.vo.AddCommentVO;
import club.licona.anthenpiaapp.entity.vo.CommentInfoVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentCountVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentDataVO;
import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.service.CommentService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class CommentPresenter {

    private CommentService commentService;

    public CommentPresenter() {
        this.commentService = new CommentService();
    }

    /**
     * 添加博客评论操作
     *
     * @param addCommentObservable 添加博客评论事件Observable
     * @return 添加博客评论操作结果Observable
     */
    public Observable<ResponseVO<CommentDetailBean>> addComment(Observable<AddCommentVO> addCommentObservable) {
        return addCommentObservable.observeOn(Schedulers.io())
                .flatMap(commentService::addComment)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        CommentDetailBean commentDetailBean = objectMapper.readValue(head.get("data").toString(), CommentDetailBean.class);
                        return new ResponseVO<>(code, msg, commentDetailBean);
                    } else return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 获取评论数量操作
     *
     * @param getCommentCountObservable 获取评论数量事件Observable
     * @return 获取评论数量操作结果Observable
     */
    public Observable<ResponseVO> getCommentCount(Observable<GetCommentCountVO> getCommentCountObservable) {
        return getCommentCountObservable.observeOn(Schedulers.io())
                .flatMap(commentService::getCommentCount)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        return new ResponseVO<>(code, msg, head.get("data"));
                    } else return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 获取评论数据操作
     *
     * @param getCommentDataObservable 获取评论数据事件Observable
     * @return 获取评论数量操作结果Observable
     */
    public Observable<ResponseVO<CommentDetailBean[]>> getCommentData(Observable<GetCommentDataVO> getCommentDataObservable) {
        return getCommentDataObservable.observeOn(Schedulers.io())
                .flatMap(commentService::getCommentData)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);

                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        CommentDetailBean[] commentDetailBeans = objectMapper.readValue(head.get("data").get("list").toString(), CommentDetailBean[].class);
                        return new ResponseVO(code, msg, commentDetailBeans);
                    } else return new ResponseVO(code, msg);
                });
    }
}