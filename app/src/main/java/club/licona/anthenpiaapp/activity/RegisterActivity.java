package club.licona.anthenpiaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.UserRegisterVO;
import club.licona.anthenpiaapp.presenter.UserPresenter;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author licona
 */
public class RegisterActivity extends AppCompatActivity {

    private UserPresenter userPresenter;

    private Unbinder unbinder;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private CompositeDisposable compositeDisposable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.userPresenter = new UserPresenter();
        this.unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeBtnRegister();
    }

    private Observable<UserRegisterVO> getRegisterObservable() {
        return RxView.clicks(btnRegister)
                .map(unit -> {
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String nickname = etNickname.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    return new UserRegisterVO(username, password, nickname, email);
                })
                .filter(userRegisterVO -> {
                    if(StrUtil.isEmpty(userRegisterVO.getUsername())) {
                        Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT);
                        return false;
                    }
                    if(StrUtil.isEmpty(userRegisterVO.getPassword())) {
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT);
                        return false;
                    }
                    String passwordAgain = etPasswordAgain.getText().toString();
                    if(userRegisterVO.getPassword().equals(passwordAgain))
                        return true;
                    else return false;
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    private void subscribeBtnRegister() {
        Disposable disposable = userPresenter.register(getRegisterObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseVo -> {
                            if (responseVo.getCode().equals(0)) {
                                Toast.makeText(this, responseVo.getMsg(), Toast.LENGTH_SHORT).show();
                                tvResult.setText(responseVo.getData().toString());
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                tvResult.setText(responseVo.getMsg());
                            }
                        },
                        error -> {
                            tvResult.setText(error.getMessage());
                            Log.e("RegisterActivity#subscribeBtnRegister", error.getMessage(), error);
                        }
                );
        compositeDisposable.add(disposable);
    }

    @OnClick(R.id.tv_back)
    void back() {
        finish();
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
