package club.licona.anthenpiaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.UserLoginVO;
import club.licona.anthenpiaapp.presenter.UserPresenter;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @auther licona
 */
public class LoginActivity extends AppCompatActivity {

    private UserPresenter userPresenter;

    private Unbinder unbinder;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_find_psw)
    TextView tvFindPsw;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private CompositeDisposable compositeDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userPresenter = new UserPresenter();
        this.unbinder = ButterKnife.bind(this);

        tvTitle.setText("登录");
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeBtnLogin();
    }

    private Observable<UserLoginVO> getLoginObservable() {
        return RxView.clicks(btnLogin)
                .map(unit -> {
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    return new UserLoginVO(username, password);
                })
                .filter(userLoginVO -> {
                    if(StrUtil.isBlank(userLoginVO.getUsername())) {
                        tvResult.setText("用户名不能为空");
                        return false;
                    }
                    if(StrUtil.isBlank(userLoginVO.getPassword())) {
                        tvResult.setText("密码不能为空");
                        return false;
                    }
                    return true;
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    private void subscribeBtnLogin() {
        Disposable disposable = userPresenter.login(getLoginObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseVO -> {
                            if (responseVO.getCode().equals(0)) {
                                Toast.makeText(this, responseVO.getMsg(), Toast.LENGTH_SHORT).show();
                                tvResult.setText(responseVO.getData().toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", responseVO.getData().getUsername());
                                startActivity(intent);
                            } else {
                                tvResult.setText(responseVO.getMsg());
                            }
                        },
                        error -> {
                            tvResult.setText(error.getMessage());
                            Log.e("LoginActivity#subscribeBtnLogin", error.getMessage(), error);
                        }
                );
        compositeDisposable.add(disposable);
    }

    @OnClick(R.id.tv_register)
    void toRegisterView() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
