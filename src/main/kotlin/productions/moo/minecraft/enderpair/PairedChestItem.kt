package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class PairedChestItem : BlockItem(EnderPair.PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC)) {
    override fun onCraft(stack: ItemStack, world: World, player: PlayerEntity) {
        super.onCraft(stack, world, player)
        stack.orCreateNbt.putUuid(EnderPair.PAIRED_CHEST, UUID.randomUUID())
    }

    override fun postPlacement(
        pos: BlockPos,
        world: World,
        player: PlayerEntity?,
        stack: ItemStack,
        state: BlockState
    ): Boolean {
        val result = super.postPlacement(pos, world, player, stack, state)
        if (!world.isClient) {
            val entity = world.getBlockEntity(pos)
            if (entity is PairedChestBlockEntity) {
                entity.uuid = stack.orCreateNbt!!.getUuid(EnderPair.PAIRED_CHEST)!!
                if (stack.hasCustomName()) {
                    entity.customName = stack.name
                }
                val inventory = PairedChestInventoryManager.getOrCreateInventory(world as ServerWorld, entity.uuid)
                entity.setInventory(inventory)
            }
        }
        return result
    }
}