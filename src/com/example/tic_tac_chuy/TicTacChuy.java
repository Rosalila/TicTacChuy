package com.example.tic_tac_chuy;

import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;
import android.widget.Toast;

public class TicTacChuy extends SimpleBaseGameActivity implements IOnMenuItemClickListener {

	// ===========================================================
		// Constants
		// ===========================================================

		private static final int CAMERA_WIDTH = 480;
		private static final int CAMERA_HEIGHT = 320;

		// ===========================================================
		// Fields
		// ===========================================================

		protected Camera mCamera;

		protected Scene mMainScene;
		
		protected PauseMenuScene mMenuPauseScene;
		
		TicTacScene mScene;
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		 
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		MusicFactory.setAssetBasePath("mfx/");
		
		Global.putResource(Global.TEXTURE_MANAGER,this.getTextureManager());
		Global.putResource(Global.VERTEX_BUFFERED_OBJECT_MANAGER,this.getVertexBufferObjectManager());
		Global.putResource(Global.MUSIC_MANAGER,this.mEngine.getMusicManager());
		Global.putResource(Global.MAIN_ACTIVITY,this);
		Global.putResource(Global.FONT_MANAGER,this.getFontManager());
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mMainScene = new MainMenuScene(mCamera);
		
		this.mMenuPauseScene = new PauseMenuScene(mCamera);
		
		return mMainScene;
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent)
	{
		if(pEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if(this.mEngine.getScene().hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mEngine.getScene().back();
			} else {
				/* Attach the menu. */
				this.mEngine.getScene().setChildScene(this.mMenuPauseScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		if(pMenuScene instanceof MainMenuScene)
		{
			MainMenuScene main_menu = (MainMenuScene) pMenuScene;
			int item_id=pMenuItem.getID();
			if(main_menu.MENU_RESET==item_id)
			{
				/* Restart the animation. */
				this.mMainScene.reset();

				/* Remove the menu and reset it. */
				this.mMainScene.clearChildScene();
				//this.mMenuScene.reset();
				return true;
			}
			
			if(main_menu.MENU_START==item_id){
				/* End Activity. */
				this.mScene = new TicTacScene();
				this.mScene.loadSelectScreen();
				this.mEngine.setScene(mScene);
				return true;
			}
			
			else if(main_menu.MENU_QUIT==item_id)
			{
				/* End Activity. */
				this.finish();
				return true;
			}
			return false;
		}
		
		else if(pMenuScene instanceof PauseMenuScene)
		{
			PauseMenuScene main_menu = (PauseMenuScene) pMenuScene;
			int item_id=pMenuItem.getID();
			if(main_menu.MENU_RESET==item_id)
			{
				this.mScene = null;
				this.mScene = new TicTacScene();
				this.mScene.loadSelectScreen();
				this.mEngine.setScene(mScene);
				return true;
			}
			else if(main_menu.MENU_MAIN==item_id)
			{
				/* End Activity. */
				this.mEngine.getScene().back();
				this.mEngine.setScene(this.mMainScene);
				return true;
			}
			else if(main_menu.MENU_QUIT==item_id)
			{
				/* End Activity. */
				this.finish();
				return true;
			}
			
			return false;
		}
		
		return false;
	}
}
