package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.ChestBlock
import net.minecraft.block.enums.ChestType
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class PairedChestBlock(settings: FabricBlockSettings) : ChestBlock(settings, { EnderPair.PAIRED_CHEST_TYPE }) {
    init {
        defaultState = stateManager.defaultState
            .with(Properties.CHEST_TYPE, ChestType.SINGLE)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return super.getPlacementState(ctx)!!
            .with(Properties.CHEST_TYPE, ChestType.SINGLE)
    }

    override fun createBlockEntity(blockPos: BlockPos, blockState: BlockState): PairedChestBlockEntity {
        return PairedChestBlockEntity(blockPos, blockState)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        println("OOOOOOOOOOOOOOOOOOOON PLACEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
        if (itemStack.hasCustomName()) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is PairedChestBlockEntity) {
                blockEntity.customName = itemStack.name
            }
        }
    }
}
