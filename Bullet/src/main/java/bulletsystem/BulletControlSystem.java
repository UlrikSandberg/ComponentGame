/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulletsystem;

import commonplayer.Player;
import data.Entity;
import data.GameData;
import data.World;
import entityparts.LifePart;
import entityparts.MovingPart;
import entityparts.PositionPart;
import entityparts.ProjectilePart;
import entityparts.ShootingPart;
import entityparts.SizePart;
import entityparts.TimerPart;
import enemy.Enemy;
import services.IEntityProcessingService;
import commonprojectile.Projectile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletControlSystem implements IEntityProcessingService {

    private Entity bullet;
    
    private String imageUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserbeam.png";
    private String soundUrl = new File("").getAbsolutePath() + "/Bullet/target/Bullet-1.0-SNAPSHOT.jar!/assets/images/laserSound.mp3";
    
    private LocalDateTime cooldown = LocalDateTime.now();

    @Override
    public void process(GameData gameData, World world) {
        
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {

                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (shootingPart.isShooting()) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                    if(gameData.getSelectedWeapon() == 1)
                    {
                        LocalDateTime now = LocalDateTime.now();
                        long millis = ChronoUnit.MILLIS.between(cooldown, now);
                        if (millis < 150 || gameData.getSelectedWeapon() != 1){
                            break;
                        }
                        cooldown = now;
                        
                        if(entity instanceof Player)
                        {
                            bullet = createBullet(positionPart.getX() + entity.getRadius(), positionPart.getY() + entity.getRadius(), positionPart.getRadians(), shootingPart.getID());
                        }
                        else if(entity instanceof Enemy)
                        {
                            //
                            List<Entity> players = world.getEntities(Player.class);
                            if(players != null)
                            {
                                if(players.size() == 1)
                                {
                                    Entity player = world.getEntities(Player.class).get(0); 
                            
                                    if(player != null)
                                    {
                                       bullet = shootAtPlayer(positionPart.getX(), positionPart.getY(), entity, player, shootingPart.getID()); 
                                    }  
                                }
                            }
                        }
                        
                        shootingPart.setIsShooting(false);
                    
                        if(bullet != null)
                        {
                            world.addEntity(bullet);
                        }
                    }
                }
            }
        }

        for (Entity b : world.getEntities(Projectile.class)) {
            PositionPart ppb = b.getPart(PositionPart.class);
            MovingPart mpb = b.getPart(MovingPart.class);
            TimerPart btp = b.getPart(TimerPart.class);
            mpb.setUp(true);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = b.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0) {
                world.removeEntity(b);
            }

            ppb.process(gameData, b);
            mpb.process(gameData, b);
            btp.process(gameData, b);
            lpb.process(gameData, b);
        }
    }

    //Could potentially do some shenanigans with differing colours for differing sources.
    private Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Projectile();

        b.add(new PositionPart(x, y, radians));
        b.add(new MovingPart(0, 5000, 800, 0));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(2);
        b.setSprite(imageUrl);
        b.setSpawnSound(soundUrl); 
        
        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        b.setColour(colour);

        return b;
    }
    
    private Entity shootAtPlayer(double x, double y, Entity enemy, Entity player, String id)
    {
        Entity b = new Projectile();
        
        b.add(new PositionPart((float)x, (float)y, (float)getRadians(enemy, player)));
        b.add(new MovingPart(0, 2000, 200, 0));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        b.add(new ProjectilePart(id.toString()));
        b.setRadius(2);
        b.setSprite(imageUrl);
        b.setSpawnSound(soundUrl);
        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        b.setColour(colour);

        return b;
        
    }
    
    private double getRadians(Entity enemy, Entity player)
    {
        //Check if player coordinates align with enemy coordinates on four of the different angles
        PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);
        SizePart enemySizePart = enemy.getPart(SizePart.class);
        PositionPart playerPositionPart = player.getPart(PositionPart.class);
        SizePart playerSizePart = player.getPart(SizePart.class);
        
        double playerWidth = 40;
        double playerHeight = 40;
        double enemyWidth = 40;
        double enemyHeight = 40;
        
        if(enemySizePart != null)
        {
            enemyHeight = enemySizePart.getHeight();
            enemyWidth = enemySizePart.getWidth();
        }
        if(playerSizePart != null)
        {
            playerWidth = playerSizePart.getWidth();
            playerHeight = playerSizePart.getHeight();
        }
        
        double playerX = playerPositionPart.getX() + playerWidth / 2;
        double playerY = playerPositionPart.getY() + playerHeight / 2;
        
        double enemyX = enemyPositionPart.getX() + enemyWidth / 2;
        double enemyY = enemyPositionPart.getY() + enemyHeight / 2;
        
        //On the exact same x-coordinate check if the player is below or above the enemy
        if(playerX < enemyX && playerX > enemyX) 
        {
            if(playerY > enemyY)
            {
                return Math.PI / 2;
            }
            else
            {
                return Math.PI * 1.5;
            }
        }
        
        //On the exact same y-coordinate check if the player is to the left or the right of the enemy
        if(playerY < enemyY && playerY > enemyY)
        {
            if(playerX > enemyX)
            {
                return 0;
            }
            else
            {
                return Math.PI;
            }
        }
        
        //The player is not on exact coordinates respective to the four angles.
        //Draw a rectangle from the enemy to the player, with a=enemy position, c=player position, b=90 degree angel.
        //ac=hypotunuse
        
        //The distance formula Sqrt(|(x2-x1)^2|+|(y2-y1)^2|)
        double xAC = Math.abs(playerX - (enemyX));
        double yAC = Math.abs(playerY - (enemyY));
        
        //Distance between enemy and player
        double ac = Math.sqrt((xAC * xAC) + (yAC * yAC));
        
        double radians = 0;
        
        double bX = 0;
        double bY = 0;
        double ab = 0;
        double bc = 0;
        
        double quadrantBuffer = 0;
        
        if(playerX > enemyX && playerY > enemyY)
        {
            //Uper right
            bX = playerX;
            bY = enemyY;
            ab = Math.abs(bX - (enemyX));
            bc = Math.abs(bY - (playerY));
            quadrantBuffer = 0;
        }
        
        if(playerX > enemyX && playerY < enemyY)
        {
            //Lower right
            bX = enemyX;
            bY = playerY;
            ab = Math.abs(bY - (enemyY));
            bc = Math.abs(bX - (playerX));
            quadrantBuffer = 270;
        }
        
        if(playerX < enemyX && playerY < enemyY)
        {
            //Lower left
            bX = playerX;
            bY = enemyY;
            ab = Math.abs(bX - (enemyX));
            bc = Math.abs(bY - (playerY));
            quadrantBuffer = 180;
        }
        
        if(playerX < enemyX && playerY > enemyY)
        {
            //Upper left
            bX = enemyX;
            bY = playerY;
            ab = Math.abs(bY - (enemyY));
            bc = Math.abs(bX - (playerX));
            quadrantBuffer = 90;
        }
        
        double cosA = (Math.pow(ab, 2) + Math.pow(ac, 2) - Math.pow(bc, 2))/(2*ab*ac);
        double cosInverse = Math.acos(cosA);
        double realAngle = (cosInverse * 180 / Math.PI) + quadrantBuffer;

        return realAngle * Math.PI / 180;
    }
}
