/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Screens.MenuScreen;
import com.badlogic.gdx.assets.AssetManager;
import managers.AssetsJarFileResolver;
/**
 *
 * @author mortenskovgaard
 */
public class GameInitializer extends Game
{
    public BitmapFont font;
    public SpriteBatch batch;
    public AssetManager _assetManager;
    
    @Override
    public void create()
    {
        font = new BitmapFont();
        batch = new SpriteBatch();
        AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        _assetManager = new AssetManager(jfhr);
        
        setScreen(new MenuScreen(this));
    }
    
    @Override
    public void render()
    {
        super.render();
    }
    
    @Override
    public void dispose()
    {
        font.dispose();
    }
}
