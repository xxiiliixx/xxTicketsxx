package pl.xxiiliixx.Main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.xxiiliixx.Main.Buttons.OpenTickets;
import pl.xxiiliixx.Main.Commands.GitHubCommand;
import pl.xxiiliixx.Main.Commands.SetupTicketsCommand;
import pl.xxiiliixx.Main.Commands.TicketCommands;
import pl.xxiiliixx.Main.Listeners.CommandListener;
import pl.xxiiliixx.Main.Managers.ConfigBuild;
import pl.xxiiliixx.Main.Managers.ConfigManager;

import javax.security.auth.login.LoginException;

public class Bot {

    public static void main(String[] args) throws LoginException {
        System.out.println("[xxTicketsxx] Building...");

        JDABuilder builder = JDABuilder.createDefault(":)");
        builder.setStatus(OnlineStatus.ONLINE);

        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);

        // events
        SetupTicketsCommand setupTicketsCommand = new SetupTicketsCommand();
        OpenTickets openTickets = new OpenTickets();
        TicketCommands ticketCommands = new TicketCommands();
        GitHubCommand gitHubCommand = new GitHubCommand();

        builder.addEventListeners(setupTicketsCommand);
        builder.addEventListeners(openTickets);
        builder.addEventListeners(ticketCommands);
        builder.addEventListeners(gitHubCommand);

        JDA jda = builder.build();
        TicketCommands.jda = jda;

        // ConfigBuild
        ConfigBuild configBuild = new ConfigBuild();
        configBuild.Build();

        // ConfigManager
        ConfigManager configManager = new ConfigManager();
        configManager.OpenConfig();
        setupTicketsCommand.configManager = configManager;
        openTickets.configManager = configManager;
        ticketCommands.configManager = configManager;

        // CommandListener
        CommandListener commandListener = new CommandListener(jda, configManager);
        Thread thread = new Thread(commandListener);
        thread.start();

        System.out.println("[xxTicketsxx] Bot has been built successfully");

    }

}
