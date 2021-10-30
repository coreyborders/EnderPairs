package productions.moo.minecraft.wrench

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object TurningWrench : ModInitializer {
    private const val MOD_ID = "wrench"
    val WRENCH_ITEM = TurningWrenchItem(FabricItemSettings().group(ItemGroup.MISC))

    override fun onInitialize() {
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "turning_wrench"), WRENCH_ITEM)
    }
}