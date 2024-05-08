package pl.xxiiliixx.Main.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.xxiiliixx.Main.Managers.ConfigManager;

public class TicketCommands extends ListenerAdapter {

    public static ConfigManager configManager;
    public static JDA jda;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        String message = event.getMessage().getContentDisplay().trim();

        String[] command = message.split(" ");

        if (command[0].equalsIgnoreCase("//ticket")) {
            if (isAdmin(event.getMember()) || event.getMember().isOwner()) {
                if (command[1].equalsIgnoreCase("remove")) {
                    if (configManager.isOpenTicket(channel.getId())) {
                        configManager.RemoveTicket(channel.getId());
                        channel.delete().queue();
                        System.out.println("[xxTicketsxx] Ticket removed");
                    }

                } else if (command[1].equalsIgnoreCase("archive")) {
                    if (configManager.isOpenTicket(channel.getId())) {

                        Category category = event.getGuild().getCategoryById(configManager.getArchives());
                        channel.getManager().setParent(category).queue();

                        configManager.RemoveTicket(channel.getId());

                        System.out.println("[xxTicketsxx] Ticket archived");
                    }

                }

            } else {
                channel.sendMessage("You don't have permissions to do that").queue();
            }

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
