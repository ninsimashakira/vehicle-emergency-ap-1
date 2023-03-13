package com.example.vehicleemergencyap;

import java.util.List;

public class OpenAIResponse {
    private List<OpenAIChoice> choices;

    public List<OpenAIChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<OpenAIChoice> choices) {
        this.choices = choices;
    }
}
