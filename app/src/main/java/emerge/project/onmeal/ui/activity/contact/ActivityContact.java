package emerge.project.onmeal.ui.activity.contact;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;

public class ActivityContact extends Activity {


    @BindView(R.id.openSansLight444)
    TextView textviewVersion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        textviewVersion.setText("Version "+getApplicationVersion());

    }
    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public String getApplicationVersion() {
        String version;
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            version = "1.0.0";
            e.printStackTrace();
        }
         version = pInfo.versionName;
        return version;
    }
}
