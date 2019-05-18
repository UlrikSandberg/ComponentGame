/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missile;

import data.GameData;
import services.IWeaponInterface;
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

    private String weaponimage = new File("").getAbsolutePath() + "/Missile/target/Missile-1.0-SNAPSHOT.jar!/images/assets/MissileTurret.jpg";
    
    @Override
    public void dertimeWeaponState(GameData gameData) {
        if(gameData.getSelectedWeapon() == 2)
        {
            gameData.setSelectedWeaponImage(weaponimage);
        }
    }
}
