package com.example.tic_tac_chuy;

import java.util.HashMap;

public class Global {
	private static HashMap<String, Object> mResources = new HashMap<String, Object>();
	public static final String VERTEX_BUFFERED_OBJECT_MANAGER = "vertex_buffered_object_manager";
	public static final String TEXTURE_MANAGER = "texture_manager";
	public static final String MAIN_ACTIVITY = "main_activity";
	public static final String GAME_FONT = "game_text_font";
	public static final String MUSIC_MANAGER = "music_manager";
	public static final String FONT_MANAGER = "font_manager";
	
	public static void putResource(String name,Object res)
	{
		mResources.put(name, res);
	}
	
	public static Object getResource(String name)
	{
		return mResources.get(name);
	}
}