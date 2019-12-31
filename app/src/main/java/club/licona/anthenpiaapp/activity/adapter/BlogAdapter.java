package club.licona.anthenpiaapp.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;
import com.tencent.mmkv.MMKV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.BlogInfoVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudCountVO;
import club.licona.anthenpiaapp.entity.vo.GetLaudStatusVO;
import club.licona.anthenpiaapp.entity.vo.LaudInfoVO;
import club.licona.anthenpiaapp.presenter.CommentPresenter;
import club.licona.anthenpiaapp.presenter.LaudPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author licona
 * 博客展示适配器
 */
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder>{
    private List<BlogInfoVO> blogInfoVOList;
    private OnClickGiveLaudListener onClickGiveLaudListener;
    private OnClickBlogListener onClickBlogListener;

    private LaudPresenter laudPresenter;
    private CommentPresenter commentPresenter;

    private Context context;

    public BlogAdapter(Context context) {
        this(new ArrayList<>());
        this.context = context;
    }

    public BlogAdapter(List<BlogInfoVO> blogInfoVOList) {
        this.blogInfoVOList = blogInfoVOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_blog, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(blogInfoVOList.get(position));
    }

    @Override
    public int getItemCount() {
        return blogInfoVOList.size();
    }

    public void setOnClickGiveLaudListener(OnClickGiveLaudListener listener) {
        this.onClickGiveLaudListener = listener;
    }

    public void setOnClickBlogListener(OnClickBlogListener listener) {
        this.onClickBlogListener = listener;
    }

    /**
     * 刷新Adapter列表
     * 自动调用{@link BlogAdapter#notifyDataSetChanged()}
     *
     * @param  blogInfoVOList 新的列表
     */
    public void refreshList(List<BlogInfoVO> blogInfoVOList) {
        this.blogInfoVOList = blogInfoVOList;
        notifyDataSetChanged();
    }

    /**
     * 添加data 刷新列表
     * 自动调用{@link BlogAdapter#notifyDataSetChanged()}
     *
     * @param blogInfoVO 新的数据
     */
    public void addItem(BlogInfoVO blogInfoVO) {
        if (this.blogInfoVOList == null) {
            blogInfoVOList = new ArrayList<>();
        }
        this.blogInfoVOList.add(blogInfoVO);
        notifyDataSetChanged();
    }


    /**
     * 点赞点击回调接口
     */
    public interface OnClickGiveLaudListener {
        /**
         * 点赞被点击时触发回调
         */
        void onClick(BlogInfoVO blogInfoVO, TextView tvGiveLaud);
    }

    /**
     * Blog点击回调接口
     */
    public interface OnClickBlogListener {
        /**
         * Blog被点击时触发回调
         *
         * @param blogInfoVO 被点击的blog所对应的vo
         */
        void onClick(BlogInfoVO blogInfoVO);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_blog_header)
        ImageView ivBlogHeader;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_give_laud)
        TextView tvGiveLaud;
        @BindView(R.id.tv_laud_amount)
        TextView tvLaudAmount;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_comment_amount)
        TextView tvCommentAmount;

        private View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        void bind(final BlogInfoVO vo) {
            // 图片地址，可通过url获取得到：http://139.196.75.76:8080/anthenpia/headPortraits/ + fileName
            String picUrl = "http:///139.196.75.76:8080/anthenpia/headPortraits/" + vo.getHeaddir();
            Picasso.with(context).load(picUrl).into(ivBlogHeader);

            tvUsername.setText(vo.getUsername());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tvTime.setText(simpleDateFormat.format(vo.getTime()));
            tvText.setText(vo.getText());
            tvContent.setText(vo.getContent());
            MMKV mmkv = MMKV.defaultMMKV();
            String token = mmkv.decodeString("token");
            int bid = vo.getId();
            int uid = vo.getUid();

            laudPresenter = new LaudPresenter();
            commentPresenter = new CommentPresenter();

            laudPresenter.getLaudStatus(Observable.just(new GetLaudStatusVO(token, bid, uid)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseVO -> {
                        if(responseVO.getCode() == 0) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            LaudInfoVO laudInfoVO = objectMapper.readValue(responseVO.getData().toString(), LaudInfoVO.class);
                            if(laudInfoVO.getStatus() == 1) {
                                tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_done_laud);
                            } else {
                                tvGiveLaud.setBackgroundResource(R.drawable.ic_blog_laud);
                            }
                        } else {
                            Toast.makeText(context, "获取点赞状态失败", Toast.LENGTH_SHORT).show();
                        }
                    },error -> Log.e("BlogAdapter#bind", error.getMessage(), error));

            laudPresenter.getLaudCount(Observable.just(new GetLaudCountVO(token, bid)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseVO -> {
                        if(responseVO.getCode() == 0)
                            tvLaudAmount.setText(responseVO.getData().toString());
                        else Toast.makeText(context, "获取博客点赞数量失败", Toast.LENGTH_SHORT).show();
                    },error -> Log.e("BlogAdapter#bind", error.getMessage(), error));

            commentPresenter.getCommentCount(Observable.just(new GetCommentCountVO(token, bid)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseVO -> {
                        if(responseVO.getCode() == 0)
                            tvCommentAmount.setText(responseVO.getData().toString());
                        else Toast.makeText(context, "获取博客评论数量失败", Toast.LENGTH_SHORT).show();
                    }, error -> Log.e("BlogAdapter#bind", error.getMessage(), error));

            view.setOnClickListener(v -> {
                if (onClickBlogListener != null) {
                    onClickBlogListener.onClick(vo);
                }
            });

            tvGiveLaud.setOnClickListener(v -> {
                if(onClickGiveLaudListener != null) {
                    onClickGiveLaudListener.onClick(vo, tvGiveLaud);
                }
            });
        }
    }
}
