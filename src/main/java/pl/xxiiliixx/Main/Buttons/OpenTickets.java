package pl.xxiiliixx.Main.Buttons;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.xxiiliixx.Main.Managers.ConfigManager;

import java.util.EnumSet;

public class OpenTickets extends ListenerAdapter {

    public static ConfigManager configManager;

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        User user = event.getUser();
        if (event.getButton().getId().equals("open_ticket")) {
            if (configManager.getTicketByUserId(user.getId()) == null) {
                Category category = event.getGuild().getCategoryById(configManager.getTicketsCategory());
                event.getGuild().createTextChannel(event.getUser().getName() + " ticket")
                        .setParent(category)
                        .queue(textChannel -> {
                            configManager.addTicket(textChannel.getId(), user.getId());
                            MakeChannelPrivate(textChannel);
                            textChannel.upsertPermissionOverride(event.getMember()).setPermissions(EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null).queue();
                            textChannel.sendMessage("@everyone").queue(message -> {
                                message.delete().queue();
                            });
                        });

                event.reply("Your ticket has been created...").setEphemeral(true).queue();

            } else {
                event.reply("You already have a ticket").setEphemeral(true).queue();
            }

        }

    }

    private void MakeChannelPrivate(TextChannel channel) {
        for (Role role : channel.getGuild().getRoles()) {
            if (!role.hasPermission(Permission.ADMINISTRATOR)) {
                channel.upsertPermissionOverride(role).deny(Permission.VIEW_CHANNEL).queue();
            }

        }

    }

}
