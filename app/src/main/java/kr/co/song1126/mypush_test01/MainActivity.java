package kr.co.song1126.mypush_test01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.notifiTV);

        getToken();

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String msg=intent.getStringExtra("msg");

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle(msg);


    }

    void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()) {Log.w("NOTOKEN", task.getException());return;}

                String token=task.getResult().getToken();// 이 값을 서버에 저장
                Log.d("getToken", token);
//                에뮬레이터 토큰
//                c8TIX2tiRs-AmWzsDwg1wQ:APA91bHC2hJ6jKMBeQFzKYNOgJ7IjMkDNM_Xmc1Lp8wLOqyXB1I_lMTQmaV7b3mG1CXHf6KWiHCfaYFFZgPuQoTZEjV32KOu4QUV0mEBSkSNA9lwbLrM421oDXnspzaPaxDj6ymWQAMh
            }

        });

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }


    public void FCM(View view) {
    }
}
