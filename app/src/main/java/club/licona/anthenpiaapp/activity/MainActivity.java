package club.licona.anthenpiaapp.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.rxbinding3.view.RxView;
import com.squareup.picasso.Picasso;
import com.tencent.mmkv.MMKV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.adapter.BlogAdapter;
import club.licona.anthenpiaapp.activity.adapter.PageAdapter;
import club.licona.anthenpiaapp.entity.vo.BlogInfoVO;
import club.licona.anthenpiaapp.entity.vo.BlogListAllVO;
import club.licona.anthenpiaapp.entity.vo.CancelLaudVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudStatusVO;
import club.licona.anthenpiaapp.entity.vo.GiveLaudVO;
import club.licona.anthenpiaapp.entity.vo.HeadPortraitGetVO;
import club.licona.anthenpiaapp.entity.vo.LaudInfoVO;
import club.licona.anthenpiaapp.presenter.BlogPresenter;
import club.licona.anthenpiaapp.presenter.HeadPortraitPresenter;
import club.licona.anthenpiaapp.presenter.LaudPresenter;
import club.licona.widget.banner.YBannerView;
import club.licona.widget.banner.indicator.CircleIndicator;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author licona
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<View> pageList = new ArrayList<>();
    View viewOccupation;
    View viewTools;
    View viewBlog;
    View viewMy;

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
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    /**
     * 职业
     */
    LinearLayout llTechnology;
    private YBannerView bannerView;

    /**
     * 工具
     */
    LinearLayout llToolsOCR;
    /**
     * 博客
     */
    private BlogAdapter adapter;
    RecyclerView recyclerView;
    private BlogPresenter blogPresenter;
    private LaudPresenter laudPresenter;
    /**
     * 我的
     */
    ImageView ivMyHeader;
    TextView tvLogin;
    LinearLayout llMyBlog;
    ConstraintLayout clSetting;
    private HeadPortraitPresenter headPortraitPresenter;
    /**
     * 底部按钮栏
     */
    @BindView(R.id.ll_occupation)
    LinearLayout llOccupation;
    @BindView(R.id.ll_tools)
    LinearLayout llTools;
    @BindView(R.id.ll_blog)
    LinearLayout llBlog;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    @BindView(R.id.iv_occupation)
    ImageView ivOccupation;
    @BindView(R.id.iv_tools)
    ImageView ivTools;
    @BindView(R.id.iv_blog)
    ImageView ivBlog;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    @BindView(R.id.tv_occupation)
    TextView tvOccupation;
    @BindView(R.id.tv_tools)
    TextView tvTools;
    @BindView(R.id.tv_blog)
    TextView tvBlog;
    @BindView(R.id.tv_my)
    TextView tvMy;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.blogPresenter = new BlogPresenter();
        this.laudPresenter = new LaudPresenter();
        this.headPortraitPresenter = new HeadPortraitPresenter();
        this.unbinder = ButterKnife.bind(this);

        init();

        initBlogRecyclerView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.compositeDisposable = new CompositeDisposable();
        subscribeSwitchPage();
        subscribeOccupation();
        subscribeTools();
        subscribeBlog();
        subscribeMy();
    }

    private void init() {
        rlBar.setBackgroundColor(getColor(R.color.slightblue));
        tvBack.setVisibility(View.GONE);
        tvTitle.setText("首 页");

        LayoutInflater inflater = LayoutInflater.from(this);
        viewOccupation = inflater.inflate(R.layout.activity_main_occupation, null);
        viewTools = inflater.inflate(R.layout.activity_main_tools, null);
        viewBlog = inflater.inflate(R.layout.activity_main_blog, null);
        viewMy = inflater.inflate(R.layout.activity_main_my, null);
        pageList.add(viewOccupation);
        pageList.add(viewTools);
        pageList.add(viewBlog);
        pageList.add(viewMy);
        viewPager.setAdapter(new PageAdapter(pageList));
        viewPager.addOnPageChangeListener(this);
        setLayoutColor(llOccupation);

        /**
         * 职业
         */
        llTechnology = viewOccupation.findViewById(R.id.ll_technology);
        bannerView = viewOccupation.findViewById(R.id.banner_view);
        List<String> bannerData = new ArrayList<>();
        bannerData.add(drawableToUri(R.drawable.occupation_test1));
        bannerData.add(drawableToUri(R.drawable.occupation_test2));
        bannerData.add(drawableToUri(R.drawable.occupation_test3));
        bannerView.setBannerData(bannerData);
        bannerView.setIndicator(new CircleIndicator(viewOccupation.getContext()));


        /**
         * 博客
         */
        recyclerView = viewBlog.findViewById(R.id.recycler);

        /**
         * 工具
         */
        llToolsOCR = viewTools.findViewById(R.id.ll_tools_ocr);

        /**
         * 我的
         */
        ivMyHeader = viewMy.findViewById(R.id.iv_my_header);
        clSetting = viewMy.findViewById(R.id.cl_setting);
        MMKV mmkv = MMKV.defaultMMKV();
        tvLogin = viewMy.findViewById(R.id.tv_login);
        llMyBlog = viewMy.findViewById(R.id.ll_blog);
        if(mmkv.decodeBool("isLogin")) {
            tvLogin.setText(mmkv.decodeString("username"));
        }

        if(getIntent().getStringExtra("selectPage")!= null && getIntent().getStringExtra("selectPage").equals("我的")) {
            viewPager.setCurrentItem(3);
            setLayoutColor(llMy);
        }
    }

    private String drawableToUri(int resId) {
        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));
        return uri.toString();
    }

    private void initBlogRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BlogAdapter(this);
        adapter.setOnClickBlogListener(blogInfoVO -> {
            Intent intent = new Intent(MainActivity.this, DetailedBlogActivity.class);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            intent.putExtra("bid", blogInfoVO.getId());
            intent.putExtra("uid", blogInfoVO.getUid());
            intent.putExtra("time", simpleDateFormat.format(blogInfoVO.getTime()));
            intent.putExtra("text", blogInfoVO.getText());
            intent.putExtra("content", blogInfoVO.getContent());
            intent.putExtra("username", blogInfoVO.getUsername());
            intent.putExtra("headdir", blogInfoVO.getHeaddir());
            startActivity(intent);
        });

        adapter.setOnClickGiveLaudListener((blogInfoVO, tvGiveLaud) -> {
            MMKV mmkv = MMKV.defaultMMKV();
            String token = mmkv.decodeString("token");

            laudPresenter.getLaudStatus(Observable.just(new GetLaudStatusVO(token, blogInfoVO.getId(), blogInfoVO.getUid())))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseVO -> {
                        if(responseVO.getCode() == 0) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            LaudInfoVO laudInfoVO = objectMapper.readValue(responseVO.getData().toString(), LaudInfoVO.class);
                            if(laudInfoVO.getStatus() == 1) {
                                tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_laud);
                                laudPresenter.cancelLaud(Observable.just(new CancelLaudVO(token, blogInfoVO.getId(), blogInfoVO.getUid())))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(InnerResponseVO -> {
                                            if(InnerResponseVO.getCode().equals(0)) {
                                                Toast.makeText(this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                                onResume();
                                            } else {
                                                Toast.makeText(this, "取消点赞失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_done_laud);
                                laudPresenter.giveLaud(Observable.just(new GiveLaudVO(token, blogInfoVO.getId(), 1)))
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
                    },error -> Log.e("BlogAdapter#bind", error.getMessage(), error));
        });
        recyclerView.setAdapter(adapter);
    }

    private void subscribeSwitchPage() {
        Disposable disposableOccupation = RxView.clicks(llOccupation).subscribe(unit -> {
            setLayoutColor(llOccupation);
            viewPager.setCurrentItem(0);
        });
        Disposable disposableTools = RxView.clicks(llTools).subscribe(unit -> {
            setLayoutColor(llTools);
            viewPager.setCurrentItem(1);
        });
        Disposable disposableBlog = RxView.clicks(llBlog).subscribe(unit -> {
            setLayoutColor(llBlog);
            viewPager.setCurrentItem(2);
        });
        Disposable disposableMy = RxView.clicks(llMy).subscribe(unit -> {
            setLayoutColor(llMy);
            viewPager.setCurrentItem(3);
        });

        compositeDisposable.add(disposableOccupation);
        compositeDisposable.add(disposableTools);
        compositeDisposable.add(disposableBlog);
        compositeDisposable.add(disposableMy);
    }

    /**
     * 设置切换界面时底部颜色的变化
     */
    private void setLayoutColor(LinearLayout currentLayout) {
        switch (currentLayout.getId()) {
            case R.id.ll_occupation:
                tvTitle.setText("首页");
                ivOccupation.setImageResource(R.drawable.ic_main_occupation_selected);
                tvOccupation.setTextColor(ContextCompat.getColor(this, R.color.slightblue));
                ivTools.setImageResource(R.drawable.ic_main_tools);
                tvTools.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivBlog.setImageResource(R.drawable.ic_main_blog);
                tvBlog.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivMy.setImageResource(R.drawable.ic_main_my);
                tvMy.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                break;
            case R.id.ll_tools:
                tvTitle.setText("工具");
                ivOccupation.setImageResource(R.drawable.ic_main_occupation);
                tvOccupation.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivTools.setImageResource(R.drawable.ic_main_tools_selected);
                tvTools.setTextColor(ContextCompat.getColor(this, R.color.slightblue));
                ivBlog.setImageResource(R.drawable.ic_main_blog);
                tvBlog.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivMy.setImageResource(R.drawable.ic_main_my);
                tvMy.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                break;
            case R.id.ll_blog:
                tvTitle.setText("博客");
                ivOccupation.setImageResource(R.drawable.ic_main_occupation);
                tvOccupation.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivTools.setImageResource(R.drawable.ic_main_tools);
                tvTools.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivBlog.setImageResource(R.drawable.ic_main_blog_selected);
                tvBlog.setTextColor(ContextCompat.getColor(this, R.color.slightblue));
                ivMy.setImageResource(R.drawable.ic_main_my);
                tvMy.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                break;
            case R.id.ll_my:
                tvTitle.setText("我的");
                ivOccupation.setImageResource(R.drawable.ic_main_occupation);
                tvOccupation.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivTools.setImageResource(R.drawable.ic_main_tools);
                tvTools.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivBlog.setImageResource(R.drawable.ic_main_blog);
                tvBlog.setTextColor(ContextCompat.getColor(this, R.color.slightgray));
                ivMy.setImageResource(R.drawable.ic_main_my_selected);
                tvMy.setTextColor(ContextCompat.getColor(this, R.color.slightblue));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int pageId) {
        switch (pageId) {
            case 0:
                setLayoutColor(llOccupation);
                break;
            case 1:
                setLayoutColor(llTools);
                break;
            case 2:
                setLayoutColor(llBlog);
                break;
            case 3:
                setLayoutColor(llMy);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 以下属于首页部分
     */
    private void subscribeOccupation() {
        Disposable disposableTechnology = RxView.clicks(llTechnology).subscribe(unit -> {
            startActivity(new Intent(MainActivity.this, TechnologyActivity.class));
        });
        compositeDisposable.add(disposableTechnology);
    }

    /**
     * 以下属于工具部分
     */
    private void subscribeTools() {
        Disposable disposableOCR = RxView.clicks(llToolsOCR).subscribe(unit ->
                startActivity(new Intent(MainActivity.this, ToolsOCRActivity.class)));
        compositeDisposable.add(disposableOCR);
    }

    /**
     * 以下内容属于博客部分
     */
    /**
     * @return Observable 列出所有博客
     */
    private Observable<BlogListAllVO> getListAllBlog() {
        MMKV mmkv = MMKV.defaultMMKV();
        String token = mmkv.decodeString("token");
        return Observable.just(new BlogListAllVO(token));
    }

    private void subscribeBlog() {
        /**
         * 订阅列出用户所有博客请求操作
         */
        Disposable disposableListAllBlog = blogPresenter.listAllBlog(getListAllBlog())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if (responseVO.getCode().equals(0)) {
                        List<BlogInfoVO> blogInfoVOList = responseVO.getData();
                        Collections.reverse(blogInfoVOList);
                        adapter.refreshList(blogInfoVOList);
                    } else {
                        Toast.makeText(this, "出错了", Toast.LENGTH_LONG);
                    }

                }, error -> Log.e("MainActivity#subscribeListAllBlog", error.getMessage(), error));

        compositeDisposable.add(disposableListAllBlog);
    }
    /**
     * 以下内容属于我的部分
     */
    /**
     * @return Observable 列出所有博客
     */
    private Observable<HeadPortraitGetVO> getHeadPortrait() {
        MMKV mmkv = MMKV.defaultMMKV();
        String token = mmkv.decodeString("token");
        return Observable.just(new HeadPortraitGetVO(token));
    }
    private void subscribeMy() {
        Disposable disposableShowHeadPortrait = headPortraitPresenter.getHeadPortrait(getHeadPortrait())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if(responseVO.getCode() == 0) {
                        String fileName = responseVO.getData().toString();
                        // 图片地址，可通过url获取得到：http://139.196.75.76:8080/anthenpia/headPortraits/ + fileName
                        String picUrl = "http:///139.196.75.76:8080/anthenpia/headPortraits/" + fileName.trim();
                        System.out.println(picUrl);
                        Picasso.with(this).load(picUrl).into(ivMyHeader);
                   } else {
                        Toast.makeText(this, "获取头像失败", Toast.LENGTH_SHORT);
                    }
                }, error -> {
                    Log.e("MainActivity#subscribeMy", error.getMessage(), error);
                });

        Disposable disposableLogin = RxView.clicks(tvLogin).subscribe(unit -> {
            MMKV mmkv = MMKV.defaultMMKV();
            if(mmkv.decodeBool("isLogin")) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Disposable disposableHeader = RxView.clicks(ivMyHeader).subscribe(unit -> {
            startActivity(new Intent(MainActivity.this, HeadPortraitActivity.class));
        });

        Disposable disposableEditBlog = RxView.clicks(llMyBlog).subscribe(unit -> {
            startActivity(new Intent(MainActivity.this, EditBlogActivity.class));
        });

        Disposable disposableSettings = RxView.clicks(clSetting).subscribe(unit -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        compositeDisposable.add(disposableShowHeadPortrait);
        compositeDisposable.add(disposableLogin);
        compositeDisposable.add(disposableHeader);
        compositeDisposable.add(disposableEditBlog);
        compositeDisposable.add(disposableSettings);
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
