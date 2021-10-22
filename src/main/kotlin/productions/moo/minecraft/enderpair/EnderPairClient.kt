package productions.moo.minecraft.enderpair

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import productions.moo.minecraft.enderpair.EnderPair.PAIRED_CHEST_BLOCK_ENTITY

class EnderPairClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererRegistry.register(PAIRED_CHEST_BLOCK_ENTITY, PairedChestBlockEntityRenderer())
    }


}
