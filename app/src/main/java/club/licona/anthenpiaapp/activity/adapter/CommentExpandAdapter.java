package club.licona.anthenpiaapp.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.bean.CommentDetailBean;
import club.licona.anthenpiaapp.bean.ReplyDetailBean;

/**
 * @author licona
 */
public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private List<CommentDetailBean> commentDetailBeans;
    private Context context;

    public CommentExpandAdapter(Context context, List<CommentDetailBean> commentDetailBeans)               {
        this.context = context;
        this.commentDetailBeans = commentDetailBeans;
    }

    @Override
    public int getGroupCount() {
        return commentDetailBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentDetailBeans.get(i).getReplyDetailBeanList() == null){
            return 0;
        }else {
            return commentDetailBeans.get(i).getReplyDetailBeanList().size()>0 ? commentDetailBeans.get(i).getReplyDetailBeanList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentDetailBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentDetailBeans.get(i).getReplyDetailBeanList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_parent_comment, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        /**Glide.with(context).load("www.baidu.com")
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(groupHolder.ivParentCommentHead);**/
        groupHolder.ivParentCommentHead.setImageResource(R.drawable.bg_default);
        groupHolder.tvParentCommentUsername.setText(commentDetailBeans.get(groupPosition).getUsername());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        groupHolder.tvParentCommentTime.setText(simpleDateFormat.format(commentDetailBeans.get(groupPosition).getCreateDate()));
        groupHolder.tvParentCommentContent.setText(commentDetailBeans.get(groupPosition).getContent());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_son_reply,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = commentDetailBeans.get(groupPosition).getReplyDetailBeanList().get(childPosition).getFromUsername();

        if(!TextUtils.isEmpty(replyUser)){
            childHolder.tvSonReplyUsername.setText(replyUser + ":");
        }else {
            childHolder.tvSonReplyUsername.setText("无名"+":");
        }
        childHolder.tvSonReplyContent.setText(commentDetailBeans.get(groupPosition).getReplyDetailBeanList().get(childPosition).getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private ImageView ivParentCommentHead;
        private TextView tvParentCommentUsername;
        private TextView tvParentCommentTime;
        private TextView tvParentCommentContent;
        public GroupHolder(View view) {
            ivParentCommentHead =  view.findViewById(R.id.iv_parent_comment_head);
            tvParentCommentUsername = view.findViewById(R.id.tv_parent_comment_username);
            tvParentCommentTime = view.findViewById(R.id.tv_parent_comment_time);
            tvParentCommentContent = view.findViewById(R.id.tv_parent_comment_content);
        }
    }

    private class ChildHolder{
        private TextView tvSonReplyUsername;
        private TextView tvSonReplyContent;
        public ChildHolder(View view) {
            tvSonReplyUsername = view.findViewById(R.id.son_reply_username);
            tvSonReplyContent =  view.findViewById(R.id.son_reply_content);
        }
    }

    /**
     * 评论后插入一条新数据
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(CommentDetailBean commentDetailBean) {
        if(commentDetailBean != null) {
            commentDetailBeans.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空");
        }
    }

    /**
     * 插入所有评论数据
     * @param commentDetailBeans
     */
    public void addAllCommentData(CommentDetailBean[] commentDetailBeans) {
        if(commentDetailBeans != null) {
            this.commentDetailBeans.addAll(Arrays.asList(commentDetailBeans));
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空");
        }
    }


    /**
     * 回复成功后插入一条数据
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(ReplyDetailBean replyDetailBean, int groupPosition) {
        if(replyDetailBean != null) {
            if(commentDetailBeans.get(groupPosition).getReplyDetailBeanList() != null ){
                commentDetailBeans.get(groupPosition).getReplyDetailBeanList().add(replyDetailBean);
            }else {
                List<ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentDetailBeans.get(groupPosition).setReplyDetailBeanList(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空");
        }
    }

    public void clear() {
        commentDetailBeans.clear();
        notifyDataSetChanged();
    }
}
