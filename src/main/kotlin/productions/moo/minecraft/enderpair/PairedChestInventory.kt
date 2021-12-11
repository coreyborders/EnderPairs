package productions.moo.minecraft.enderpair

import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.PersistentState

class PairedChestInventory : PersistentState() {
    var items: DefaultedList<ItemStack> = DefaultedList.ofSize(EnderPair.INVENTORY_SIZE, ItemStack.EMPTY)

    companion object {
        fun createFromNbt(nbt: NbtCompound): PairedChestInventory {
            val inventory = PairedChestInventory()
            Inventories.readNbt(nbt, inventory.items)
            return inventory
        }
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return Inventories.writeNbt(nbt, items)
    }
}