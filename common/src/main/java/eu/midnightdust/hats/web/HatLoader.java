package eu.midnightdust.hats.web;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class HatLoader {
    public static final System.Logger logger = System.getLogger("MidnightLib");
    private final static String HATS_URL = "https://raw.githubusercontent.com/TeamMidnightDust/MidnightHats/master/hats.json";
    public static final Type HAT_TYPE = new TypeToken<Map<UUID, PlayerHatData>>(){}.getType();
    public static Map<UUID, PlayerHatData> PLAYER_HATS;
    private static final Gson GSON = new GsonBuilder().create();


    public static void init() {
        CompletableFuture.supplyAsync(() -> {
            try (Reader reader = new InputStreamReader(new URL(HATS_URL).openStream())) {
                return GSON.<Map<UUID, PlayerHatData>>fromJson(reader, HAT_TYPE);
            } catch (MalformedURLException error) {
                logger.log(System.Logger.Level.ERROR, "Unable to load player hats because of connection problems: " + error.getMessage());
            } catch (IOException error) {
                logger.log(System.Logger.Level.ERROR, "Unable to load player hats because of an I/O Exception: " + error.getMessage());
            }

            return null;
        }).thenAcceptAsync(playerData -> {
            if (playerData != null) {
                PLAYER_HATS = playerData;
                System.out.println("(MidnightLib) Player hats successfully loaded!");
            } else {
                PLAYER_HATS = Collections.emptyMap();
                logger.log(System.Logger.Level.WARNING, "A problem with the database occurred, the hats could not be initialized.");
            }
        }, MinecraftClient.getInstance());
    }
}
