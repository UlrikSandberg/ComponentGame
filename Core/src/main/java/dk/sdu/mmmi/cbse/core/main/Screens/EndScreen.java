/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.main.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import dk.sdu.mmmi.cbse.core.main.GameEngine;
import dk.sdu.mmmi.cbse.core.main.GameInitializer;

/**
 *
 * @author ulriksandberg
 */
public class EndScreen implements Screen {
    
    private GameInitializer game;
    private double highScore;
    
    public EndScreen(GameInitializer gameInit, double highScore)
    {    
        this.game = gameInit;
        this.highScore = highScore;
    }

    @Override
    public void show() {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
         //To change body of generated methods, choose Tools | Templates.
         
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        

        game.batch.begin();
        
        game.font.setScale(3f);
        
        game.font.draw(game.batch, "Game Over", 400 , 700);
        
        game.font.setScale(1.5f);
        
        game.font.draw(game.batch, "Your score: " + highScore, 350, 600);
        
        game.font.draw(game.batch, "Press the number of the menu you want to go into", 250, 400);
        game.font.draw(game.batch, "1:Play again", 400, 300);
        game.font.draw(game.batch, "2:Main menu", 400, 250);
        game.font.draw(game.batch, "Escape :Quit", 400, 200);
        
        
        game.batch.end();
        
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            game.setScreen(new GameEngine(game));
            dispose();
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
        {
            game.setScreen(new MenuScreen(game));
            dispose();
        }else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int i, int i1) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
         //To change body of generated methods, choose Tools | Templates.
    }
}
