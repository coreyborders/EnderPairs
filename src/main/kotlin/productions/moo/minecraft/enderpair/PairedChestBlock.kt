package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.ChestBlock
import net.minecraft.block.enums.ChestType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class PairedChestBlock(settings: FabricBlockSettings) : ChestBlock(settings, { EnderPair.PAIRED_CHEST_TYPE }) {
    init {
        defaultState = stateManager.defaultState
            .with(Properties.CHEST_TYPE, ChestType.SINGLE)
    }

    companion object {
        private val inventoryMap = HashMap<UUID, DefaultedList<ItemStack>>()

        fun getOrCreateInventory(uuid: UUID): DefaultedList<ItemStack> {
            return if (inventoryMap.containsKey(uuid)) {
                inventoryMap[uuid]!!
            } else {
                val inventory = DefaultedList.ofSize(EnderPair.INVENTORY_SIZE, ItemStack.EMPTY)
                inventoryMap[uuid] = inventory
                inventory
            }
        }
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
        if (blockEntity is PairedChestBlockEntity) {
            val inventory = getOrCreateInventory(blockEntity.uuid)
            blockEntity.setInventory(inventory)
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }
}
