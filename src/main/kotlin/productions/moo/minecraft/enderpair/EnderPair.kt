package productions.moo.minecraft.enderpair

import PairedChestItem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Material
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object EnderPair : ModInitializer {
    private const val MOD_ID = "ender_pair"
    val PAIRED_CHEST_BLOCK = PairedChestBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f))
    val PAIRED_CHEST_ITEM = PairedChestItem(PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC))

    override fun onInitialize() {
        Registry.register(Registry.BLOCK, Identifier(MOD_ID, "paired_chest"), PAIRED_CHEST_BLOCK)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "paired_chest"), PAIRED_CHEST_ITEM)
    }
}