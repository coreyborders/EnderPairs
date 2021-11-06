package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) :
    ChestBlockEntity(EnderPair.PAIRED_CHEST_TYPE, pos, state) {

    init {
        println(" EEEEEEEEEEEEEEEEEEEENTITY CREATEDDDDDDDDDDDDDDDDDDDDD")
        println(state)
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        if (world != null) {
            if (world!!.isClient) {
                println("!!!!!!!!!! CLIENT READ NBT DATA !!!!!!!!!!")
                println(nbt.toString())
            } else {
                println("!!!!!!!!!! SERVER READ NBT DATA !!!!!!!!!!")
                println(nbt.toString())
            }
        } else {
            println("!!!!!!!!!! UNKONWN READ NBT DATA !!!!!!!!!!")
            println(nbt.toString())
        }

        if(nbt.contains(EnderPair.PAIRED_CHEST)) {
            (cachedState.block as PairedChestBlock).uuid = nbt.getUuid(EnderPair.PAIRED_CHEST)!!
        }

    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        val ret = super.writeNbt(nbt)

        if (world != null) {
            if (world!!.isClient) {
                println("!!!!!!!!!! CLIENT WRITE NBT DATA !!!!!!!!!!")
                println(nbt.toString())
            } else {
                println("!!!!!!!!!! SERVER WRITE NBT DATA !!!!!!!!!!")
                println(nbt.toString())
            }
        } else {
            println("!!!!!!!!!! UNKNOWN WRITE NBT DATA !!!!!!!!!!")
            println(nbt.toString())
        }

        val uuid = (cachedState.block as PairedChestBlock).uuid
        ret.putUuid(EnderPair.PAIRED_CHEST, uuid)

        return ret
    }

    fun setInventory(inventory: DefaultedList<ItemStack>) {
        this.invStackList = inventory
    }
}
