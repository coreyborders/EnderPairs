package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.impl.container.ContainerProviderImpl
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class PairedChestBlock(settings: FabricBlockSettings) : BlockWithEntity(settings),
    FabricBlockEntityTypeBuilder.Factory<PairedChestBlockEntity> {
    override fun create(blockPos: BlockPos, blockState: BlockState): PairedChestBlockEntity {
        return createBlockEntity(blockPos, blockState)
    }

    override fun createBlockEntity(blockPos: BlockPos, blockState: BlockState): PairedChestBlockEntity {
        return PairedChestBlockEntity(blockPos, blockState)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient) {
            val screenHandlerFactory = state.createScreenHandlerFactory(world, pos)
            if(screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun onStateReplaced(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        newState: BlockState?,
        moved: Boolean
    ) {
        if(state?.block != newState?.block) {
            val blockEntity = world?.getBlockEntity(pos)
            if(blockEntity is PairedChestBlockEntity) {
                ItemScatterer.spawn(world, pos, blockEntity)
                world.updateComparators(pos, this)
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState?, world: World?, pos: BlockPos?): Int {
        return ScreenHandler.calculateComparatorOutput(world?.getBlockEntity(pos))
    }

    override fun onPlaced(
        world: World?,
        pos: BlockPos?,
        state: BlockState?,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        if (itemStack!!.hasCustomName()) {
            val blockEntity = world!!.getBlockEntity(pos)
            if (blockEntity is PairedChestBlockEntity) {
                (blockEntity as PairedChestBlockEntity).customName = itemStack.name
            }
        }
    }
}
