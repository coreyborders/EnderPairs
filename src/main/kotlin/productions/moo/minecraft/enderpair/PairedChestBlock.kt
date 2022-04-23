package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.ChestBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.enums.ChestType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
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

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        val blockEntity = world.getBlockEntity(pos)
        if (blockEntity is PairedChestBlockEntity && !world.isClient) {
            val inventory = PairedChestInventoryManager.getOrCreateInventory(world as ServerWorld, blockEntity.uuid)
            blockEntity.setInventory(inventory)
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (!state.isOf(newState.block)) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is PairedChestBlockEntity) {
                world.removeBlockEntity(pos)
            } else {
                super.onStateReplaced(state, world, pos, newState, moved)
            }
        }
    }

    override fun afterBreak(
        world: World,
        player: PlayerEntity,
        pos: BlockPos,
        state: BlockState,
        blockEntity: BlockEntity?,
        stack: ItemStack?
    ) {
        if (blockEntity is PairedChestBlockEntity) {
            val chestNbtCompound = NbtCompound()
            chestNbtCompound.putInt("Count", 1)
            chestNbtCompound.putString("id", EnderPair.PAIRED_CHEST_IDENTIFIER.toString())
            val chestStack = ItemStack.fromNbt(chestNbtCompound)
            chestStack.orCreateNbt.putUuid(EnderPair.PAIRED_CHEST, blockEntity.uuid)
            if (blockEntity.hasCustomName()) {
                chestStack.setCustomName(blockEntity.customName)
            }
            val droppedChest = DefaultedList.of<ItemStack>()
            droppedChest.add(chestStack)

            ItemScatterer.spawn(world, pos, droppedChest)
        } else {
            super.afterBreak(world, player, pos, state, blockEntity, stack)
        }
    }

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (world.isClient) {
            super.getTicker(world, state, type)
        } else checkType(type, EnderPair.PAIRED_CHEST_TYPE) { world: World, pos: BlockPos, state: BlockState, blockEntity: PairedChestBlockEntity ->
            blockEntity.serverTick()
        }
    }

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
    }
}
