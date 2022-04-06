package com.kevin.jevil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;

import com.kevin.jevil.devl.Devil;
import com.kevin.jevil.retorfit.models.MResponce;
import com.kevin.jevil.retorfit.RetrofitInstance;
import com.kevin.jevil.retorfit.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Devil.e("Devil is  online : Listening for logs");
//        GestureDetector dic = new GestureDetector(this,this);
    }


    public void makeCall() {
        try {
            UserService service = RetrofitInstance.getRetrofitInstance().create(UserService.class);

            /** Call the method with parameter in the interface to get the notice data*/
            Call<MResponce> call = service.fail();
            call.enqueue(new Callback<MResponce>() {
                @Override
                public void onResponse(Call<MResponce> call, Response<MResponce> response) {

                }

                @Override
                public void onFailure(Call<MResponce> call, Throwable t) {
                    Devil.wtf(call, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callDevil(View view) {
        makeCall();
    }

    public void succes(View view) {

        UserService service = RetrofitInstance.getRetrofitInstance().create(UserService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<MResponce> call = service.getUser();
        call.enqueue(new Callback<MResponce>() {
            @Override
            public void onResponse(Call<MResponce> call, Response<MResponce> response) {

            }

            @Override
            public void onFailure(Call<MResponce> call, Throwable t) {
            }
        });
    }

    public void get404(View view) {

        UserService service = RetrofitInstance.getRetrofitInstance().create(UserService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<MResponce> call = service.get404();
        call.enqueue(new Callback<MResponce>() {
            @Override
            public void onResponse(Call<MResponce> call, Response<MResponce> response) {

            }

            @Override
            public void onFailure(Call<MResponce> call, Throwable t) {
            }
        });
    }

    public void errorLog(View view) {
        Devil.e("Normal error log");
    }

    public void exceptionLog(View view) {
        try {
            String hi = "dd";
            Log.e("blah ", hi.split("gg")[2]);
        } catch (Exception exception) {
            exception.getStackTrace();
            Devil.ex("Sample Exception ", exception);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Devil.kill();
    }


    public void debugLog(View view) {
        Devil.d("This is a sample Debug log");
    }
}
