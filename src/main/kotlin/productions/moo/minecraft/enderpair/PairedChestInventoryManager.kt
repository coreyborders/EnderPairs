package productions.moo.minecraft.enderpair

import net.minecraft.server.world.ServerWorld
import java.util.*

class PairedChestInventoryManager {
    companion object {
        private val inventoryMap = HashMap<UUID, PairedChestInventory>()

        fun getOrCreateInventory(world: ServerWorld, uuid: UUID): PairedChestInventory {
            return world.server.overworld.persistentStateManager!!.getOrCreate({ nbt ->
                PairedChestInventory.createFromNbt(
                    nbt
                )
            }, { PairedChestInventory() }, uuid.toString())!!
        }
    }
}