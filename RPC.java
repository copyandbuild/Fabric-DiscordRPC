package studios.bluemoon;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import  net.minecraft.client.multiplayer.ClientLevel;

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
            params.setClientID(1259424712644366416L);
            params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
            ACTIVITY.timestamps().setStart(Instant.now());
            try (final Core core = new Core(params)) {
                while (true) {
                    if (Minecraft.getInstance().isLocalServer()) {
                        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21");
                        ACTIVITY.assets().setLargeImage("large");
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName());
                        ACTIVITY.setDetails("In Singleplayer");
                        ACTIVITY.setState("with BMC-1.21");
                    } else if (isOnMultiplayerServer()) {
                        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21");
                        ACTIVITY.assets().setLargeImage("large");
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName());
                        ACTIVITY.setDetails("In Multiplayer");
                        ACTIVITY.setState("with BMC-1.21");
                    } else {
                        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21");
                        ACTIVITY.assets().setLargeImage("large");
                        updatePlayerHead();
                        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName());
                        ACTIVITY.setDetails("In Mainmenu");
                        ACTIVITY.setState("with BMC-1.21");
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
