package com.without.universe.android.demo;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.without.universe.wu.UniversalBundle;
import com.without.universe.wu.core.CoreManager;
import com.without.universe.wu.core.CoreOsManager;
import com.without.universe.wu.core.CoreScreen;
import com.without.universe.wu.game.ExampleGameScreen;

public class GameActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		CoreManager coreManager = new CoreManager(new CoreOsManager() {
			@Override
			public void doAction(int actionId, UniversalBundle bundle) {
			}
		}, 32, 20) {
			@Override
			public void create() {
				super.create();
				CoreScreen screen = new ExampleGameScreen();
				pushScreen(screen);
				pushScreen(screen.createLoaderScreen());
			}
		};

		View gameView = initializeForView(coreManager, cfg);
		layout.addView(gameView);
		setContentView(layout);
	}

}