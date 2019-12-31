package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.mmkv.MMKV;

import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.entity.vo.UserInfoVO;
import club.licona.anthenpiaapp.entity.vo.UserLoginVO;
import club.licona.anthenpiaapp.entity.vo.UserRegisterVO;
import club.licona.anthenpiaapp.service.UserService;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class UserPresenter {

    private UserService userService;

    public UserPresenter() {
        this.userService = new UserService();
    }

    /**
     * 登录操作
     *
     * @param loginObservable 登录事件Observable
     * @return 登录操作结果Observable
     */
    public Observable<ResponseVO<UserInfoVO>> login(Observable<UserLoginVO> loginObservable) {
        return loginObservable.observeOn(Schedulers.io())
                .flatMap(userService::login)
                .observeOn(Schedulers.computation())
                .map((Function<String, ResponseVO<UserInfoVO>>) resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        UserInfoVO vo = objectMapper.readValue(head.get("data").toString(), UserInfoVO.class);
                        return new ResponseVO<>(code, msg, vo);
                    } else {
                        return new ResponseVO<>(code, msg);
                    }
                })
                .doOnNext(responseVo -> {
                    if (responseVo.getCode() == 0) {
                        String username = responseVo.getData().getUsername();
                        String token = responseVo.getData().getToken();
                        int userId = responseVo.getData().getId();
                        String nickname = responseVo.getData().getNickname();
                        String email = responseVo.getData().getEmail();

                        MMKV mmkv = MMKV.defaultMMKV();
                        mmkv.encode("username", username);
                        mmkv.encode("token", token);
                        mmkv.encode("userId", userId);
                        mmkv.encode("nickname", nickname);
                        mmkv.encode("email", email);
                    }
                });
    }

    /**
     * 注册操作
     *
     * @param registerObservable 注册事件Observable
     * @return 注册操作结果Observable
     */
    public Observable<ResponseVO<UserInfoVO>> register(Observable<UserRegisterVO> registerObservable) {
        return registerObservable.observeOn(Schedulers.io())
                .flatMap(userService::register)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        UserInfoVO vo = objectMapper.readValue(head.get("data").toString(), UserInfoVO.class);
                        return new ResponseVO<>(code, msg, vo);
                    } else {
                        return new ResponseVO<>(code, msg);
                    }
                });
    }

}
