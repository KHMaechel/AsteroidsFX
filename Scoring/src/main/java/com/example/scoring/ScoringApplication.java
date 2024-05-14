package com.example.scoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@RestController
public class ScoringApplication {

    private int totalScore = 0;
    private int level = 1;
    private int nextLevelScore = 2;

    public static void main(String[] args) {
        SpringApplication.run(ScoringApplication.class, args);
    }

    @GetMapping("/update-score")
    public int updateTotalScore(@RequestParam(value = "point") Long point) {
        totalScore += point;
        while (totalScore >= nextLevelScore) {
            level++; // Increment the level
            nextLevelScore += level * 2;
        }
        return totalScore ;
    }

    @GetMapping("/score")
    public String getTotalScore() {
        return String.valueOf(totalScore);
    }

    @GetMapping("/reset")
    public void resetScore() {
        totalScore = 0;
    }

    @GetMapping("/update-level")
    public void updateLevel() {
        totalScore ++;
    }

    @GetMapping("/level")
    public String getLevel() {
        return String.valueOf(level);
    }

    @GetMapping("/reset-level")
    public void resetLevel() {
        level = 1;
        nextLevelScore = 2;
    }

}
