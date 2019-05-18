package enemysystem;

import data.Entity;
import data.GameData;
import data.World;
import entityparts.ControlPart;
import entityparts.LifePart;
import entityparts.MovingPart;
import entityparts.PositionPart;
import entityparts.ShootingPart;
import enemy.Enemy;
import services.IGamePluginService;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        
        startSpawner(gameData,world);
        // Add entities to the world
        enemy = createEnemyShip(gameData);
        enemy.setSprite(new File("").getAbsolutePath() + "/Enemy/target/Enemy-1.0-SNAPSHOT.jar!/assets/images/ufoAbove.png");
        world.addEntity(enemy);
        
    }
    
    private void startSpawner(GameData gameData, World world) {
        
        Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new EnemySpawner(gameData,world), 0, 5000);
    }
    

    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.setColour(colour);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(3));
        enemyShip.add(new ControlPart(true));
        enemyShip.setSprite(new File("").getAbsolutePath() + "/Enemy/target/Enemy-1.0-SNAPSHOT.jar!/assets/images/ufoAbove.png");
        enemyShip.add(new ShootingPart("fromEnemy"));

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

}
