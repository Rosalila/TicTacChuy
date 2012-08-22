package com.example.tic_tac_chuy;

//import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
//import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.content.Context;
import java.util.ArrayList;

	public class TicTacScene extends Scene {
		private SceneSprite bg, chuy, sBg;
		private ArrayList <ArrayList<touchSprite>> grid;
		private ArrayList <ArrayList<Character>> logicGrid;
		private int gridXInit = 90;
		private int gridYInit = 10;
		private int turnCounter = 0;
		private touchSprite selectX, selectO;
		private char player1Choice, player2Choice, lastTurn; // = ' ';

	@SuppressWarnings("serial")
	public TicTacScene() {
		this.player1Choice = this.player2Choice = this.lastTurn = ' ';
		this.grid = new ArrayList<ArrayList<touchSprite>>() {{
			add(new ArrayList<touchSprite>());
			add(new ArrayList<touchSprite>());
			add(new ArrayList<touchSprite>());
		}};
		this.logicGrid = new ArrayList<ArrayList<Character>>() {{
			add(new ArrayList<Character>());
			add(new ArrayList<Character>());
			add(new ArrayList<Character>());
		}};
		this.selectScreen();
		BitmapTextureAtlas sceneTextureAtlas = new BitmapTextureAtlas((TextureManager) Global.getResource(Global.TEXTURE_MANAGER), 95, 95, TextureOptions.BILINEAR);
		ITextureRegion sceneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sceneTextureAtlas, (Context) Global.getResource(Global.MAIN_ACTIVITY), "area.png", 0, 0);
		this.setGrid(sceneTextureAtlas, sceneTextureRegion);
	}
	
	private void selectScreen() {
		this.sBg = new SceneSprite("selectBackground.png", 480, 320, 0, 0);
		BitmapTextureAtlas atlas = new BitmapTextureAtlas((TextureManager) Global.getResource(Global.TEXTURE_MANAGER), 95, 95, TextureOptions.BILINEAR);
		ITextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, (Context) Global.getResource(Global.MAIN_ACTIVITY), "sx.png", 0, 0);
		this.selectX = new touchSprite(gridXInit, gridYInit + 95, atlas, region) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (!this.alreadyMarked()) {
						if (TicTacScene.this.player1Choice == ' ') {
							TicTacScene.this.player1Choice = 'x';
						} 
						else if (TicTacScene.this.player1Choice == 'o') {
							TicTacScene.this.player2Choice = 'x';
						}
						if (TicTacScene.this.player1Choice != ' ' && TicTacScene.this.player2Choice != ' ') {
							TicTacScene.this.unloadSelectScreen();
							TicTacScene.this.loadGrid();
						}
						this.setMarked();
					}
					return true;
				} 
				else {
					return false;
				}
			}
		};

		BitmapTextureAtlas atlas1 = new BitmapTextureAtlas((TextureManager) Global.getResource(Global.TEXTURE_MANAGER), 95, 95, TextureOptions.BILINEAR);
		ITextureRegion region1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas1, (Context) Global.getResource(Global.MAIN_ACTIVITY), "so.png", 0, 0);
		this.selectO = new touchSprite(gridXInit + 95 + 95, gridYInit + 95, atlas1, region1) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (!this.alreadyMarked()) {
						if (TicTacScene.this.player1Choice == ' ') {
							TicTacScene.this.player1Choice = 'o';
						} 
						else if (TicTacScene.this.player1Choice == 'x') {
							TicTacScene.this.player2Choice = 'o';
						}
						if (TicTacScene.this.player1Choice != ' '
								&& TicTacScene.this.player2Choice != ' ') {
							TicTacScene.this.unloadSelectScreen();
							TicTacScene.this.loadGrid();
						}
						this.setMarked();
					}
					return true;
				} else {
					return false;
				}
			}
		};
	}

	private void setGrid(BitmapTextureAtlas atlas, ITextureRegion region) {
		this.bg = new SceneSprite("background.png", 480, 320, 0, 0);
		// Population rules the nation
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				this.logicGrid.get(x).add(' ');
				touchSprite temp = new touchSprite(gridXInit, gridYInit, atlas, region) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						if (!this.alreadyMarked()) {
							if (TicTacScene.this.lastTurn == ' ') {
								TicTacScene.this.loadNewChuy((float) (this.getX() + 47.5 - 18), (float) (this.getY() + 47.5 - 17), TicTacScene.this.player1Choice);
								TicTacScene.this.lastTurn = TicTacScene.this.player1Choice;
							}
							else if (TicTacScene.this.lastTurn == TicTacScene.this.player1Choice) {
								TicTacScene.this.loadNewChuy((float) (this.getX() + 47.5 - 18), (float) (this.getY() + 47.5 - 17), TicTacScene.this.player2Choice);
								TicTacScene.this.lastTurn = TicTacScene.this.player2Choice;
							}
							else if (TicTacScene.this.lastTurn == TicTacScene.this.player2Choice) {
								TicTacScene.this.loadNewChuy((float) (this.getX() + 47.5 - 18), (float) (this.getY() + 47.5 - 17), TicTacScene.this.player1Choice);
								TicTacScene.this.lastTurn = TicTacScene.this.player1Choice;
							}
							this.setMarked();
							TicTacScene.this.turnCounter++;
							TicTacScene.this.logicGrid.get(this.getCoordX()).set(this.getCoordY(), TicTacScene.this.lastTurn);
							if (TicTacScene.this.turnCounter > 4) {
								TicTacScene.this.logic();
							}
						}
							return true;
					}
					else {
						return false;
					}
				}
				};
				temp.setCoords(x, y);
				this.grid.get(x).add(temp);
			}
		}
		// this.grid.get(0).get(0).setPosition(gridXInit, gridYInit); //No es necesario
		this.grid.get(0).get(1).setPosition(gridXInit + 95 + 5, gridYInit);
		this.grid.get(0).get(2).setPosition(gridXInit + 95 + 5 + 95 + 5, gridYInit);
		this.grid.get(1).get(0).setPosition(gridXInit, gridYInit + 95 + 5);
		this.grid.get(1).get(1).setPosition(gridXInit + 95 + 5, gridYInit + 95 + 5);
		this.grid.get(1).get(2).setPosition(gridXInit + 95 + 5 + 95 + 5, gridYInit + 95 + 5);
		this.grid.get(2).get(0).setPosition(gridXInit, gridYInit + 95 + 5 + 95 + 5);
		this.grid.get(2).get(1).setPosition(gridXInit + 95 + 5, gridYInit + 95 + 5 + 95 + 5);
		this.grid.get(2).get(2).setPosition(gridXInit + 95 + 5 + 95 + 5, gridYInit + 95 + 5 + 95 + 5);
	}
	
	public void loadSelectScreen() {
		this.sBg.getAtlas().load();
		this.selectX.getAtlas().load();
		this.selectO.getAtlas().load();
		this.attachChild(this.sBg.getSprite());
		this.attachChild(selectX);
		this.attachChild(selectO);
		this.registerTouchArea(selectX);
		this.registerTouchArea(selectO);
	}

	
	private void unloadSelectScreen() {
		this.unregisterTouchArea(this.selectO);
		this.selectO.dispose();
		this.selectO.getAtlas().unload();
		this.selectO.detachSelf();

		this.unregisterTouchArea(this.selectX);
		this.selectX.dispose();
		this.selectX.getAtlas().unload();
		this.selectX.detachSelf();

		this.sBg.getSprite().dispose();
		this.sBg.getAtlas().unload();
		this.sBg.getSprite().detachSelf();
	}

	
	private void loadGrid() {
		this.bg.getAtlas().load();
		this.attachChild(this.bg.getSprite());

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				this.grid.get(x).get(y).getAtlas().load();
				this.attachChild(this.grid.get(x).get(y));
				this.registerTouchArea(this.grid.get(x).get(y));
			}
		}
	}

	/*
	 * public void loadChuy(){ this.setOnSceneTouchListener(new
	 * IOnSceneTouchListener() {
	 * 
	 * @Override public boolean onSceneTouchEvent(final Scene pScene, final
	 * TouchEvent pSceneTouchEvent) { if(pSceneTouchEvent.isActionDown()) {
	 * TicTacScene.this.loadNewChuy(pSceneTouchEvent.getX(),
	 * pSceneTouchEvent.getY()); }
	 * 
	 * return true; } });
	 * 
	 * }
	 */
	private void loadNewChuy(float x, float y, char c) {
		if (c == 'x') {
			this.chuy = new SceneSprite("equis.png", 36, 34, x, y);
		}
		if (c == 'o') {
			this.chuy = new SceneSprite("cero.png", 23, 25, x, y);
		}
		this.chuy.getAtlas().load();
		this.attachChild(this.chuy.getSprite());
	}

	
	private void unsetTouch(){
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				this.unregisterTouchArea(this.grid.get(x).get(y));
			}
		}
	}
	
	
	private void displayWin(String winType, float displayX, float displayY) {
		SceneSprite win = null;
		if (winType.equals("horizontal")) {
			win = new SceneSprite("win_horizontal.png", 240, 17, displayX, displayY);
		} 
		else if (winType.equals("vertical")) {
			win = new SceneSprite("win_vertical.png", 14, 183, displayX, displayY);
		} 
		else if (winType.equals("diagonalLeft")) {
			win = new SceneSprite("win_diagonal_1.png", 190, 180, displayX, displayY);
		} 
		else if (winType.equals("diagonalRight")) {
			win = new SceneSprite("win_diagonal_2.png", 190, 172, displayX, displayY);
		}
		else if (winType.equals("draw")) {
			win = new SceneSprite("draw.png", 95, 95, displayX, displayY);
		}
		win.getAtlas().load();
		this.unsetTouch();
		this.attachChild(win.getSprite());
	}
	
	private void logic() {
		// Luego del viaje
		// Los 8 casos para el win
		if (this.logicGrid.get(0).get(0) == 'x' && this.logicGrid.get(0).get(1) == 'x' && this.logicGrid.get(0).get(2) == 'x') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5));
		}
		else if (this.logicGrid.get(1).get(0) == 'x' && this.logicGrid.get(1).get(1) == 'x' && this.logicGrid.get(1).get(2) == 'x') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5 + 95 + 5));
		}
		else if (this.logicGrid.get(2).get(0) == 'x' && this.logicGrid.get(2).get(1) == 'x' && this.logicGrid.get(2).get(2) == 'x') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5 + 95 + 5 + 95 + 5));
		}
		else if (this.logicGrid.get(0).get(0) == 'x' && this.logicGrid.get(1).get(0) == 'x' && this.logicGrid.get(2).get(0) == 'x') {
			this.displayWin("vertical", (float) (90 + 47.5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(1) == 'x' && this.logicGrid.get(1).get(1) == 'x'	&& this.logicGrid.get(2).get(1) == 'x') {
			this.displayWin("vertical", (float) (90 + 47.5 + 95 + 5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(2) == 'x' && this.logicGrid.get(1).get(2) == 'x'	&& this.logicGrid.get(2).get(2) == 'x') {
			this.displayWin("vertical", (float) (90 + 47.5 + 95 + 5 + 95 + 5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(0) == 'x' && this.logicGrid.get(1).get(1) == 'x'	&& this.logicGrid.get(2).get(2) == 'x') {
			this.displayWin("diagonalLeft", (float) 90, (float) 10);
		}
		else if (this.logicGrid.get(0).get(2) == 'x' && this.logicGrid.get(1).get(1) == 'x' && this.logicGrid.get(2).get(0) == 'x') {
			this.displayWin("diagonalRight", (float) (90 + 95 + 5), (float) 10);
		}
		// Otros 8 para O
		else if (this.logicGrid.get(0).get(0) == 'o' && this.logicGrid.get(0).get(1) == 'o' && this.logicGrid.get(0).get(2) == 'o') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5));
		}
		else if (this.logicGrid.get(1).get(0) == 'o' && this.logicGrid.get(1).get(1) == 'o'	&& this.logicGrid.get(1).get(2) == 'o') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5 + 95 + 5));
		}
		else if (this.logicGrid.get(2).get(0) == 'o' && this.logicGrid.get(2).get(1) == 'o'	&& this.logicGrid.get(2).get(2) == 'o') {
			this.displayWin("horizontal", (float) 90, (float) (10 + 47.5 + 95 + 5 + 95 + 5));
		}
		else if (this.logicGrid.get(0).get(0) == 'o' && this.logicGrid.get(1).get(0) == 'o'	&& this.logicGrid.get(2).get(0) == 'o') {
			this.displayWin("vertical", (float) (90 + 47.5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(1) == 'o' && this.logicGrid.get(1).get(1) == 'o'	&& this.logicGrid.get(2).get(1) == 'o') {
			this.displayWin("vertical", (float) (90 + 47.5 + 95 + 5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(2) == 'o' && this.logicGrid.get(1).get(2) == 'o'	&& this.logicGrid.get(2).get(2) == 'o') {
			this.displayWin("vertical", (float) (90 + 47.5 + 95 + 5 + 95 + 5), (float) 10);
		}
		else if (this.logicGrid.get(0).get(0) == 'o' && this.logicGrid.get(1).get(1) == 'o'	&& this.logicGrid.get(2).get(2) == 'o') {
			this.displayWin("diagonalLeft", (float) 90, (float) 10);
		}
		else if (this.logicGrid.get(0).get(2) == 'o' && this.logicGrid.get(1).get(1) == 'o'	&& this.logicGrid.get(2).get(0) == 'o') {
			this.displayWin("diagonalRight", (float) (90 + 95 + 5), (float) 10);
		} 
		else if (this.turnCounter == 9){
			this.displayWin("draw", (float) 0, (float) (10 + 47.5 + 95 + 5	+ 47.5));
		}
	}
}