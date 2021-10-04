package com.livelyspark.ludumdare49.managers;

import com.livelyspark.ludumdare49.enums.Screens;

public interface IScreenManager {
    void switchScreen(Screens screen);
    void setGameOverMessage(String gameOverMessage);
}
