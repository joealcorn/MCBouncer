package com.mcbouncer.bukkit;

import com.mcbouncer.ConfigurationDefaults;
import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.node.YAMLFileNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of the LocalConfiguration interface. 
 * It uses YAML to load in a config.yml file, but it
 * extends MapNode as well, so standard node accessors
 * can be used on it.
 * 
 * @author sk89q
 */
public class BukkitConfiguration extends LocalConfiguration {

    protected File dataFolder;

    public BukkitConfiguration(File dataFolder) {
        this.dataFolder = dataFolder;
    }
    
    /**
     * Loads the config.yml file. If it doesn't
     * exist, it copies the file in the defaults/
     * folder in the jar to the right location
     * 
     */
    @Override
    public void load() {
        
        dataFolder.getParentFile().mkdirs();
        File file = new File(dataFolder, "config.yml");
        
        if (!file.exists()) {
            InputStream input = MCBouncer.class.getResourceAsStream("/defaults/config.yml");
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(file);
                    byte[] buf = new byte[8192];
                    int length = 0;
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {
                    }

                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }

        }
        
        YAMLFileNode node = new YAMLFileNode(file);
        node.load();
        this.conf = node;
        
        debugMode = conf.getBoolean("debug", ConfigurationDefaults.DEBUG.getBoolVal());
        apiKey = conf.getString("apiKey", ConfigurationDefaults.APIKEY.getStrVal());
        numBansDisallow = conf.getInteger("numBansDisallow", ConfigurationDefaults.BANSDISALLOW.getIntVal());
        showBanMessages = conf.getBoolean("showBanMessages", ConfigurationDefaults.SHOWMESSAGES.getBoolVal());
        defaultReason = conf.getString("defaultBanMessage", ConfigurationDefaults.DEFAULTBAN.getStrVal());
        defaultKickMessage = conf.getString("defaultKickMessage", ConfigurationDefaults.DEFAULTKICK.getStrVal());
        website = conf.getString("website", ConfigurationDefaults.WEBSITE.getStrVal());
        
    }

    @Override
    public String getAPIKey() {
        return apiKey;
    }

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public String getDefaultKickReason() {
        return defaultKickMessage;
    }

    @Override
    public String getDefaultReason() {
        return defaultReason;
    }

    @Override
    public int getNumBansDisallow() {
        return numBansDisallow;
    }

    @Override
    public boolean isShowBanMessages() {
        return showBanMessages;
    }

    @Override
    public String getWebsite() {
        return website;
    }
    
}
