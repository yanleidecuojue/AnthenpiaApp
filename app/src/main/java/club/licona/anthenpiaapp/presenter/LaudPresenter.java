package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import club.licona.anthenpiaapp.entity.vo.CancelLaudVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudStatusVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudUserVO;
import club.licona.anthenpiaapp.entity.vo.GiveLaudVO;
import club.licona.anthenpiaapp.entity.vo.LaudUserVO;
import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.service.LaudService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class LaudPresenter {

    private LaudService laudService;

    public LaudPresenter() {
        this.laudService = new LaudService();
    }

    /**
     * 获取点赞状态操作
     *
     * @param getLaudStatusObservable 获取点赞状态事件Observable
     * @return 获取点赞状态操作结果Observable
     */
    public Observable<ResponseVO> getLaudStatus(Observable<GetLaudStatusVO> getLaudStatusObservable) {
        return getLaudStatusObservable.observeOn(Schedulers.io())
                .flatMap(laudService::getLaudStatus)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if(code == 0) {
                        return new ResponseVO<>(code, msg, head.get("data").toString());
                    } else return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 点赞操作
     *
     * @param giveLaudObservable 点赞事件Observable
     * @return 点赞操作结果Observable
     */
    public Observable<ResponseVO> giveLaud(Observable<GiveLaudVO> giveLaudObservable) {
        return giveLaudObservable.observeOn(Schedulers.io())
                .flatMap(laudService::giveLaud)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 取消点赞操作
     *
     * @param cancelLaudObservable 取消点赞事件Observable
     * @return 取消点赞操作结果Observable
     */
    public Observable<ResponseVO> cancelLaud(Observable<CancelLaudVO> cancelLaudObservable) {
        return cancelLaudObservable.observeOn(Schedulers.io())
                .flatMap(laudService::cancelLaud)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 获取博客点赞数量操作
     *
     * @param getLaudCountObservable 获取博客点赞数量事件Observable
     * @return 获取博客点赞数量操作结果Observable
     */
    public Observable<ResponseVO> getLaudCount(Observable<GetLaudCountVO> getLaudCountObservable) {
        return getLaudCountObservable.observeOn(Schedulers.io())
                .flatMap(laudService::getLaudCount)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if(code == 0) {
                        int data = head.get("data").asInt();
                        return new ResponseVO<>(code, msg, data);
                    }
                    return new ResponseVO<>(code, msg);
                });
    }

    /**
     * 获取博客点赞用户信息操作
     *
     * @param getLaudUserObservable 获取博客点赞用户信息事件Observable
     * @return 获取博客点赞用户信息操作结果Observable
     */
    public Observable<ResponseVO<List<LaudUserVO>>> getLaudUser(Observable<GetLaudUserVO> getLaudUserObservable) {
        return getLaudUserObservable.observeOn(Schedulers.io())
                .flatMap(laudService::getLaudUser)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if(code == 0) {
                        LaudUserVO[] vo = objectMapper.readValue(head.get("data").toString(), LaudUserVO[].class);
                        List<LaudUserVO> laudUserVOS = Arrays.stream(vo).collect(Collectors.toList());
                        return new ResponseVO<>(code, msg, laudUserVOS);
                    }
                    return new ResponseVO<>(code, msg);
                });
    }
}
