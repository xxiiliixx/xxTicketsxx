package pl.xxiiliixx.Main.Managers;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private Properties config = new Properties();
    private Gson gson = new Gson();

    public void OpenConfig() {
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            config.load(fileInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void SaveConfig() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("config.properties")) {
            config.store(fileOutputStream,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getTicket(String channelId) {
        return config.getProperty(channelId);
    }

    public String getTicketByUserId(String userId) {
        for (String key : config.stringPropertyNames()) {
            if (config.getProperty(key).equals(userId)) {
                return key;
            }

        }

        return null;
    }

    public boolean isOpenTicket(String channelId) {
        if (config.containsKey(channelId)) {
            return true;
        }
        return false;
    }

    public String getTicketsCategory() {
        return config.getProperty("tickets_category");
    }

    public String getArchives() {
        return config.getProperty("archives_id");
    }

    public void addTicket(String channelId, String userId) {
        config.setProperty(channelId, userId);
        SaveConfig();
    }

    public void RemoveTicket(String userId) {
        if (getTicket(userId) != null) {
            config.remove(userId);
            SaveConfig();
        } else {
            System.out.println("[xxTicketsxx] Cannot remove " + userId + " from config.properties");
        }

    }

}
