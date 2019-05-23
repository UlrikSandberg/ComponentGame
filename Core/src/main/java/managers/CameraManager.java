/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import data.GameData;

/**
 *
 * @author casperhasnsen
 */
public class CameraManager {
    
    
    
    public void EdgeMovement(OrthographicCamera cam , GameData gameData) {
        
         if (gameData.getPlayerPositionX() < cam.viewportWidth/2) {
           cam.position.x =  cam.viewportWidth/2;

        } else if (gameData.getPlayerPositionX() > gameData.getDisplayWidth() - cam.viewportWidth/2) {
            cam.position.x = gameData.getDisplayWidth() - cam.viewportWidth/2;
        } else {
            cam.position.x = gameData.getPlayerPositionX();
        }
        
        if (gameData.getPlayerPositionY() < cam.viewportHeight/2) {
            cam.position.y = cam.viewportHeight/2;
        } else if (gameData.getPlayerPositionY() > gameData.getDisplayHeight() - cam.viewportHeight/2) {
            cam.position.y = gameData.getDisplayHeight() - cam.viewportHeight/2;
        } else {
            cam.position.y = gameData.getPlayerPositionY();
        }
    }
}
