package domywork.android.com.domywork;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);


        Handler handler;
            handler=new Handler();
            boolean b = handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(Splash_activity.this,login_activity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);

        }
}
