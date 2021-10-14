package productions.moo.minecraft.enderpair

import ImplementedInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

private const val UUID_KEY = "UUID"

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(EnderPair.PAIRED_CHEST_BLOCK_ENTITY, pos, state), ImplementedInventory {
    override val items: DefaultedList<ItemStack?>
        get() = DefaultedList.ofSize(2, ItemStack.EMPTY)


    private var _id = UUID.randomUUID()
    var id: String
        get() = _id.toString()
        set(value) {
            _id = UUID.fromString(value)
            markDirty()
        }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)
        nbt.putString(UUID_KEY, _id.toString())
        Inventories.writeNbt(nbt, items)
        return nbt
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        _id = UUID.fromString(nbt.getString(UUID_KEY))
        Inventories.readNbt(nbt, items)
    }

    companion object {
        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, blockEntity: PairedChestBlockEntity) {
            println("Look ma, I'm ticking!")
        }
    }

    override fun clear() {
        super.clear()
    }

    override fun size(): Int {
        return super.size()
    }

    override fun isEmpty(): Boolean {
        return super.isEmpty()
    }

    override fun getStack(slot: Int): ItemStack {
        return super.getStack(slot)
    }

    override fun removeStack(slot: Int, count: Int): ItemStack {
        return super.removeStack(slot, count)
    }

    override fun removeStack(slot: Int): ItemStack {
        return super.removeStack(slot)
    }

    override fun getMaxCountPerStack(): Int {
        return super.getMaxCountPerStack()
    }

    override fun markDirty() {
        TODO("Not yet implemented")
    }

    override fun onOpen(player: PlayerEntity?) {
        super.onOpen(player)
    }

    override fun onClose(player: PlayerEntity?) {
        super.onClose(player)
    }

    override fun isValid(slot: Int, stack: ItemStack?): Boolean {
        return super.isValid(slot, stack)
    }

    override fun count(item: Item?): Int {
        return super.count(item)
    }

    override fun containsAny(items: MutableSet<Item>?): Boolean {
        return super.containsAny(items)
    }
}