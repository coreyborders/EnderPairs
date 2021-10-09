package productions.moo.minecraft.enderpair

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object EnderPair : ModInitializer {
    private const val MOD_ID = "ender_pair"

    val PAIRED_CHEST = Identifier(MOD_ID, "paired_chest")

    val PAIRED_CHEST_BLOCK = PairedChestBlock(FabricBlockSettings.of(Material.METAL).requiresTool())

    override fun onInitialize() {
        Registry.register(Registry.BLOCK, Identifier(MOD_ID, "paired_chest"), PAIRED_CHEST_BLOCK)
        println("Ender Pairs has been initialized.")
    }
}