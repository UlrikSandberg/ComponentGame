package services;

import data.GameData;
import data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
