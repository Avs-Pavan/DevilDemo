package com.kevin.jevil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kevin.devil.Devil;
import com.kevin.devil.models.DevilMessage;
import com.kevin.jevil.retorfit.models.MResponce;
import com.kevin.jevil.retorfit.RetrofitInstance;
import com.kevin.jevil.retorfit.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void success(View view) {

        UserService service = RetrofitInstance.getRetrofitInstance().create(UserService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<MResponce> call = service.getUser();
        call.enqueue(new Callback<>() {
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

    private boolean isShowing = false;

    void showSubjectDialog() {
        isShowing = true;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.new_message_dialog, null);

        Button primabutton = view.findViewById(R.id.primaryButton);
        primabutton.setOnClickListener(view1 -> {
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, MessagingActivity.class));
        });
        Button sec = view.findViewById(R.id.secondaryButton);
        sec.setOnClickListener(view1 -> {
            dialog.dismiss();
            isShowing = false;
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        dialog.show();

    }

    public void debugLog(View view) {
        Devil.d("This is a sample Debug log");
    }

    public void sendMessage(View view) {
        startActivity(new Intent(this, MessagingActivity.class));
    }

    @Override
    public void updateMessage(@NonNull String topic, @NonNull String payload) {
        if (!isShowing)
            showSubjectDialog();
    }
}
