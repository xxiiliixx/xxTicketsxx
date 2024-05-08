package pl.xxiiliixx.Main.Listeners;

import net.dv8tion.jda.api.JDA;
import pl.xxiiliixx.Main.Managers.ConfigManager;

import java.util.Scanner;

public class CommandListener implements Runnable {

    private JDA jda;
    private ConfigManager configManager;

    public CommandListener(JDA jda, ConfigManager configManager) {
        this.jda = jda;
        this.configManager = configManager;
    }

    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("stop")) {
                stop();
            }

        }

        jda.shutdown();
        configManager.SaveConfig();
        System.out.println("[xxTicketsxx] config.properties has been saved");
        scanner.close();
        System.out.println("[xxTicketsxx] Shutdown... ");
        System.exit(0);

    }

}
