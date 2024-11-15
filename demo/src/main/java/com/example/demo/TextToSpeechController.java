package com.example.demo;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;



public class TextToSpeechController {
    @FXML
    private Button speakbutton;
    @FXML
    private TextArea textbox;
    @FXML
    private ChoiceBox<String> volume, voice, rate;

    private static final VoiceManager voiceManager = VoiceManager.getInstance();


    public void initialize(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i=0;i<=10;i++){
            list.add(Integer.toString(i));
        }

        volume.setItems(list);
        volume.setValue(volume.getItems().get(0));
        volume.getStyleClass().add("setting-combo-box");


        rate.setItems(getSpeedRates());
        rate.setValue(rate.getItems().get(0));
        rate.getStyleClass().add("setting-combo-box");

        voice.setItems(getVoices());
        voice.setValue(voice.getItems().get(0));
        voice.getStyleClass().add("setting-combo-box");

        speakbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String msg = textbox.getText();
                String voices = voice.getValue();
                String rates = rate.getValue();
                String volumes = volume.getValue();
                speak(msg, voices, rates, volumes);
            }
        });
    }

    public static ObservableList<String> getVoices() {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");


        ObservableList<String> voices = FXCollections.observableArrayList();
        for(Voice voice : voiceManager.getVoices()){
            voices.add(voice.getName());
        }

        return voices;
    }

    public static ObservableList<String> getSpeedRates() {
        ObservableList<String> speedRates = FXCollections.observableArrayList();
        speedRates.add("140"); // normal
        speedRates.add("170"); // fast
        speedRates.add("200"); // very fast
        speedRates.add("100"); // slow
        speedRates.add("60"); // very slow
        return speedRates;
    }

    public static void speak(String message, String voiceType, String rate, String volume) {
        Voice voice = voiceManager.getVoice(voiceType);
        if (voice == null) {
            System.err.println("Cannot find voice: kevin16");
            System.exit(1);
        }


        // allocate the resources for the voice
        voice.allocate();

        // set the speed at which the text will be spoken (words per minute)
        voice.setRate(Integer.parseInt(rate));

        // set the volume (0-10)
        voice.setVolume(Integer.parseInt(volume));

        // convert text to speech
        voice.speak(message);

        // deallocate the resources when done
        voice.deallocate();
    }


}
