package com.example.tic_tac_chuy;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import android.content.Context;
import android.opengl.GLES20;

public class MainMenuScene extends MenuScene{
	
	private Sprite bg;

	public static final int MENU_RESET = 0;
	public static final int MENU_QUIT = MENU_RESET + 1;
	public static final int MENU_START = MENU_QUIT + 1;
	
	private BitmapTextureAtlas mMenuTexture;
	private ITextureRegion mMenuStartTextureRegion;
	private ITextureRegion mMenuQuitTextureRegion;
	
	public MainMenuScene(Camera camera)
	{
		super(camera);
		
		this.setBackgroundEnabled(false);
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas((TextureManager) Global.getResource(Global.TEXTURE_MANAGER), 720, 480, TextureOptions.BILINEAR);
		ITextureRegion mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, (Context)Global.getResource(Global.MAIN_ACTIVITY), "main_background.png", 0, 0);
		mBitmapTextureAtlas.load();
		bg= new Sprite(0, 0, mTextureRegion, (VertexBufferObjectManager) Global.getResource(Global.VERTEX_BUFFERED_OBJECT_MANAGER));
		this.attachChild(bg);
		
		this.mMenuTexture = new BitmapTextureAtlas((TextureManager) Global.getResource(Global.TEXTURE_MANAGER), 200, 150, TextureOptions.BILINEAR);
		this.mMenuStartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, (Context)Global.getResource(Global.MAIN_ACTIVITY), "menu_ok.png", 0, 50);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, (Context)Global.getResource(Global.MAIN_ACTIVITY), "menu_quit.png", 0, 100);
		this.mMenuTexture.load();
		
		final SpriteMenuItem startMenuItem = new SpriteMenuItem(MENU_START, this.mMenuStartTextureRegion, (VertexBufferObjectManager)Global.getResource(Global.VERTEX_BUFFERED_OBJECT_MANAGER) );
		startMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.addMenuItem(startMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.mMenuQuitTextureRegion, (VertexBufferObjectManager)Global.getResource(Global.VERTEX_BUFFERED_OBJECT_MANAGER) );
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.addMenuItem(quitMenuItem);

		this.buildAnimations();

		this.setOnMenuItemClickListener((TicTacChuy)Global.getResource(Global.MAIN_ACTIVITY));
	}
}
