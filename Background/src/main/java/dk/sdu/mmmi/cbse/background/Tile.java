/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.background;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.NonEntity;

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
