package kr.co.song1126.mypush_test01;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RetrofitService {

    @Multipart
    @POST("/FCM_test/fcmPush.php")
    Call<String> postDataFCM(@PartMap Map<String, String> dataPart);
}
