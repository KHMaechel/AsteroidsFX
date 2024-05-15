package dk.sdu.mmmi.cbse.life;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Score implements IUIProcessingService{

    @Override
    public void generate(GameData gameData, Pane gameWindow){
        resetTotalScore();
        int totalScore = getTotalScore();
        Text scoreText = new Text(10, 20, "Your points: " + totalScore);

        scoreText.setId("scoreText");
        gameWindow.getChildren().add(scoreText);
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {

        int totalScore = getTotalScore();
        Text scoreText = (Text) gameWindow.lookup("#scoreText");
        scoreText.setText("Your points: " + totalScore);

    }

    private int getTotalScore() {
        URL url;
        int score;
        try {
            url = new URL("http://localhost:8080/score");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            score = Integer.parseInt(String.valueOf(response));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    public void resetTotalScore() {
        try {
            URL updateUrl = new URL("http://localhost:8080/reset");
            HttpURLConnection connection = (HttpURLConnection) updateUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Score reset successfully.");
            } else {
                System.out.println("Failed to reset score. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
