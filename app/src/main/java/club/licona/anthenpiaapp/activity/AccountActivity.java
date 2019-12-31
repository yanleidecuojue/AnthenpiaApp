package club.licona.anthenpiaapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AccountActivity extends Activity {
    private Unbinder unbinder;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvTitle;

    private CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        MMKV mmkv = MMKV.defaultMMKV();
        tvUsername.setText( mmkv.decodeString("username"));
        tvNickname.setText(mmkv.decodeString("nickname"));
        tvEmail.setText(mmkv.decodeString("email"));

        titleBar = findViewById(R.id.title_bar);
        tvBack = titleBar.findViewById(R.id.tv_back);
        tvTitle = titleBar.findViewById(R.id.tv_title);
        tvTitle.setText("我的资料");
    }

    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeBackToMy();
    }

    private void subscribeBackToMy() {
        Disposable disposable = RxView.clicks(tvBack).subscribe(unit -> {
            this.finish();
        });
        compositeDisposable.add(disposable);
    }

    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
