package club.licona.anthenpiaapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SettingsActivity extends Activity {
    private Unbinder unbinder;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvTitle;

    private CompositeDisposable compositeDisposable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        titleBar.setBackgroundColor(getResources().getColor(R.color.palegoldenrod));
        tvBack = titleBar.findViewById(R.id.tv_back);
        tvTitle = titleBar.findViewById(R.id.tv_title);
        tvTitle.setText("设置");
    }

    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();

        subscribeLogout();
    }

    private void subscribeLogout() {
        Disposable disposableLogout = RxView.clicks(llLogout).subscribe(unit -> {
            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
        });

        compositeDisposable.add(disposableLogout);
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
