package productions.moo.minecraft.enderpair

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class PairedChestItem : BlockItem(EnderPair.PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC)) {
    override fun onCraft(stack: ItemStack, world: World, player: PlayerEntity) {
        super.onCraft(stack, world, player)
    }

    override fun getPlacementContext(context: ItemPlacementContext?): ItemPlacementContext? {
        return super.getPlacementContext(context)
    }
}