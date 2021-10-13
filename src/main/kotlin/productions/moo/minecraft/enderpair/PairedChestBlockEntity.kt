package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

private const val UUID_KEY = "UUID"

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(EnderPair.PAIRED_CHEST_BLOCK_ENTITY, pos, state) {
    private var _id = UUID.randomUUID();
    var id: String
        get() = _id.toString()
        set(value) {
            _id = UUID.fromString(value)
            markDirty()
        }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)
        nbt.putString(UUID_KEY, _id.toString())
        return nbt
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        _id = UUID.fromString(nbt.getString(UUID_KEY))
    }

    companion object {
        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, blockEntity: PairedChestBlockEntity) {
            println("Look ma, I'm ticking!")
        }
    }
}