package productions.moo.minecraft.enderpair

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.text.Text

class EnderPairClient : ClientModInitializer {
    override fun onInitializeClient() {
        ScreenRegistry.register(EnderPair.PAIRED_CHEST_SCREEN_HANDLER) { handler: GenericContainerScreenHandler, playerInventory: PlayerInventory, title: Text ->
            GenericContainerScreen(handler, playerInventory, title)
        }

        BlockEntityRendererRegistry.register(EnderPair.PAIRED_CHEST_TYPE) { context -> PairedChestBlockEntityRenderer(context) }
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register {
            _, registry -> registry.register(PairedChestBlockEntityRenderer.PAIRED_CHEST_TEXTURE)
        }
    }
}
