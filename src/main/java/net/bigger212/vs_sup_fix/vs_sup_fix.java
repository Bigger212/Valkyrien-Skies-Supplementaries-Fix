package net.bigger212.vs_sup_fix;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(vs_sup_fix.MODID)
public class vs_sup_fix {

    public static final String MODID = "vs_sup_fix";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean DAMAGE_SHIPS_UNIQUELY  = false;
    public static double CANNONBALL_BREAK_RADIUS = 1.1;

    private static final File CONFIG_FILE = new File("config/vs2+sup-cannon-fix.properties");
    private static final java.util.Properties CONFIG = new java.util.Properties();

    public vs_sup_fix() {
        LOGGER.info("Initializing vs_sup_fix");
        registerConfigs();
    }

    public static void registerConfigs() {
        if (!CONFIG_FILE.exists()) {
            try {
                CONFIG_FILE.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                    writer.write("# VS+Supplementaries Cannonball Config\n");
                    writer.write("# In order to stay minimal; this mod only alters parts of Supplementaries which are necessary to enable detection of VS ships.\n");
                    writer.write("# Those parts partially expose the cannonball break radius, and so I have made it configureable.\n#\n");

                    writer.write("# You can rely on the config; 'supplementaries-common.json' to work while 'DAMAGE_SHIPS_UNIQUELY' is false.\n");
                    writer.write("# If you want the cannonball break radius on ships to only use this config; set 'DAMAGE_SHIPS_UNIQUELY' to true.\n");
                    writer.write("# Supplementaries only supports values of 1.1, 0.0, or 10.0. \n\n");

                    writer.write("DAMAGE_SHIPS_UNIQUELY=" + DAMAGE_SHIPS_UNIQUELY + "\n");
                    writer.write("CANNONBALL_BREAK_RADIUS=" + CANNONBALL_BREAK_RADIUS + "\n");
                }
            } catch (IOException e) {
                System.err.println("Failed to create vs_sup_fix config: " + e);
            }
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            CONFIG.load(reader);
            DAMAGE_SHIPS_UNIQUELY = Boolean.parseBoolean(CONFIG.getProperty("DAMAGE_SHIPS_UNIQUELY", "false"));
            CANNONBALL_BREAK_RADIUS = Double.parseDouble(CONFIG.getProperty("CANNONBALL_BREAK_RADIUS", "1.1"));
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load vs_sup_fix config: " + e);
        }
    }
}
