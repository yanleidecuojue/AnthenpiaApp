package club.licona.anthenpiaapp.activity.language;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class JavaActivity extends Activity {
    private Unbinder unbinder;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.java_web_view)
    WebView javaWebView;

    private TextView tvBack;
    private TextView tvTitle;

    private CompositeDisposable compositeDisposable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_backend_java);

        this.unbinder = ButterKnife.bind(this);

        init();

        webViewShow();
    }

    private void init() {
        rlBar.setBackgroundColor(getColor(R.color.slightblue));
        tvBack = rlBar.findViewById(R.id.tv_back);
        tvTitle = rlBar.findViewById(R.id.tv_title);
        tvTitle.setText("Java技术文档");
    }

    private void webViewShow() {
        javaWebView.loadUrl("http://139.196.75.76:8080/anthenpia/knowledgeGraph/java.jsp");
        javaWebView.getSettings().setJavaScriptEnabled(true);
        javaWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeRlBar();
    }

    private void subscribeRlBar() {
        Disposable disposable = RxView.clicks(tvBack).subscribe(unit -> this.finish());
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
