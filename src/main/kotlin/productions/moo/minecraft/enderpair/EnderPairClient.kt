package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry

class EnderPairClient {
    fun onInitializeClient() {
        ScreenHandlerRegistry.registerExtended(EnderPair.PAIRED_CHEST_IDENTIFIER)
        { syncId, player, buf ->
            val world = player.player.world
            val pos = buf.readBlockPos()
            world.getBlockState(pos).createScreenHandlerFactory(player.player.world, pos)!!
                .createMenu(syncId, player.player.inventory, player.player)
        }
    }
}