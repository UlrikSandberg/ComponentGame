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
/**
 *
 * @author mortenskovgaard
 */
public class GameInitializer extends Game
{
    public BitmapFont font;
    public SpriteBatch batch;
    
    @Override
    public void create()
    {
        font = new BitmapFont();
        batch = new SpriteBatch();
        
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
