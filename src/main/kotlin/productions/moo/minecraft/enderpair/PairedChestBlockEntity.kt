package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import java.util.*

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) :
    ChestBlockEntity(EnderPair.PAIRED_CHEST_TYPE, pos, state) {

    var uuid: UUID = UUID.randomUUID()
    private var inventory: PairedChestInventory? = null

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        if (nbt.contains(EnderPair.PAIRED_CHEST)) {
            uuid = nbt.getUuid(EnderPair.PAIRED_CHEST)
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putUuid(EnderPair.PAIRED_CHEST, uuid)
    }

    fun setInventory(inventory: PairedChestInventory) {
        this.inventory = inventory
        this.invStackList = inventory.items
    }

    override fun onClose(player: PlayerEntity?) {
        inventory!!.markDirty()
        super.onClose(player)
    }
}
