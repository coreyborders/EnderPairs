package productions.moo.minecraft.enderpair;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class EnderPairClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(EnderPair.PAIRED_CHEST_SCREEN_HANDLER, PairedChestScreen::new);
    }
}