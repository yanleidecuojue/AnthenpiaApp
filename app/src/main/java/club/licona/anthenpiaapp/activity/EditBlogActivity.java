package club.licona.anthenpiaapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding3.view.RxView;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.BlogCreateVO;
import club.licona.anthenpiaapp.presenter.BlogPresenter;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class EditBlogActivity extends Activity {
    private BlogPresenter blogPresenter;

    private Unbinder unbinder;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_create_blog)
    Button btnCreateBlog;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private CompositeDisposable compositeDisposable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);

        this.blogPresenter = new BlogPresenter();
        this.unbinder = ButterKnife.bind(this);
    }

    protected void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeCreateBlog();
    }

    private Observable<BlogCreateVO> getCreateBlogObservable() {
        return RxView.clicks(btnCreateBlog)
                .map(unit -> {
                    String text = etText.getText().toString();
                    String content = etContent.getText().toString();
                    MMKV mmkv = MMKV.defaultMMKV();
                    return new BlogCreateVO(mmkv.decodeString("token"), text, content);
                })
                .filter(blogCreateVO -> {
                    if(StrUtil.isEmpty(blogCreateVO.getText())) {
                        Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(StrUtil.isEmpty(blogCreateVO.getContent())) {
                        Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    return true;
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    private void subscribeCreateBlog() {
        Disposable disposable = blogPresenter.createBlog(getCreateBlogObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseVO -> {
                            if(responseVO.getCode().equals(0)) {
                                Toast.makeText(this, responseVO.getMsg(), Toast.LENGTH_SHORT).show();
                                tvResult.setText(responseVO.toString());
                                Intent intent = new Intent(EditBlogActivity.this, MainActivity.class);
                                intent.putExtra("selectPage", "我的");
                                startActivity(intent);
                            } else {
                                tvResult.setText(responseVO.toString());
                            }
                        },
                        error -> {
                            tvResult.setText(error.getMessage());
                            Log.e("EditBlogActivity#subscribeCreateBlog", error.getMessage(), error);
                        }
                );
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
