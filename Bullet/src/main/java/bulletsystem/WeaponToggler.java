/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulletsystem;

import data.GameData;
import services.IEntityProcessingService;
import services.IWeaponInterface;
import java.io.Console;
import java.io.File;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author ulriksandberg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IWeaponInterface.class),})
public class WeaponToggler implements IWeaponInterface {

    private String toggleWeaponString = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/toggleLaserWeapon.jpg";
    
    @Override
    public void dertimeWeaponState(GameData gameData) {
        
        if(gameData.getSelectedWeapon() == 1)
        {   
            gameData.setSelectedWeaponImage(toggleWeaponString);
        }
    }
}
