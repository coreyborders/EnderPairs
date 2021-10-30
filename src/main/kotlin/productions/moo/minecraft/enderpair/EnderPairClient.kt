package productions.moo.minecraft.enderpair

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class EnderPairClient : ClientModInitializer {
    override fun onInitializeClient() {
        ScreenRegistry.register(EnderPair.PAIRED_CHEST_SCREEN_HANDLER) { handler: PairedChestScreenHandler, playerInventory: PlayerInventory, title: Text ->
            PairedChestScreen(handler, playerInventory, title)
        }
    }
}