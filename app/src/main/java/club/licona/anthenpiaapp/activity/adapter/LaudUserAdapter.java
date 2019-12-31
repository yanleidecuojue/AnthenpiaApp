package club.licona.anthenpiaapp.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.LaudUserVO;

/**
 * @author licona
 *
 * 博客点赞用户信息适配器
 */

public class LaudUserAdapter extends RecyclerView.Adapter<LaudUserAdapter.ViewHolder> {

    private Context context;

    private List<LaudUserVO> laudUserVOList;

    public LaudUserAdapter(Context context) {
        laudUserVOList = new ArrayList<>();
        this.context = context;
    }


    public LaudUserAdapter(List<LaudUserVO> laudUserVOList) {
        this.laudUserVOList = laudUserVOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_laud_user, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(laudUserVOList.get(position));
    }

    @Override
    public int getItemCount() {
        return laudUserVOList.size();
    }

    /**
     * 刷新Adapter列表
     * 自动调用{@link LaudUserAdapter#notifyDataSetChanged()}
     *
     * @param  laudUserVOList 新的列表
     */
    public void refreshList(List<LaudUserVO> laudUserVOList) {
        this.laudUserVOList = laudUserVOList;
        notifyDataSetChanged();
    }

    /**
     * 添加data 刷新列表
     * 自动调用{@link LaudUserAdapter#notifyDataSetChanged()}
     *
     * @param laudUserVO 新的数据
     */
    public void addItem(LaudUserVO laudUserVO) {
        if (this.laudUserVOList == null) {
            laudUserVOList = new ArrayList<>();
        }
        this.laudUserVOList.add(laudUserVO);
        notifyDataSetChanged();
    }

    /**
     * Item点击回调接口
     */
    public interface OnClickItemListener {
        /**
         * Item被点击时触发回调
         *
         * @param laudUserVO 被点击的item所对应的vo
         */
        void onClick(LaudUserVO laudUserVO);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_laud_user_head)
        ImageView ivLaudUserHead;
        @BindView(R.id.tv_laud_user_username)
        TextView tvLaudUserUsername;
        @BindView(R.id.tv_laud_user_email)
        TextView tvLaudUserEmail;

        private View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        void bind(final LaudUserVO vo) {
            // 图片地址，可通过url获取得到：http://139.196.75.76:8080/anthenpia/headPortraits/ + fileName
            String picUrl = "http:///139.196.75.76:8080/anthenpia/headPortraits/" + vo.getHeaddir();
            Picasso.with(context).load(picUrl).into(ivLaudUserHead);

            tvLaudUserUsername.setText(vo.getUsername());
            tvLaudUserEmail.setText(vo.getEmail());
        }
    }
}
