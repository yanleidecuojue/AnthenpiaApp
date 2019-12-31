package club.licona.anthenpiaapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;

/**
 * @author licona
 */
public class SplashActivity extends BaseActivity {

    private Unbinder unbinder;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        this.unbinder = ButterKnife.bind(this);

        init();
    }

    private void init(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Version"+info.versionName);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            tvVersion.setText("Version");
        }

        /**
         * 统一地去获取权限
         */
        performCodeWithPermission("读写sd卡，调用相机", new PermissionCallback() {
            @Override
            public void hasPermission() {
                MMKV mmkv = MMKV.defaultMMKV();
                if(mmkv.decodeString("username") == null){
                    new Handler().postDelayed(() ->
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class)), 1500);
                } else {
                    new Handler().postDelayed(() ->
                            startActivity(new Intent(SplashActivity.this, MainActivity.class)), 1500);
                }

            }

            @Override
            public void noPermission() {
                finish();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}