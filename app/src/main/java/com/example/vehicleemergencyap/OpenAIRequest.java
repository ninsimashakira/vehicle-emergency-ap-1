package com.example.vehicleemergencyap;

public class OpenAIRequest {
    private String prompt;
    private int max_tokens;
    private double temperature;

    public OpenAIRequest(String prompt, int max_tokens, double temperature) {
        this.prompt = prompt;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
