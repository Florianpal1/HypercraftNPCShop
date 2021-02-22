package fr.florianpal.hypercraftnpcshop.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import fr.florianpal.hypercraftnpcshop.HypercraftNPCShop;
import org.bukkit.entity.Player;

@CommandAlias("npcshop")
public class NPCShopCommand extends BaseCommand {
    private HypercraftNPCShop plugin;

    public NPCShopCommand(HypercraftNPCShop plugin) {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @CommandPermission("hc.npcshop.reload")
    @Description("{@@hypercraft.event_effect_saturation_help_description}")
    public void onReload(Player playerSender){
        plugin.reloadConfig();
    }
}
