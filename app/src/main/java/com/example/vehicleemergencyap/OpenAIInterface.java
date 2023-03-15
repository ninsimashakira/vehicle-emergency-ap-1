package com.example.vehicleemergencyap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIInterface {
    @Headers({"Content-Type: application/json", "Authorization: Bearer API_KEY"})
    @POST("engines/text-davinci-003/completions")
    Call<OpenAIResponse> getCarMaintenanceTip(@Body OpenAIRequest request);
}
