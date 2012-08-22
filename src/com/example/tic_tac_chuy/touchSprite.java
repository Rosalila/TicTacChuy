package com.example.tic_tac_chuy;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class touchSprite extends Sprite{
	private BitmapTextureAtlas sceneTextureAtlas;
	private static ITextureRegion sceneTextureRegion;
	private boolean touched;
	//This is a fix
	private int coordX, coordY;
	
	public touchSprite(int posX, int posY,  BitmapTextureAtlas sceneTextureAtlas, ITextureRegion sceneTextureRegion){
		super(posX, posY, sceneTextureRegion, (VertexBufferObjectManager)Global.getResource(Global.VERTEX_BUFFERED_OBJECT_MANAGER));
		this.sceneTextureAtlas = sceneTextureAtlas;
		this.touched = false;
	}
	
	public void setCoords(int x, int y){
		this.coordX = x;
		this.coordY = y;
	}
	
	public int getCoordX(){
		return this.coordX;
	}
	
	public int getCoordY(){
		return this.coordY;
	}
	
	public void setMarked(){
		this.touched = true;
	}
	
	public boolean alreadyMarked(){
		return this.touched;
	}
	
	public void setPosition(float posX, float posY){
		this.setX(posX);
		this.setY(posY);
	}
	
	public BitmapTextureAtlas getAtlas(){
		return this.sceneTextureAtlas;
	}
	
	public ITextureRegion getRegion(){
		return touchSprite.sceneTextureRegion;
	}
}