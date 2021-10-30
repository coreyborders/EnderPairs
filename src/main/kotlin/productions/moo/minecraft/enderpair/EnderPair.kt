package productions.moo.minecraft.enderpair

import PairedChestItem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object EnderPair : ModInitializer {
    private const val MOD_ID = "ender_pair"
    val PAIRED_CHEST_IDENTIFIER: Identifier = Identifier(MOD_ID, "paired_chest")
    val PAIRED_CHEST_BLOCK = PairedChestBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f))
    val PAIRED_CHEST_TRANSLATION_KEY = Util.createTranslationKey("container", PAIRED_CHEST_IDENTIFIER)
    val PAIRED_CHEST_ITEM = PairedChestItem(PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC))
    val PAIRED_CHEST_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create<PairedChestBlockEntity>(PAIRED_CHEST_BLOCK).build(null)
    lateinit var PAIRED_CHEST_BLOCK_ENTITY_TYPE: BlockEntityType<PairedChestBlockEntity>

    override fun onInitialize() {
        Registry.register(Registry.BLOCK, PAIRED_CHEST_IDENTIFIER, PAIRED_CHEST_BLOCK)
        Registry.register(Registry.ITEM, PAIRED_CHEST_IDENTIFIER, PAIRED_CHEST_ITEM)
        PAIRED_CHEST_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(MOD_ID, "paired_chest_block_entity"),
            PAIRED_CHEST_BLOCK_ENTITY
        )

        ScreenHandlerRegistry.registerExtended(EnderPair.PAIRED_CHEST_IDENTIFIER)
        { syncId, player, buf ->
           PairedChestBlockEntity.createScreenHandler(syncId, player, buf)
        }

//        ContainerProviderRegistry.INSTANCE.registerFactory(PAIRED_CHEST_IDENTIFIER) { syncId, identifier, player, buf ->
//            val world = player.world
//            val pos = buf.readBlockPos()
//            world.getBlockState(pos).createScreenHandlerFactory(player.world, pos)!!
//                .createMenu(syncId, player.inventory, player)
//        }
    }
}
