package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import club.licona.anthenpiaapp.entity.vo.GetImageLiteralVO;
import club.licona.anthenpiaapp.entity.vo.ImageLiteralInfoVO;
import club.licona.anthenpiaapp.entity.vo.ImageLiteralResponseVO;
import club.licona.anthenpiaapp.service.GetImageLiteralService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class GetImageLiteralPresenter {

    private GetImageLiteralService getImageLiteralService;

    public GetImageLiteralPresenter() {
        this.getImageLiteralService = new GetImageLiteralService();
    }

    /**
     * 获取图片文字操作
     *
     * @param getImageLiteralObservable 获取图片文字事件Observable
     * @return 获取图片文字操作结果Observable
     */
    public Observable<ImageLiteralResponseVO> getImageLiteral(Observable<GetImageLiteralVO> getImageLiteralObservable) {
        return getImageLiteralObservable.observeOn(Schedulers.io())
                .flatMap(getImageLiteralService::getImageLiteral)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    Long logId = head.get("log_id").asLong();
                    Long wordsResultNum = head.get("words_result_num").asLong();
                    ImageLiteralInfoVO[] imageLiteralInfoVOS = objectMapper.readValue(head.get("words_result").toString(), ImageLiteralInfoVO[].class);
                    List<ImageLiteralInfoVO> list = Arrays.stream(imageLiteralInfoVOS).collect(Collectors.toList());
                    return new ImageLiteralResponseVO(logId, wordsResultNum, list);
                });
    }
}