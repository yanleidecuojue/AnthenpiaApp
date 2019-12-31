package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.UserLoginVO;
import club.licona.anthenpiaapp.entity.vo.UserRegisterVO;
import club.licona.anthenpiaapp.service.api.LoginApi;
import club.licona.anthenpiaapp.service.api.RegisterApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author licona
 */
public class UserService {
    /**
     * 登陆
     * <p>
     * 调用服务器登陆接口
     *
     * @param userLoginVO 登录VO
     * @return 登陆操作返回json结果
     */
    public Observable<String> login(UserLoginVO userLoginVO) {
        return RetrofitProvider.get().create(LoginApi.class)
                .login(userLoginVO.getUsername(), userLoginVO.getPassword())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 注册
     * <p>
     * 调用服务器注册接口
     *
     * @param userRegisterVO 注册VO
     * @return 注册操作返回json结果
     */
    public Observable<String> register(UserRegisterVO userRegisterVO) {
        return RetrofitProvider.get().create(RegisterApi.class)
                .register(userRegisterVO.getUsername(), userRegisterVO.getPassword(),
                        userRegisterVO.getNickname(), userRegisterVO.getEmail())
                .subscribeOn(Schedulers.io());
    }


}
