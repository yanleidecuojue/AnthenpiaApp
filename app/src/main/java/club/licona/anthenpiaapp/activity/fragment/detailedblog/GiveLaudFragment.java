package club.licona.anthenpiaapp.activity.fragment.detailedblog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.List;

import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.adapter.LaudUserAdapter;
import club.licona.anthenpiaapp.entity.vo.GetLaudUserVO;
import club.licona.anthenpiaapp.entity.vo.LaudUserVO;
import club.licona.anthenpiaapp.presenter.LaudPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GiveLaudFragment extends Fragment {
    private int bid;
    private int uid;
    private String token;

    private LaudPresenter laudPresenter;

    private LaudUserAdapter laudUserAdapter;
    RecyclerView recyclerLaudView;

    private View view;

    public GiveLaudFragment(int bid, int uid) {
        this.bid = bid;
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_detailed_blog_give_laud, container, false);
        MMKV mmkv = MMKV.defaultMMKV();
        token = mmkv.decodeString("token");
        recyclerLaudView = view.findViewById(R.id.detailed_blog_give_laud_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerLaudView.setLayoutManager(layoutManager);
        laudUserAdapter = new LaudUserAdapter(getContext());
        recyclerLaudView.setAdapter(laudUserAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        laudPresenter = new LaudPresenter();
        listLaudUser();
    }

    private void listLaudUser() {
        laudPresenter.getLaudUser(Observable.just(new GetLaudUserVO(token, bid)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseVO -> {
                    if(responseVO.getCode().equals(0)) {
                        List<LaudUserVO> laudUserVOList = responseVO.getData();
                        Collections.reverse(laudUserVOList);
                        laudUserAdapter.refreshList(laudUserVOList);
                    } else {
                        Toast.makeText(getContext(), "无法加载博客点赞用户信息", Toast.LENGTH_LONG).show();
                    }
                }, error -> Log.e("GiveLaudFragment#listLaudUser", error.getMessage(), error));
    }
}
