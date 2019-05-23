/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import main.GameEngine;
import main.GameInitializer;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author mortenskovgaard
 */
public class HighScoreScreen implements Screen
{
    private GameInitializer game;

    public HighScoreScreen(GameInitializer game)
    {
        this.game = game;
    }
    
    @Override
    public void show()
    {
        
    }

    @Override
    public void render(float f)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        

        game.batch.begin();
        
        game.font.setScale(2f);
        game.font.draw(game.batch, "Space :Endgame", 400, 500);
        game.font.draw(game.batch, "This site is not implementet yet", 400, 400);
        game.font.draw(game.batch, "Click any where to go back to the menu", 400, 350);
       
        game.batch.end();
        
        
        if(Gdx.input.isTouched())
        {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
        
    }

    @Override
    public void resize(int i, int i1)
    {
        
    }

    @Override
    public void pause()
    {
        
    }

    @Override
    public void resume()
    {
        
    }

    @Override
    public void hide()
    {
        
    }

    @Override
    public void dispose()
    {
        
    }
    
}
