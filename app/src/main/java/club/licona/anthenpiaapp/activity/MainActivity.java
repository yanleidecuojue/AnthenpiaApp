package club.licona.anthenpiaapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.view.BlogFragment;
import club.licona.anthenpiaapp.activity.view.MyFragment;
import club.licona.anthenpiaapp.activity.view.OccupationFragment;
import club.licona.anthenpiaapp.activity.view.ToolsFragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author licona
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 视图
     */
    private Fragment occupationFragment;
    private Fragment toolsFragment;
    private Fragment blogFragment;
    private Fragment myFragment;

    private Unbinder unbinder;

    /**
     * 顶部栏
     */
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    /**
     * 中间内容栏
     */
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    /**
     * 底部按钮栏
     */
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.iv_occupation)
    ImageView ivOccupation;
    @BindView(R.id.iv_tools)
    ImageView ivTools;
    @BindView(R.id.iv_blog)
    ImageView ivBlog;
    @BindView(R.id.iv_my)
    ImageView ivMy;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        rlBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.compositeDisposable = new CompositeDisposable();
        setFragment(3);
        subscribeSwitchFragment();
    }

    private void subscribeSwitchFragment () {

        Disposable disposableOccupation = RxView.clicks(ivOccupation).subscribe(unit -> {
            setFragment(0);
        });

        Disposable disposableTools = RxView.clicks(ivTools).subscribe(unit -> {
            setFragment(1);
        });

        Disposable disposableBlog = RxView.clicks(ivBlog).subscribe(unit -> {
            setFragment(2);
        });

        Disposable disposableMy = RxView.clicks(ivMy).subscribe(unit -> {
            setFragment(3);
        });

        compositeDisposable.add(disposableOccupation);
        compositeDisposable.add(disposableTools);
        compositeDisposable.add(disposableBlog);
        compositeDisposable.add(disposableMy);
    }

    private void setFragment(int index) {
        /**
         * 获取Fragment管理器
         */
        FragmentManager fragmentManager = getSupportFragmentManager();
        /**
         * 开启事务
         */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /**
         * 隐藏所有Fragment
         */
        hideFragments(fragmentTransaction);

        switch (index) {
            default:
                break;
            case 0:
                ivOccupation.setImageResource(R.drawable.ic_main_occupation_selected);
                if(occupationFragment == null) {
                    occupationFragment = new OccupationFragment();
                    fragmentTransaction.add(R.id.fl_container, occupationFragment, "occupationFragment");
                } else {
                    fragmentTransaction.show(occupationFragment);
                }
                break;
            case 1:
                ivTools.setImageResource(R.drawable.ic_main_tools_selected);
                if(toolsFragment == null) {
                    toolsFragment = new ToolsFragment();
                    fragmentTransaction.add(R.id.fl_container, toolsFragment, "toolsFragment");
                } else {
                    fragmentTransaction.show(toolsFragment);
                }
                break;
            case 2:
                ivBlog.setImageResource(R.drawable.ic_main_blog_selected);
                if(blogFragment == null) {
                    blogFragment = new BlogFragment();
                    fragmentTransaction.add(R.id.fl_container, blogFragment, "blogFragment");
                } else {
                    fragmentTransaction.show(blogFragment);
                }
                break;
            case 3:
                ivMy.setImageResource(R.drawable.ic_main_my_selected);
                if(myFragment == null) {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.fl_container, myFragment, "myFragment");
                } else {
                    fragmentTransaction.show(myFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {

        if(occupationFragment != null) {
            fragmentTransaction.hide(occupationFragment);
            ivOccupation.setImageResource(R.drawable.ic_main_occupation);
        }

        if(toolsFragment != null) {
            fragmentTransaction.hide(toolsFragment);
            ivTools.setImageResource(R.drawable.ic_main_tools);
        }

        if(blogFragment != null) {
            fragmentTransaction.hide(blogFragment);
            ivBlog.setImageResource(R.drawable.ic_main_blog);
        }

        if(myFragment != null) {
            fragmentTransaction.hide(myFragment);
            ivMy.setImageResource(R.drawable.ic_main_my);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
