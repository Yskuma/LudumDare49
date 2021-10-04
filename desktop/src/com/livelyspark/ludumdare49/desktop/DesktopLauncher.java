package com.livelyspark.ludumdare49.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.livelyspark.ludumdare49.LudumDare49;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowSizeLimits(1040,592,1920,1080);
		new Lwjgl3Application(new LudumDare49(new DesktopFloatFormatter()), config);
	}
}
