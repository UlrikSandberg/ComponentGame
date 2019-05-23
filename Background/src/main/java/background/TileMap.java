/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;
import java.io.File;

/**
 *
 * @author ulriksandberg
 */
public class TileMap {
    private int tilesheight;
    private int tileswidth;
    private Tile[] tilemap;

    private int coloumn = 2;
    private int row = 2;
    
    public TileMap(int mapWidth, int mapHeight)
    {
        FactorClass scaleFactor = GetScaleFactor(mapWidth, mapHeight);
        
        coloumn = scaleFactor.getWidthScaleFactor();
        row = scaleFactor.getHeightScaleFactor();
        
        tilemap = new Tile[coloumn * row];
        
        Integer index = 0;
        
        for(int i = 0; i < coloumn; i++)
        {
            for(int j = 0; j < row; j++)
            {
                Tile tile = new Tile(new File("").getAbsolutePath() + "/Background/target/Background-1.0-SNAPSHOT.jar!/assets/images/SpaceBackground.jpg");
                tile.setId(index.toString());
                tile.setHeight(mapHeight / row);
                tile.setWidth(mapWidth / coloumn);
                tile.setPositionX(i * (mapWidth / coloumn));
                tile.setPositionY(j * (mapHeight / row));
                
                tilemap[index] = tile;
                index++;
            }
        }
    }
    
    private FactorClass GetScaleFactor(int mapWidth, int mapHeight)
    {
        double originalWidthScaleFactor = 1920 * 0.85;
        double originalHeightScaleFActor = 1080 * 0.85;
        
        int widthScaleFactor = (int)( mapWidth /originalWidthScaleFactor);
        int heightScaleFactor = (int)(mapHeight / originalHeightScaleFActor);
        
        if(widthScaleFactor < 1)
        {
            widthScaleFactor = 1;
        }
        
        if(heightScaleFactor < 1)
        {
            heightScaleFactor = 1;
        }
        
        FactorClass fClass = new FactorClass();
        
        fClass.setHeightScaleFactor(heightScaleFactor);
        fClass.setWidthScaleFactor(widthScaleFactor);
        
        return fClass;
    }
    
    
    public Tile[] getMap()
    {
        return tilemap;
    }
}
