package club.licona.anthenpiaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;

/**
 * @author licona
 */
public class TechnologyActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_technology);
        this.unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        rlBar.setBackgroundColor(getColor(R.color.slightblue));
        tvBack.setVisibility(View.GONE);
        tvTitle.setText("技 术");
    }

    @OnClick(R.id.ll_backend_development)
    void toBackendDevelopmentView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "后端");
        intent.putExtra("route", "technology/backendDevelopment/");
        intent.putExtra("jsonFileName", "backend_development");
        startActivity(intent);
    }
    @OnClick(R.id.ll_mobile_development)
    void toMobileDevelopmentView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "移动开发");
        intent.putExtra("route", "technology/mobileDevelopment/");
        intent.putExtra("jsonFileName", "mobile_development");
        startActivity(intent);
    }
    @OnClick(R.id.ll_frontend_development)
    void toFrontendDevelopmentView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "前端开发");
        intent.putExtra("route", "technology/frontendDevelopment/");
        intent.putExtra("jsonFileName", "frontend_development");
        startActivity(intent);
    }
    @OnClick(R.id.ll_test)
    void toTestView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "测试");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "test");
        startActivity(intent);
    }
    @OnClick(R.id.ll_operation_maintenance)
    void toOperationMaintenanceView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "运维");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "operation_maintenance");
        startActivity(intent);
    }
    @OnClick(R.id.ll_dba)
    void toDBAView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "DBA");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "dba");
        startActivity(intent);
    }
    @OnClick(R.id.ll_senior_jobs)
    void toSeniorJobsView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "高端职位");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "senior_jobs");
        startActivity(intent);
    }
    @OnClick(R.id.ll_project_management)
    void toProjectManagementView() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "项目管理");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "project_management");
        startActivity(intent);
    }
    @OnClick(R.id.ll_hardware_development)
    void toHardwareDevelopment() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "硬件开发");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "hardware_development");
        startActivity(intent);
    }
    @OnClick(R.id.ll_enterprise_software)
    void toEnterpriseSoftware() {
        Intent intent = new Intent(TechnologyActivity.this, ItemsActivity.class);
        intent.putExtra("title", "企业软件");
        intent.putExtra("route", "technology/");
        intent.putExtra("jsonFileName", "enterprise_software");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
