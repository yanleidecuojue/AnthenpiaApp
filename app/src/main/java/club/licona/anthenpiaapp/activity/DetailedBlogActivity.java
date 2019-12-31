package club.licona.anthenpiaapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.squareup.picasso.Picasso;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.fragment.detailedblog.GiveCommentFragment;
import club.licona.anthenpiaapp.activity.fragment.detailedblog.GiveLaudFragment;
import club.licona.anthenpiaapp.entity.vo.CancelLaudVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudStatusVO;
import club.licona.anthenpiaapp.entity.vo.GiveLaudVO;
import club.licona.anthenpiaapp.entity.vo.LaudInfoVO;
import club.licona.anthenpiaapp.presenter.CommentPresenter;
import club.licona.anthenpiaapp.presenter.LaudPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DetailedBlogActivity extends FragmentActivity {
    private int bid;
    private int uid;
    private String token;

    private LaudPresenter laudPresenter;
    private CommentPresenter commentPresenter;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    private ImageView ivBlogHeader;
    private TextView tvUsername;
    private TextView tvTime;
    private TextView tvText;
    private TextView tvContent;
    private TextView tvGiveLaud;
    private TextView tvLaudAmount;
    private TextView tvCommentAmount;

    private Unbinder unbinder;
    @BindView(R.id.items_blog)
    LinearLayout itemsBlog;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private CompositeDisposable compositeDisposable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_blog);

        this.unbinder = ButterKnife.bind(this);
        laudPresenter = new LaudPresenter();
        commentPresenter = new CommentPresenter();

        init();
    }

    public void onResume() {
        super.onResume();
        this.compositeDisposable = new CompositeDisposable();

        getLaudAndComment();
        subscribeGiveLaud();
    }

    private void init() {
        ivBlogHeader = itemsBlog.findViewById(R.id.iv_blog_header);
        tvUsername = itemsBlog.findViewById(R.id.tv_username);
        tvTime = itemsBlog.findViewById(R.id.tv_time);
        tvText = itemsBlog.findViewById(R.id.tv_text);
        tvContent = itemsBlog.findViewById(R.id.tv_content);
        tvGiveLaud = itemsBlog.findViewById(R.id.tv_give_laud);
        tvLaudAmount = itemsBlog.findViewById(R.id.tv_laud_amount);
        tvCommentAmount = itemsBlog.findViewById(R.id.tv_comment_amount);

        String headdir = getIntent().getStringExtra("headdir");
        String username = getIntent().getStringExtra("username");
        String time = getIntent().getStringExtra("time");
        String text = getIntent().getStringExtra("text");
        String content = getIntent().getStringExtra("content");

        // 图片地址，可通过url获取得到：http:///139.196.75.76:8080/anthenpia/headPortraits/ + fileName
        String picUrl = "http:///139.196.75.76:8080/anthenpia/headPortraits/" + headdir.trim();
        Picasso.with(this).load(picUrl).into(ivBlogHeader);

        tvUsername.setText(username);
        tvTime.setText(time);
        tvText.setText(text);
        tvContent.setText(content);
        bid = getIntent().getIntExtra("bid", 0);
        uid = getIntent().getIntExtra("uid", 0);
        MMKV mmkv = MMKV.defaultMMKV();
        token = mmkv.decodeString("token");


        titles.add("点赞");
        titles.add("评论");
        fragments.add(new GiveLaudFragment(bid, uid));
        fragments.add(new GiveCommentFragment(bid, uid));

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getLaudAndComment() {
        laudPresenter.getLaudStatus(Observable.just(new GetLaudStatusVO(token, bid, uid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if (responseVO.getCode() == 0) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        LaudInfoVO laudInfoVO = objectMapper.readValue(responseVO.getData().toString(), LaudInfoVO.class);
                        if (laudInfoVO.getStatus() == 1) {
                            tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_done_laud);
                        } else {
                            tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_laud);
                        }
                    } else {
                        Toast.makeText(this, "获取点赞状态失败", Toast.LENGTH_SHORT).show();
                    }
                }, error -> Log.e("DetailedBlogActivity#init", error.getMessage(), error));

        laudPresenter.getLaudCount(Observable.just(new GetLaudCountVO(token, bid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if (responseVO.getCode() == 0)
                        tvLaudAmount.setText(responseVO.getData().toString());
                    else Toast.makeText(this, "获取博客点赞数量失败", Toast.LENGTH_SHORT).show();
                }, error -> Log.e("DetailedBlogActivity#init", error.getMessage(), error));

        commentPresenter.getCommentCount(Observable.just(new GetCommentCountVO(token, bid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if (responseVO.getCode() == 0)
                        tvCommentAmount.setText(responseVO.getData().toString());
                    else Toast.makeText(this, "获取博客评论数量失败", Toast.LENGTH_SHORT).show();
                }, error -> Log.e("DetailedBlogActivity#init", error.getMessage(), error));
    }

    private void subscribeGiveLaud() {
        Disposable disposable = RxView.clicks(tvGiveLaud).subscribe(unit -> laudPresenter.getLaudStatus(Observable.just(new GetLaudStatusVO(token, bid, uid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if(responseVO.getCode() == 0) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        LaudInfoVO laudInfoVO = objectMapper.readValue(responseVO.getData().toString(), LaudInfoVO.class);
                        if(laudInfoVO.getStatus() == 1) {
                            tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_laud);
                            laudPresenter.cancelLaud(Observable.just(new CancelLaudVO(token, bid, uid)))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(InnerResponseVO -> {
                                        if(InnerResponseVO.getCode().equals(0)) {
                                            Toast.makeText(this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                            onResume();
                                        } else {
                                            Toast.makeText(this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_done_laud);
                            laudPresenter.giveLaud(Observable.just(new GiveLaudVO(token, bid, 1)))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(InnerResponseVO -> {
                                        if(InnerResponseVO.getCode().equals(0)) {
                                            Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();
                                            onResume();
                                        } else {
                                            Toast.makeText(this, "点赞失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "获取点赞状态失败", Toast.LENGTH_SHORT).show();
                    }
                },error -> Log.e("DetailedBlogActivity#subscribeGiveLaud", error.getMessage(), error)));
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