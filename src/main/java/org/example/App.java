package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.beans.value.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private final int standardNumSelectedEnglish = 1;
    private final int standardNumSelectedSpanish = 5;
    public int numSelectedEnglish = standardNumSelectedEnglish;
    public int numSelectedSpanish = standardNumSelectedSpanish;
    public int numSelectedPause = 10;
    public int numSelectedPauseAfter = 5;
    public List<List<String>> sentences;

    @Override
    public void start(Stage stage) throws IOException {
        VBox mainBox = new VBox();
        HBox englishBox = new HBox();
        HBox spanishBox = new HBox();
        HBox pauseBox = new HBox();
        HBox pauseBoxAfter = new HBox();

        Label title = new Label("LI-Maker");
        title.setFont(Font.font(40));
        Label english = new Label("English repetitions: ");
        Label spanish = new Label("Spanish repetitions: ");
        Label pause = new Label("Pause in between [s]: ");
        Label pauseAfter = new Label("Pause after [s]: ");
        String numMotherTongue[] = {"0", "1", "2", "3", "4", "5"};
        String numForeignLanguage[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ChoiceBox choiceBoxEnglish = new ChoiceBox(FXCollections.observableArrayList(numMotherTongue));
        choiceBoxEnglish.setValue(standardNumSelectedEnglish);
        ChoiceBox choiceBoxSpanish = new ChoiceBox(FXCollections.observableArrayList(numForeignLanguage));
        choiceBoxSpanish.setValue(standardNumSelectedSpanish);

        Label englishSelected = new Label(numMotherTongue[numSelectedEnglish] + " selected");
        Label spanishSelected = new Label(numForeignLanguage[numSelectedSpanish] + " selected");
        Label pauseSelected = new Label("Value: " + numSelectedPause + " s");
        Label pauseSelectedAfter = new Label("Value: " + numSelectedPauseAfter + " s");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open csv file");
        Button buttonFileChooser = new Button("Open csv file");
        Button buttonGenerate = new Button("Generate");

        buttonFileChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    sentences = new ArrayList<>();
                    try {
                        Scanner scanner = new Scanner(file);

                        while (scanner.hasNextLine()) {
                            sentences.add(getRecordFromLine(scanner.nextLine()));
                        }

                        scanner.close();
                        System.out.println(sentences);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        choiceBoxEnglish.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                numSelectedEnglish = new_value.intValue();
                englishSelected.setText(numMotherTongue[numSelectedEnglish] + " selected");
            }
        });

        choiceBoxSpanish.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                numSelectedSpanish = new_value.intValue();
                spanishSelected.setText(numForeignLanguage[numSelectedSpanish] + " selected");
            }
        });

        Slider slider = new Slider(0, 30, 10);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                numSelectedPause = newValue.intValue();
                pauseSelected.setText("Value: " + numSelectedPause + " s");
            }
        });

        Slider sliderAfter = new Slider(0, 15, 5);
        sliderAfter.setShowTickLabels(true);
        sliderAfter.setShowTickMarks(true);

        sliderAfter.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                numSelectedPauseAfter = newValue.intValue();
                pauseSelectedAfter.setText("Value: " + numSelectedPauseAfter + " s");
            }
        });

        mainBox.setSpacing(50);
        mainBox.setPadding(new Insets(50));
        englishBox.setSpacing(10);
        spanishBox.setSpacing(10);
        pauseBox.setSpacing(10);
        pauseBoxAfter.setSpacing(10);
        mainBox.setAlignment(Pos.TOP_CENTER);
        englishBox.setAlignment(Pos.TOP_CENTER);
        spanishBox.setAlignment(Pos.TOP_CENTER);
        pauseBox.setAlignment(Pos.TOP_CENTER);
        pauseBoxAfter.setAlignment(Pos.TOP_CENTER);

        englishBox.getChildren().add(english);
        englishBox.getChildren().add(choiceBoxEnglish);
        englishBox.getChildren().add(englishSelected);

        spanishBox.getChildren().add(spanish);
        spanishBox.getChildren().add(choiceBoxSpanish);
        spanishBox.getChildren().add(spanishSelected);

        pauseBox.getChildren().add(pause);
        pauseBox.getChildren().add(slider);
        pauseBox.getChildren().add(pauseSelected);

        pauseBoxAfter.getChildren().add(pauseAfter);
        pauseBoxAfter.getChildren().add(sliderAfter);
        pauseBoxAfter.getChildren().add(pauseSelectedAfter);

        mainBox.getChildren().add(title);
        mainBox.getChildren().add(englishBox);
        mainBox.getChildren().add(spanishBox);
        mainBox.getChildren().add(pauseBox);
        mainBox.getChildren().add(pauseBoxAfter);
        mainBox.getChildren().add(buttonFileChooser);
        mainBox.getChildren().add(buttonGenerate);

        Scene scene = new Scene(mainBox, 800, 800);

        stage.setScene(scene);
        stage.setTitle("LI-Maker");

        stage.show();
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void main(String[] args) {
        launch();
    }

}