package pl.xxiiliixx.Main.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import pl.xxiiliixx.Main.Managers.ConfigManager;

import java.awt.*;

public class SetupTicketsCommand extends ListenerAdapter {

    public static ConfigManager configManager;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        String message = event.getMessage().getContentDisplay().trim();
        User user = event.getAuthor();

        String[] command = message.split(" ", 3);

        if (command[0].equalsIgnoreCase("//setup-tickets")) {
            if (isAdmin(event.getMember()) || event.getMember().isOwner()) {
                if (command.length == 3) {
                        EmbedBuilder e = new EmbedBuilder();

                        e.setTitle(command[1]);
                        e.setDescription(command[2]);
                        e.setColor(Color.BLUE);

                        Button button = Button.primary("open_ticket", "OpenTicket");

                        channel.sendMessageEmbeds(e.build()).setActionRow(button).queue();

                } else {
                    channel.sendMessage("Usage: **//setup-tickets <title> <description>**").queue();
                }

            } else {
                channel.sendMessage("You don't have permission to do that...").queue();
            }
            event.getMessage().delete().queue();
        }

    }

    private boolean isAdmin(Member member) {
        for (Role role : member.getRoles()) {
            if (role.hasPermission(Permission.ADMINISTRATOR)) {
                return true;
            }

        }

        return false;
    }

}
