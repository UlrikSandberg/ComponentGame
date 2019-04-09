/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author casperhasnsen
 */
public class Assets {
    
     AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        
    private AssetManager assetManager = new AssetManager(jfhr);
    public static final AssetDescriptor<Texture> someTexture = 
        new AssetDescriptor<Texture>("images/sometexture.png", Texture.class);
  
   
        
        public void load() {
            assetManager.load(someTexture);
            
        
    }
        
        public void dispose() {
            assetManager.dispose();
        }
            
            
   
    
}
