/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityparts;

import data.Entity;
import data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {

    void process(GameData gameData, Entity entity);
}
