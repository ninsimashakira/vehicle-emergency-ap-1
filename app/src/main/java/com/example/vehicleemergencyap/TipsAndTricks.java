package com.example.vehicleemergencyap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class TipsAndTricks extends AppCompatActivity {

    private TextView tipTextView;
    private OpenAIInterface openAIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_and_tricks);

        tipTextView = findViewById(R.id.tipTextView);
        Button generateTipButton = findViewById(R.id.generateTipButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openAIInterface = retrofit.create(OpenAIInterface.class);

        generateTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("stat Conn","generating tip");
                generateTip();

            }
        });
    }

    private void generateTip() {
        OpenAIRequest request = new OpenAIRequest(
                "What tip can you give a driver about car maintenance today.",
                100,
                0.5
        );

        Call<OpenAIResponse> call = openAIInterface.getCarMaintenanceTip(request);
        call.enqueue(new Callback<OpenAIResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OpenAIResponse> call, Response<OpenAIResponse> response) {
                if (!response.isSuccessful()) {
                    tipTextView.setText("Error: " + response.code());
                    return;
                }
                assert response.body() != null;
                List<OpenAIChoice> choices = response.body().getChoices();
                if (choices.size() > 0) {
                    OpenAIChoice choice = choices.get(0);
                    tipTextView.setText(choice.getText());
                    System.out.println(choice.getText());
                    Log.e("Success","Tip generated");
                } else {
                    tipTextView.setText("No tip generated.");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<OpenAIResponse> call, Throwable t) {
                tipTextView.setText("Error: " + t.getMessage());
            }
        });

    }
}
