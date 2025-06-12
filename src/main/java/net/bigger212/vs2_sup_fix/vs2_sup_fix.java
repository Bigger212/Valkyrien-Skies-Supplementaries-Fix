package net.bigger212.vs2_sup_fix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class vs2_sup_fix implements ModInitializer {
    public static final String MOD_ID = "vs2_sup_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean DAMAGE_SHIPS_UNIQUELY  = false;
    public static double CANNONBALL_BREAK_RADIUS = 1.1;

    private static final File CONFIG_FILE = new File("config/vs2+sup-cannon-fix.properties");
    private static final java.util.Properties CONFIG = new java.util.Properties();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing vs2_sup_fix");
        registerConfigs();
    }

    public static void registerConfigs() {
        if (!CONFIG_FILE.exists()) {
            try {
                CONFIG_FILE.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                    writer.write("# VS+Supplementaries Cannonball Config\n");
                    writer.write("# This config controls cannonball explosion behavior on ships\n#\n");
                    writer.write("# You can rely on the config; 'supplementaries-common.json' to work while 'DAMAGE_SHIPS_UNIQUELY' is false.\n");
                    writer.write("# If you want the explosion on ships to only use this config; set 'DAMAGE_SHIPS_UNIQUELY' to true.\n");
                    writer.write("# Supplementaries only supports values of 1.1, 0.0, or 10.0. \n\n");

                    writer.write("DAMAGE_SHIPS_UNIQUELY=" + DAMAGE_SHIPS_UNIQUELY + "\n");
                    writer.write("CANNONBALL_BREAK_RADIUS=" + CANNONBALL_BREAK_RADIUS + "\n");
                }
            } catch (IOException e) {
                System.err.println("Failed to create vs2_sup_fix config: " + e);
            }
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            CONFIG.load(reader);
            DAMAGE_SHIPS_UNIQUELY = Boolean.parseBoolean(CONFIG.getProperty("DAMAGE_SHIPS_UNIQUELY", "false"));
            CANNONBALL_BREAK_RADIUS = Double.parseDouble(CONFIG.getProperty("CANNONBALL_BREAK_RADIUS", "1.1"));
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load vs2_sup_fix config: " + e);
        }
    }
}
