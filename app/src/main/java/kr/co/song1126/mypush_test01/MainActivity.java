package kr.co.song1126.mypush_test01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    EditText etTitle, etMsg;
    String token;

    String title, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle=findViewById(R.id.title_et);
        etMsg=findViewById(R.id.msg_et);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String msg=intent.getStringExtra("msg");

//        getSupportActionBar().setTitle(name);
//        getSupportActionBar().setSubtitle(msg);

        getToken();

    }

    void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()) {Log.w("NOTOKEN", task.getException());return;}// 토큰 얻어오지 못했을때..

                token=task.getResult().getToken();// 이 값을 서버에 저장해야함...
                Log.d("getToken", token);
//                에뮬레이터 토큰
//                c8TIX2tiRs-AmWzsDwg1wQ:APA91bHC2hJ6jKMBeQFzKYNOgJ7IjMkDNM_Xmc1Lp8wLOqyXB1I_lMTQmaV7b3mG1CXHf6KWiHCfaYFFZgPuQoTZEjV32KOu4QUV0mEBSkSNA9lwbLrM421oDXnspzaPaxDj6ymWQAMh
            }

        });

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }


    public void FCM(View view) {


        title=etTitle.getText().toString();
        msg=etMsg.getText().toString();

        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Map<String, String> dataPart=new HashMap<>();
        dataPart.put("title",title);
        dataPart.put("msg",msg);
        dataPart.put("token", token);

        Call<String> call=retrofitService.postDataFCM(dataPart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String s=response.body();
                    Log.d("responseBody : ", s);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("responseError : ", t.getMessage());
            }
        });

    }
}
