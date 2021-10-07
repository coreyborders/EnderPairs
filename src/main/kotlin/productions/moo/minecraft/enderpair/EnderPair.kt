package productions.moo.minecraft.enderpair

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object EnderPair: ModInitializer {
    private const val MOD_ID = "ender_pair"

    val PAIRED_CHEST_ITEM = PairedChestItem(FabricItemSettings().group(ItemGroup.MISC).maxCount(2))

    override fun onInitialize() {
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "paired_chest"), PAIRED_CHEST_ITEM)
        println("Ender Pairs has been initialized.")
    }
}