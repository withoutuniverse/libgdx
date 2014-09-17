package com.without.universe.shlumsy.shmird;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.without.universe.wu.UniversalBundle;
import com.without.universe.wu.core.CoreManager;
import com.without.universe.wu.core.CoreOsManager;
import com.without.universe.wu.core.CoreScreen;
import com.without.universe.wu.encryption.CryptUtil;
import com.without.universe.wu.game.ExampleGameScreen;

public class Main {

	public static int sReleaseGfx = 0;
	public static int sNeedRepack = 0;
	public static int sNeedCrypt = 0;

	// don't forget to press F5 on android project

	public static void main(String[] args) {
		if (sNeedRepack == 1) {
			repackGfx();
			System.out.printf("repacking completed; don't forget to refresh projects(F5 eclipse)");
			return;
		}
		if (sNeedCrypt == 1) {
			cryptAssets();
			System.out.printf("encrypting completed; don't forget to refresh projects(F5 eclipse)");
			return;
		}

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Demo Title";
		cfg.width = 1280;
		cfg.height = 800;
		
		
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

		new LwjglApplication(coreManager, cfg);
	}

	private static void repackGfx() {
		Settings settings = new Settings();
		settings.filterMin = TextureFilter.MipMapNearestNearest;
		settings.filterMag = TextureFilter.MipMapNearestNearest;
		settings.fast = sReleaseGfx != 1;

		TexturePacker.process(settings, "../gfx_unpacked/loader/", "../android/assets/gfx/", "loader");
		// too big texture size (biggest than limit in 1024 pixels)
		//TexturePacker.process(settings, "../gfx_unpacked/main/", "../android/assets/gfx/", "main");
	}

	private static void cryptAssets() {
		try {
			// TODO set "CoreManager.sCrypt = true;"
			CryptUtil.cryptExternalFolder(new File("../android/assets/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}