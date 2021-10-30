package productions.moo.minecraft.enderpair

import PairedChestItem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object EnderPair : ModInitializer {
    private const val MOD_ID = "ender_pair"
    val PAIRED_CHEST_IDENTIFIER: Identifier = Identifier(MOD_ID, "paired_chest")
    val PAIRED_CHEST_BLOCK = Registry.register(Registry.BLOCK, PAIRED_CHEST_IDENTIFIER, PairedChestBlock(FabricBlockSettings.copyOf(Blocks.ENDER_CHEST)))
    val PAIRED_CHEST_ITEM = Registry.register(Registry.ITEM, PAIRED_CHEST_IDENTIFIER, PairedChestItem(PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC)))
    val PAIRED_CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PAIRED_CHEST_IDENTIFIER, FabricBlockEntityTypeBuilder.create(PAIRED_CHEST_BLOCK).build(null))
    @kotlin.jvm.JvmField
    val PAIRED_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(EnderPair.PAIRED_CHEST_IDENTIFIER) { syncId: Int, playerInventory: PlayerInventory ->
        PairedChestScreenHandler(syncId, playerInventory)
    }

    override fun onInitialize() {

    }
}
