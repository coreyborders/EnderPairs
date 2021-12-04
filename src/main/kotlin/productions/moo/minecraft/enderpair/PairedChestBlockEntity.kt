package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import java.util.*

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) :
    ChestBlockEntity(EnderPair.PAIRED_CHEST_TYPE, pos, state) {

    var uuid: UUID = UUID.randomUUID()

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        if (nbt.contains(EnderPair.PAIRED_CHEST)) {
            uuid = nbt.getUuid(EnderPair.PAIRED_CHEST)
        }
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putUuid(EnderPair.PAIRED_CHEST, uuid)
        return super.writeNbt(nbt)
    }

    fun setInventory(inventory: DefaultedList<ItemStack>) {
        this.invStackList = inventory
    }
}
