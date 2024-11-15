package com.example.demo;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Dictionary;

public class LearningTabController {
    @FXML
    private ComboBox<String> Words;
    @FXML
    private ComboBox<String> volume, voice, rate;
    @FXML
    private Button Speak;


    private static VoiceManager voiceManager = VoiceManager.getInstance();
    ObservableList<String> dict = FXCollections.observableArrayList();
    private String[] List = {"Accommodate", "Aesthetic", "Acknowledgment", "Amateur", "Annihilate", "Appalachian", "Assassination", "Asthma", "Bouillon", "Bouquet", "Broccoli", "Camouflage", "Cantaloupe", "Cemetery", "Chandelier", "Connoisseur", "Conscientious", "Counterfeit", "Dilemma", "Disastrous", "Ecstasy", "Embarrass", "Entrepreneur", "Exhilarate", "Fuchsia", "Gubernatorial", "Harassment", "Hierarchy", "Humorous", "Hypocrisy", "Idiosyncrasy", "Ignominious", "Indict", "Irresistible", "Juxtaposition", "Liaison", "Lieutenant", "Maintenance", "Maneuver", "Mischievous", "Narcissistic", "Necessary", "Onomatopoeia", "Paraphernalia", "Perseverance", "Phenomenon", "Reservoir", "Rhythm", "Sophisticated", "Subpoena"};

    public void initialize(){
        dict.addAll(List);
        Words.setItems(dict);
        Words.getStyleClass().add("setting-combo-box");
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i=0;i<=10;i++){
            list.add(Integer.toString(i));
        }
        volume.setItems(list);
        volume.getStyleClass().add("setting-combo-box");
        volume.setValue(volume.getItems().get(0));


        rate.setItems(getSpeedRates());
        rate.setValue(rate.getItems().get(0));
        rate.getStyleClass().add("setting-combo-box");


        voice.setItems(getVoices());
        voice.setValue(voice.getItems().get(0));
        voice.getStyleClass().add("setting-combo-box");

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

    @FXML
    void speak(){
        String msg = Words.getValue();
        String voices = voice.getValue();
        String rates = rate.getValue();
        String volumes = volume.getValue();

        Voice voice = voiceManager.getVoice(voices);
        if (voice == null) {
            System.err.println("Cannot find voice: kevin16");
            System.exit(1);
        }


        // allocate the resources for the voice
        voice.allocate();

        // set the speed at which the text will be spoken (words per minute)
        voice.setRate(Integer.parseInt(rates));

        // set the volume (0-10)
        voice.setVolume(Integer.parseInt(volumes));

        // convert text to speech
        voice.speak(msg);

        // deallocate the resources when done
        voice.deallocate();
    }


}

