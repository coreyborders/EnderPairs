package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class PairedChestBlock(settings: FabricBlockSettings) : BlockWithEntity(settings), FabricBlockEntityTypeBuilder.Factory<PairedChestBlockEntity> {
    override fun create(blockPos: BlockPos, blockState: BlockState): PairedChestBlockEntity {
        return createBlockEntity(blockPos, blockState)
    }

    override fun createBlockEntity(blockPos: BlockPos, blockState: BlockState): PairedChestBlockEntity {
        return PairedChestBlockEntity(blockPos, blockState)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        blockState: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, EnderPair.PAIRED_CHEST_BLOCK_ENTITY) { world1, blockPos, blockState1, blockEntity ->
            PairedChestBlockEntity.tick(world1, blockPos, blockState1, blockEntity)
        }
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (world != null) {
            if(world.isClient) return ActionResult.SUCCESS
        }
        val blockEntity = world?.getBlockEntity(pos) as Inventory
        if(!player?.getStackInHand(hand)?.isEmpty!!) {
            if(blockEntity.getStack(0).isEmpty) {
                blockEntity.setStack(0, player.getStackInHand(hand).copy())
                player.getStackInHand(hand).count = 0
            } else if(blockEntity.getStack(1).isEmpty) {
                blockEntity.setStack(1, player.getStackInHand(hand).copy())
                player.getStackInHand(hand).count = 0
            } else {
                println("The first slot holds ${blockEntity.getStack(0)} and the second slot holds ${blockEntity.getStack(1)}")
            }
        }
        return ActionResult.SUCCESS
    }
}
