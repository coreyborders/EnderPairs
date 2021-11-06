package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import java.util.*

class PairedChestItem : BlockItem(EnderPair.PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC)) {
    override fun onCraft(stack: ItemStack, world: World, player: PlayerEntity) {
        super.onCraft(stack, world, player)
        stack.orCreateNbt.putUuid(EnderPair.PAIRED_CHEST, UUID.randomUUID())
    }
}