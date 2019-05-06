/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.main.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.main.GameEngine;
import dk.sdu.mmmi.cbse.core.main.GameInitializer;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author mortenskovgaard
 */
public class MenuScreen implements Screen
{
    private GameInitializer game;

    public MenuScreen(GameInitializer game)
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
        
        game.font.setScale(1.5f);
        game.font.draw(game.batch, "Space:Endgame", 350, 600);
        game.font.draw(game.batch, "Press the number of the menu you want to go into", 250, 400);
        game.font.draw(game.batch, "1: Play", 400, 300);
        game.font.draw(game.batch, "Escape: Quit", 400, 250);
        
        
        game.batch.end();
        
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            game.setScreen(new GameEngine(game));
            dispose();
        } else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            
            Gdx.app.exit();
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
