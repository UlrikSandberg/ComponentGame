package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static GameEngine g;
    private static GameInitializer gi;
    

    @Override
    public void restored() {

         gi = new GameInitializer();
         
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Space: Endgame";
        cfg.width = 1000;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(gi , cfg);
    }
}
