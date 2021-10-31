package productions.moo.minecraft.enderpair

import PairedChestItem
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object EnderPair : ModInitializer {
    const val MOD_ID = "ender_pair"
    private val PAIRED_CHEST_IDENTIFIER: Identifier = Identifier(MOD_ID, "paired_chest")
    val PAIRED_CHEST_BLOCK = Registry.register(Registry.BLOCK, PAIRED_CHEST_IDENTIFIER, PairedChestBlock())!!
    val PAIRED_CHEST_ITEM = Registry.register(Registry.ITEM, PAIRED_CHEST_IDENTIFIER, PairedChestItem())!!

    override fun onInitialize() {
    }
}
