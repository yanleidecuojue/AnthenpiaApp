package club.licona.anthenpiaapp.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author licona
 */
public class SplashActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}