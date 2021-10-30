package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.ChestBlockEntity
import java.util.function.Supplier

class PairedChestBlock : ChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST), Supplier<BlockEntityType<out ChestBlockEntity>> { BlockEntityType.CHEST })