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
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
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
        if (!world!!.isClient) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is PairedChestBlockEntity) {
                ContainerProviderImpl.INSTANCE.openContainer(
                   EnderPair.PAIRED_CHEST_IDENTIFIER,
                    player
                ) { buf: PacketByteBuf -> buf.writeBlockPos(pos) }
            }
        }
        return ActionResult.SUCCESS
    }

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return false
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
