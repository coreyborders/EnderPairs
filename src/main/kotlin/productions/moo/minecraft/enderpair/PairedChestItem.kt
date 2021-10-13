import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import productions.moo.minecraft.enderpair.PairedChestBlock

class PairedChestItem(chestBlock: PairedChestBlock, settings: Settings): BlockItem(chestBlock, settings) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        player.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0f, 1.0f)
        return super.use(world, player, hand)
    }
}