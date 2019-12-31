package club.licona.anthenpiaapp.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.OccupationInfoVO;

/**
 * @author licona
 *
 * 职业适配器
 */

public class OccupationAdapter extends RecyclerView.Adapter<OccupationAdapter.ViewHolder> {

    private List<OccupationInfoVO> occupationInfoVOList;
    private OnClickItemListener onClickItemListener;

    public OccupationAdapter() {
        this(new ArrayList<>());
    }

    public OccupationAdapter(List<OccupationInfoVO> occupationInfoVOList) {
        this.occupationInfoVOList = occupationInfoVOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_occupation, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(occupationInfoVOList.get(position));
    }

    @Override
    public int getItemCount() {
        return occupationInfoVOList.size();
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        this.onClickItemListener = listener;
    }

    /**
     * 刷新Adapter列表
     * 自动调用{@link OccupationAdapter#notifyDataSetChanged()}
     *
     * @param  occupationInfoVOList 新的列表
     */
    public void refreshList(List<OccupationInfoVO> occupationInfoVOList) {
        this.occupationInfoVOList = occupationInfoVOList;
        notifyDataSetChanged();
    }

    /**
     * 添加data 刷新列表
     * 自动调用{@link OccupationAdapter#notifyDataSetChanged()}
     *
     * @param occupationInfoVO 新的数据
     */
    public void addItem(OccupationInfoVO occupationInfoVO) {
        if (this.occupationInfoVOList == null) {
            occupationInfoVOList = new ArrayList<>();
        }
        this.occupationInfoVOList.add(occupationInfoVO);
        notifyDataSetChanged();
    }

    /**
     * Item点击回调接口
     */
    public interface OnClickItemListener {
        /**
         * Item被点击时触发回调
         *
         * @param occupationInfoVO 被点击的item所对应的vo
         */
        void onClick(OccupationInfoVO occupationInfoVO);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header)
        ImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        private View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        void bind(final OccupationInfoVO vo) {
            tvName.setText(vo.getName());
            tvDesc.setText(vo.getDesc());
            ivHeader.setImageBitmap(vo.getBitmap());
            view.setOnClickListener(v -> {
                if (onClickItemListener != null) {
                    onClickItemListener.onClick(vo);
                }
            });
        }
    }
}
