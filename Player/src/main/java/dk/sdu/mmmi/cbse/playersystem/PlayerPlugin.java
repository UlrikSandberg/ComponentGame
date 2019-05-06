package dk.sdu.mmmi.cbse.playersystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ScorePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.powerupPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    private String jarUrl;
    
    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        player = createPlayerShip(gameData);
        jarUrl = new File("").getAbsolutePath() + "/Player/target/Player-1.0-SNAPSHOT.jar!/assets/images/ship.png";
        player.setSprite(jarUrl);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth()/2 ;
        float y = gameData.getDisplayHeight()/2 ;
        float radians = 3.1415f / 2;
        

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        Entity playerShip = new Player();
        playerShip.setRadius(8);
        playerShip.setColour(colour);
        playerShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        UUID uuid = UUID.randomUUID();
        playerShip.add(new ShootingPart(uuid.toString()));
        playerShip.add(new LifePart(1));
        playerShip.add(new powerupPart());
        playerShip.add(new ScorePart(true));
        
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
    


}
