package club.licona.anthenpiaapp.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.adapter.OccupationAdapter;
import club.licona.anthenpiaapp.activity.language.JavaActivity;
import club.licona.anthenpiaapp.entity.vo.OccupationInfoVO;
import club.licona.anthenpiaapp.util.GetJson;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author licona
 */
public class ItemsActivity extends AppCompatActivity {
    private OccupationAdapter adapter;

    private Unbinder unbinder;

    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        this.unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.compositeDisposable = new CompositeDisposable();

    }

    private void init() {
        rlBar.setBackgroundColor(getColor(R.color.slightblue));
        tvBack.setVisibility(View.GONE);
        tvTitle.setText(getIntent().getStringExtra("title"));
        initItemsRecyclerView();
        listItems();
    }

    private void initItemsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OccupationAdapter();
        adapter.setOnClickItemListener(occupationInfoVO -> {
            if(occupationInfoVO.getName().equals("Java")) {
                startActivity(new Intent(ItemsActivity.this, JavaActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void listItems() {
        String jsonFileName = getIntent().getStringExtra("jsonFileName");
        String route = getIntent().getStringExtra("route");

        try {
            JSONObject jsonObject = new JSONObject(GetJson.
                    getJson(route + jsonFileName + ".json",this));
            JSONArray jsonArray = jsonObject.getJSONArray(jsonFileName);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.optString("name", null);
                String desc = object.optString("desc", null);
                String header = object.optString("header", null);

                Bitmap bitmap = null;
                AssetManager assetManager = this.getAssets();
                try {
                    InputStream inputStream = assetManager.open(route + header);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.addItem(new OccupationInfoVO(name, desc, bitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
