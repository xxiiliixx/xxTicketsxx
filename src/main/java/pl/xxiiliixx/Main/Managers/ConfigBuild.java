package pl.xxiiliixx.Main.Managers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ConfigBuild {

    public void Build() {
        File file = new File("config.properties");

        try {
            if (file.createNewFile()) {
                System.out.println("[xxTicketsxx] Creating config.properties");
                InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.println("[xxTicketsxx] config.properties has been found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
