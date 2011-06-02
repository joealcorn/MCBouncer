package com.mcbouncer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MCBouncerConfig {

    private static String apiKey = "";
    private static int numBansDisallow = 10;
    private static boolean showBanMessages = true;
    private static String defaultBanMessage = "Banned for rule violation.";


    public MCBouncerConfig(File folder) {
        Properties configFile = new Properties();
        try {
            File f = new File(folder.getPath() + "/config.properties");
            if (f.exists()) {
                configFile.load(this.getClass().getClassLoader().getResourceAsStream(folder.getPath() + "/config.properties"));
                configFile.load(this.getClass().getClassLoader().getResourceAsStream("/my_config.properties"));
                MCBouncerConfig.apiKey = configFile.getProperty("apiKey");
                MCBouncerConfig.numBansDisallow = Integer.valueOf(configFile.getProperty("numBansDisallow"));
                MCBouncerConfig.showBanMessages = configFile.getProperty("showBanMessages").toLowerCase().contains("true");
                MCBouncerConfig.defaultBanMessage = configFile.getProperty("defaultBanMessage");
            } else {
                f.createNewFile();
                FileWriter fstream = new FileWriter(folder.getPath() + "/config.properties");
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("# Replace this with your API key from mcbouncer.com/apikey\napiKey:REPLACE\nnumBansDisallow:10\nshowBanMessages:true\ndefaultBanMessage:Banned for rule violation.");
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MCBouncerConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static int getNumBansDisallow() {
        return numBansDisallow;
    }

    public static boolean isShowBanMessages() {
        return showBanMessages;
    }

    public static String getDefaultReason() {
        return defaultBanMessage;
    }
}