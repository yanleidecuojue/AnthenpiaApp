package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.licona.anthenpiaapp.entity.vo.HeadPortraitGetVO;
import club.licona.anthenpiaapp.entity.vo.HeadPortraitSaveVO;
import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.service.HeadPortraitService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class HeadPortraitPresenter {
    private HeadPortraitService headPortraitService;

    public HeadPortraitPresenter() {
        this.headPortraitService = new HeadPortraitService();
    }

    /**
     * 上传用户头像
     *
     * @param saveHeadPortraitObservable 上传用户头像事件Observable
     * @return 上传用户头像操作结果Observable
     */
    public Observable<ResponseVO> saveHeadPortrait(Observable<HeadPortraitSaveVO> saveHeadPortraitObservable) {
        return saveHeadPortraitObservable.observeOn(Schedulers.io())
                .flatMap(headPortraitService::saveHeadPortrait)
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
     * 获取用户头像路径
     *
     * @param getHeadPortraitObservable 获取用户头像路径事件Observable
     * @return 获取用户头像路径操作结果Observable
     */
    public Observable<ResponseVO> getHeadPortrait(Observable<HeadPortraitGetVO> getHeadPortraitObservable) {
        return getHeadPortraitObservable.observeOn(Schedulers.io())
                .flatMap(headPortraitService::getHeadPortrait)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    String data = head.get("data").asText();
                    return new ResponseVO<>(code, msg, data);
                });
    }
}
