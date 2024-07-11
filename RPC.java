package dev.larrox;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import net.minecraft.client.multiplayer.ClientLevel;

import java.time.Instant;

public class RPC {

    private static final Activity ACTIVITY = new Activity();
    
    public static boolean isOnMultiplayerServer() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel world = minecraft.level;
        return world != null && !minecraft.isSingleplayer();
    }

    public static void start() {
        new Thread(() -> {
            final CreateParams params = new CreateParams();
            params.setClientID(_YOUCLIENTID_L); // PLEASE KEEP THE 'L' AND ONLY REPLACE '_YOURCLIENTID_'
            params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
            ACTIVITY.timestamps().setStart(Instant.now());
            try (final Core core = new Core(params)) {
                while (true) {
                    if (Minecraft.getInstance().isLocalServer()) {
                        ACTIVITY.assets().setLargeText("Hovertext for large image"); // HERE YOU CAN CHANGE THE HOVER TEXT FOR THE LARGE/MAIN IMAGE FOR SINGLEPLAYER
                        ACTIVITY.assets().setLargeImage("large"); // HERE YOU CAN CHANGE THE NAME OF THE LARGE/MAIN IMAGE FOR SINGLEPLAYER (change it to that you have named it in discord's dev portal)
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName()); // HERE YOU CAN CHANGE THE HOVER TEXT FOR THE SMALL IMAGE FOR SINGLEPLAYER
                        ACTIVITY.setDetails("In Singleplayer"); // HER YOU CAN CHANGE THE FIRST TEXT WHEN THE PLAYER IS IN SINGLEPLAYER
                        ACTIVITY.setState("with BMC-1.21"); // HER YOU CAN CHANGE THE SECOND TEXT WHEN THE PLAYER IS IN SINGLEPLAYER
                    } else if (isOnMultiplayerServer()) {
                        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21"); // HERE YOU CAN CHANGE THE HOVER TEXT FOR THE LARGE/MAIN IMAGE FOR MULTIPLAYER
                        ACTIVITY.assets().setLargeImage("large"); // HERE YOU CAN CHANGE THE NAME OF THE LARGE/MAIN IMAGE FOR MULTIPLAYER (change it to that you have named it in discord's dev portal)
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName()); // HERE YOU CAN CHANGE THE HOVER TEXT FOR THE SMALL IMAGE FOR MULTIPLAYER
                        ACTIVITY.setDetails("In Multiplayer"); // HER YOU CAN CHANGE THE FIRST TEXT WHEN THE PLAYER IS IN MULTIPLAYER
                        ACTIVITY.setState("with BMC-1.21"); // HER YOU CAN CHANGE THE SECOND TEXT WHEN THE PLAYER IS IN MULTIPLAYER
                    } else {
                        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21"); // HERE YOU CAN CHANGE THE HOVER TEXT FOR THE LARGE/MAIN IMAGE FOR THE MAIN MENU / TITLESCREEN
                        ACTIVITY.assets().setLargeImage("large"); // HERE YOU CAN CHANGE THE NAME OF THE LARGE/MAIN IMAGE FOR THE TITLE SCREEN (change it to that you have named it in discord's dev portal)
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName());
                        ACTIVITY.setDetails("In Mainmenu"); // HER YOU CAN CHANGE THE FIRST TEXT WHEN THE PLAYER IS IN MULTIPLAYER
                        ACTIVITY.setState("with BMC-1.21"); // HER YOU CAN CHANGE THE SECOND TEXT WHEN THE PLAYER IS IN MULTIPLAYER
                    }
                    core.activityManager().updateActivity(ACTIVITY);

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        }).start();
    }

// IF YOU DONT USE updatePlayerHead THEN YOU DONT NEED THESE FROM HERE
    private static void updatePlayerHead() {
        String uuid = Minecraft.getInstance().getGameProfile().getId().toString();
        String playerHeadImage = getPlayerHeadURL(uuid, "head", 3);
        ACTIVITY.assets().setSmallImage(playerHeadImage);
    }

    @Contract(pure = true)
    private static @NotNull String getPlayerHeadURL(String uuid, String type, int size) {
        return "https://api.mineatar.io/" + type + "/" + uuid + "?scale=" + size;
    }
}
