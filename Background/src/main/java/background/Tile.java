/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import data.Entity;
import data.NonEntity;

/**
 *
 * @author ulriksandberg
 */
public class Tile extends NonEntity {
    
    public Tile(String imageUrl)
    {
        setSprite(imageUrl);
    }
}
