/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityparts;

import data.Entity;
import data.GameData;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author ulriksandberg
 */
public class CreatedMetaDataPart implements EntityPart {

    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public CreatedMetaDataPart(LocalDateTime date)
    {
        this.date = date;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
         //To change body of generated methods, choose Tools | Templates.
    }
}
