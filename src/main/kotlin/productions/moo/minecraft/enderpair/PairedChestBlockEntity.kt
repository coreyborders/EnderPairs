package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

private const val UUID_KEY = "UUID"

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) :
    LootableContainerBlockEntity(EnderPair.PAIRED_CHEST_BLOCK_ENTITY, pos, state) {

    companion object {
        fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf): PairedChestScreenHandler {
            val world = playerInventory.player.world
            val pos = buf.readBlockPos()
            val blockState = world.getBlockState(pos)
            val entityProvider = blockState.block as BlockEntityProvider
            val pairedChestBlockEntity = entityProvider.createBlockEntity(pos, blockState) as PairedChestBlockEntity

            return PairedChestScreenHandler(syncId, playerInventory, pairedChestBlockEntity)
        }

        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, blockEntity: PairedChestBlockEntity) {
            println("Look ma, I'm ticking!")
        }

        val INVENTORY_SIZE = 36
    }


    private var inventory = DefaultedList.ofSize<ItemStack>(INVENTORY_SIZE, ItemStack.EMPTY)!!
    override fun getContainerName(): Text {
        return TranslatableText("container.ender_pair")
    }

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        return PairedChestScreenHandler(syncId, playerInventory, this as Inventory)
    }

    override fun getInvStackList(): DefaultedList<ItemStack> {
        return inventory
    }

    override fun setInvStackList(list: DefaultedList<ItemStack>) {
        inventory = list
    }

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
        Inventories.writeNbt(nbt, inventory)
        return nbt
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        _id = UUID.fromString(nbt.getString(UUID_KEY))
        Inventories.readNbt(nbt, inventory)
    }

    override fun size(): Int {
        return INVENTORY_SIZE
    }


}
