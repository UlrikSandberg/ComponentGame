package dk.sdu.mmmi.cbse.core.main;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.NonEntity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.AssetsJarFileResolver;
import dk.sdu.mmmi.cbse.core.managers.CameraManager;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.geometry.Pos;
import org.lwjgl.util.vector.Matrix;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private SpriteBatch batch;
    private Graphics2D g2d;
    private Sprite sprite;
    private Sprite Astsprite;
    private Music music;
    
    private AssetManager assetManager;
    
    private CameraManager cameraManager;

   
    private static final int GAME_HEIGHT = 5000;
    private static final int GAME_WIDTH = 8000;
    
    
    @Override
    public void create() {
        gameData.setDisplayWidth(GAME_WIDTH);
        gameData.setDisplayHeight(GAME_HEIGHT);
        
        
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //cam.translate(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        //cam.setToOrtho(false , GAME_WIDTH , GAME_HEIGHT);
        cameraManager = new CameraManager();
        
        batch = new SpriteBatch();
        
        
        cam.update();

        sr = new ShapeRenderer();
        float x = gameData.getPlayerPositionX();
        float y = gameData.getPlayerPositionY();
        
        AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        assetManager = new AssetManager(jfhr);
        
        batch.begin();
        //sprite.setCenter(gameData.getPlayerPositionX(), gameData.getPlayerPositionY());
       

        batch.end();
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        
        
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            
            gamePlugins.add(plugin);
            
        }
        assetManager.load(new File("").getAbsolutePath() + "/Core/target/Core-1.0-SNAPSHOT.jar!/assets/images/SpaceSounds.mp3", Music.class);
        assetManager.finishLoading();
        music = assetManager.get(new File("").getAbsolutePath() + "/Core/target/Core-1.0-SNAPSHOT.jar!/assets/images/SpaceSounds.mp3", Music.class);
        music.setLooping(true);
        music.play();
        LoadAssets();
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        
      
        cam.update();
         batch.setProjectionMatrix(cam.combined);
   
        cameraManager.EdgeMovement(cam, gameData);
        
        update();

        batch.begin();
        //batch.setProjectionMatrix(cam.combined);
        DrawNonEntities();
        draw();
        batch.end();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        
        for (Entity entity : world.getEntities()) {
            
            if(entity.getPart(PositionPart.class) != null && entity.getSprite() != null){
                PositionPart part = entity.getPart(PositionPart.class);
                Sprite sprite = new Sprite(assetManager.get(entity.getSprite(), Texture.class) );
                
                sprite.setSize(40, 40);
               
        // sprite.setOrigin(x, y); 
               sprite.setOriginCenter();
       // sprite.setCenterX(x);
        //sprite.setCenterY(y);
                //Make spawn sound
                if(!entity.isIsSpawned())
                {
                    entity.setIsSpawned(true);
                    if(entity.getSpawnSound() != null)
                    {
                       Sound s = assetManager.get(entity.getSpawnSound(), Sound.class);
                       s.play();
                    }
                }
        
        
        sprite.flip(false, false);
        sprite.rotate90(true);
        sprite.setY(part.getY());
        sprite.setX(part.getX());
        sprite.setRotation(part.getRadians() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
                
            }

        }
    }
       

    public void DrawNonEntities()
    {
        for(NonEntity entity : world.getNonEntities())
        {
            if(!entity.isIsDrawn())
            {
                //Draw entities
                if(entity.getSprite() != null)
                {
                    Sprite sprite = new Sprite(assetManager.get(entity.getSprite(), Texture.class));
                    sprite.setSize(entity.getWidth(), entity.getHeight());
                    
                    sprite.setOriginCenter();
                    
                    sprite.flip(false, false);
                    sprite.rotate90(true);
                    sprite.setY(entity.getPositionY());
                    sprite.setX(entity.getPositionX());
                    sprite.draw(batch);
                }
            }
        }
    }
    
    private void LoadAssets()
    {
        for(Entity entity : world.getEntities())
        {
            if(entity.getSprite() != null)
            {
                assetManager.load(entity.getSprite(), Texture.class);
                
                assetManager.finishLoading();

                entity.setIsLoaded(true);
            }
            if(entity.getSpawnSound() != null)
            {
                assetManager.load(entity.getSpawnSound(), Sound.class);
                
                assetManager.finishLoading();
                
                entity.setIsSpawnSoundLoaded(true);
            }
        } 
        for(NonEntity entity : world.getNonEntities())
        {
            if(entity.getSprite() != null)
            {
                assetManager.load(entity.getSprite(), Texture.class);
                
                assetManager.finishLoading();
                
                entity.setIsLoaded(true);
            }
        }
    }
    
     
    private void UnloadAssets()
    {
        for(Entity entity : world.getEntities())
        {
            if(entity.getSprite() != null && entity.getIsLoaded())
            {
                assetManager.unload(entity.getSprite());
                entity.setIsLoaded(false);
            }
            if(entity.getSpawnSound() != null && entity.isIsSpawnSoundLoaded())
            {
                assetManager.unload(entity.getSpawnSound());
                entity.setIsSpawnSoundLoaded(false);
            }
        }
        for(NonEntity entity : world.getNonEntities())
        {
           if(entity.getSprite() != null && entity.isIsLoaded())
           {
               assetManager.unload(entity.getSprite());
               entity.setIsLoaded(false);
           }
        }
    }
    

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    LoadAssets();
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    UnloadAssets();
                    gamePlugins.remove(gs);
                }
            }
        }

    };
}
