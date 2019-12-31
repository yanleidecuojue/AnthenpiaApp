package club.licona.anthenpiaapp.activity.fragment.detailedblog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tencent.mmkv.MMKV;

import java.util.LinkedList;
import java.util.List;

import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.DetailedBlogActivity;
import club.licona.anthenpiaapp.activity.adapter.CommentExpandAdapter;
import club.licona.anthenpiaapp.activity.view.CommentExpandableListView;
import club.licona.anthenpiaapp.bean.CommentDetailBean;
import club.licona.anthenpiaapp.bean.ReplyDetailBean;
import club.licona.anthenpiaapp.entity.vo.AddCommentVO;
import club.licona.anthenpiaapp.entity.vo.AddReplyVO;
import club.licona.anthenpiaapp.entity.vo.GetCommentDataVO;
import club.licona.anthenpiaapp.presenter.CommentPresenter;
import club.licona.anthenpiaapp.presenter.ReplyPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
public class GiveCommentFragment extends Fragment implements OnRefreshListener {
    private int bid;
    private int uid;
    private String token;

    private CommentPresenter commentPresenter;
    private ReplyPresenter replyPresenter;

    private CommentExpandableListView commentExpandableListView;
    private CommentExpandAdapter commentExpandAdapter;
    CommentDetailBean[] commentDetailBeans;
    private List<CommentDetailBean> commentDetailBeanList;
    private BottomSheetDialog bottomSheetDialog;

    private SwipeToLoadLayout swipeToLoadLayout;

    private View view;
    private TextView tvGiveComment;
    public GiveCommentFragment(int bid, int uid) {
        this.bid = bid;
        this.uid = uid;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_detailed_blog_give_comment, container, false);
        tvGiveComment = view.findViewById(R.id.tv_give_comment);
        MMKV mmkv = MMKV.defaultMMKV();
        token = mmkv.decodeString("token");
        commentExpandableListView = view.findViewById(R.id.comment_expandable_list_view);
        commentDetailBeanList = new LinkedList<>();
        initExpandableListView(commentDetailBeanList);

        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        }, 100);
        return view;
    }

    private void initExpandableListView(final List<CommentDetailBean> commentList) {

        commentExpandableListView.setGroupIndicator(null);
        commentExpandAdapter = new CommentExpandAdapter(getContext(), commentList);
        commentExpandableListView.setAdapter(commentExpandAdapter);
        for(int i = 0; i < commentDetailBeanList.size(); i++) {
            commentExpandableListView.expandGroup(i);
        }
        commentExpandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            showReplyCommentDialog(groupPosition);

            return true;
        });

        commentExpandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            showReplyReplyDialog(groupPosition, childPosition);
            return true;
        });

        commentExpandableListView.setOnGroupExpandListener(groupPosition -> {

        });
    }

    private void showReplyCommentDialog(final int position) {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        View dialogComment = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        final EditText etDialogComment = dialogComment.findViewById(R.id.et_dialog_comment);
        etDialogComment.setHint("回复" + commentDetailBeanList.get(position).getUsername() + "的评论");
        final Button btnDialogComment = dialogComment.findViewById(R.id.btn_dialog_comment);
        bottomSheetDialog.setContentView(dialogComment);

        btnDialogComment.setOnClickListener(v -> {
            int cid = commentDetailBeanList.get(position).getId();
            // 0表示回复的是评论
            int replyType = 0;
            String toUsername = commentDetailBeanList.get(position).getUsername();
            String replyContent = etDialogComment.getText().toString().trim();
            if(!TextUtils.isEmpty(replyContent)) {
                bottomSheetDialog.dismiss();
                replyPresenter.addReply(Observable.just(new AddReplyVO(token, cid, replyType, toUsername, replyContent)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseVO -> {
                            if(responseVO.getCode().equals(0)) {
                                ReplyDetailBean replyDetailBean = responseVO.getData();
                                commentExpandAdapter.addTheReplyData(replyDetailBean, position);
                                commentExpandableListView.expandGroup(position);
                            } else
                                Toast.makeText(getContext(), "对不起，回复评论暂时出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                        }, error -> Log.e("GiveCommentFragment#showReplyCommentDialog", error.getMessage(), error));
                Toast.makeText(getContext(), "回复评论成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "回复评论内容不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }

    private void showReplyReplyDialog(final int groupPosition, final int childPosition) {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        View dialogComment = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        final EditText etDialogComment = dialogComment.findViewById(R.id.et_dialog_comment);
        etDialogComment.setHint("回复" + commentDetailBeanList.get(groupPosition).getReplyDetailBeanList().get(childPosition).getFromUsername() + "的评论回复");
        final Button btnDialogComment = dialogComment.findViewById(R.id.btn_dialog_comment);
        bottomSheetDialog.setContentView(dialogComment);

        btnDialogComment.setOnClickListener(unit -> {
            int cid = commentDetailBeanList.get(groupPosition).getId();
            // 1表示回复的是回复回复
            int replyType = 1;
            String toUsername = commentDetailBeanList.get(groupPosition).getReplyDetailBeanList().get(childPosition).getToUsername();
            String replyContent = etDialogComment.getText().toString().trim();
            if(!TextUtils.isEmpty(replyContent)) {
                bottomSheetDialog.dismiss();
                replyPresenter.addReply(Observable.just(new AddReplyVO(token, cid, replyType, toUsername, replyContent)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseVO -> {
                            if(responseVO.getCode().equals(0)) {
                                ReplyDetailBean replyDetailBean = responseVO.getData();
                                commentExpandAdapter.addTheReplyData(replyDetailBean, groupPosition);
                                commentExpandableListView.expandGroup(groupPosition);
                            } else
                                Toast.makeText(getContext(), "对不起，回复评论回复暂时出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                        }, error -> Log.e("GiveCommentFragment#showReplyReplyDialog", error.getMessage(), error));
                Toast.makeText(getContext(), "回复评论回复成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "回复评论回复内容不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commentPresenter = new CommentPresenter();
        replyPresenter = new ReplyPresenter();

        giveComment();
    }

    private void getComment() {
        commentPresenter.getCommentData(Observable.just(new GetCommentDataVO(token, bid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponseVO -> {
                    if(listResponseVO.getCode().equals(0)) {
                        commentDetailBeans = listResponseVO.getData();
                        commentExpandAdapter.clear();
                        commentExpandableListView.clearChoices();

                        commentExpandAdapter.addAllCommentData(commentDetailBeans);
                        for(int i = 0; i < commentDetailBeans.length; i++) {
                            commentExpandableListView.expandGroup(i);
                        }
                    } else {
                        Toast.makeText(getContext(), "无法加载用户评论信息", Toast.LENGTH_LONG).show();
                    }
                }, error -> Log.e("GiveCommentFragment#getComment", error.getMessage(), error));

    }

    private void giveComment() {
        tvGiveComment.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(getContext());
            View dialogComment = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
            final EditText etDialogComment = dialogComment.findViewById(R.id.et_dialog_comment);
            final Button btnDialogComment = dialogComment.findViewById(R.id.btn_dialog_comment);
            bottomSheetDialog.setContentView(dialogComment);
            /**
             * 解决bsd显示不全的情况
             */
            View parent = (View) dialogComment.getParent();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
            dialogComment.measure(0, 0);
            bottomSheetBehavior.setPeekHeight(dialogComment.getMeasuredHeight());

            btnDialogComment.setOnClickListener(unit -> {
                String commentContent = etDialogComment.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)) {
                    bottomSheetDialog.dismiss();
                    commentPresenter.addComment(Observable.just(new AddCommentVO(token, bid, uid, commentContent)))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(responseVO -> {
                                if(responseVO.getCode().equals(0)) {
                                    CommentDetailBean commentDetailBean = responseVO.getData();
                                    commentExpandAdapter.addTheCommentData(commentDetailBean);
                                    DetailedBlogActivity detailedBlogActivity = (DetailedBlogActivity)getActivity();
                                    detailedBlogActivity.onResume();
                                } else
                                    Toast.makeText(getContext(), "对不起，评论暂时出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                            }, error -> Log.e("GiveCommentFragment#giveComment", error.getMessage(), error));
                    Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            bottomSheetDialog.show();
        });
    }

    @Override
    public void onRefresh() {
        getComment();
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 1000);
    }

}
