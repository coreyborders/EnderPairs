package productions.moo.minecraft.enderpair

import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.block.entity.ViewerCountManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class PairedChestBlockEntity(pos: BlockPos, state: BlockState) :
    ChestBlockEntity(EnderPair.PAIRED_CHEST_TYPE, pos, state) {
    private val stateManager: ViewerCountManager

    init {
        this.stateManager = object : ViewerCountManager() {
            override fun onContainerOpen(world: World, pos: BlockPos, state: BlockState?) {
                playSound(world, pos, SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL)
            }

            override fun onContainerClose(world: World, pos: BlockPos, state: BlockState?) {
                playSound(world, pos, SoundEvents.BLOCK_GRINDSTONE_USE)
            }

            override fun onViewerCountUpdate(
                world: World?,
                pos: BlockPos?,
                state: BlockState?,
                oldViewerCount: Int,
                newViewerCount: Int
            ) {
                onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount)
            }

            override fun isPlayerViewing(player: PlayerEntity?): Boolean {
                return if (player!!.currentScreenHandler !is GenericContainerScreenHandler) {
                    false
                } else {
                    val inventory = (player.currentScreenHandler as GenericContainerScreenHandler).inventory
                    inventory === this@PairedChestBlockEntity
                }
            }
        }
    }

    fun playSound(world: World, pos: BlockPos, soundEvent: SoundEvent) {
        world.playSound(
            null as PlayerEntity?,
            pos.x.toDouble(),
            pos.y.toDouble(),
            pos.z.toDouble(),
            soundEvent,
            SoundCategory.BLOCKS,
            0.3f,
            0.6f
        )
    }

    override fun serializeLootTable(nbt: NbtCompound?): Boolean {
        val inventory = PairedChestInventoryManager.getOrCreateInventory(world as ServerWorld, uuid)
        setInventory(inventory)
        return super.serializeLootTable(nbt)
    }


    var uuid: UUID = UUID.randomUUID()
    private var inventory: PairedChestInventory? = null

    override fun getDisplayName(): Text {
        return if (hasCustomName()) {
            customName!!
        } else {
            Text.translatable(cachedState.block.translationKey)
        }
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

    override fun onOpen(player: PlayerEntity) {
        if (!removed && !player.isSpectator) {
            this.stateManager.openContainer(player, getWorld(), getPos(), cachedState)
        }
    }

    override fun onClose(player: PlayerEntity) {
        inventory!!.markDirty()
        if (!removed && !player.isSpectator) {
            this.stateManager.closeContainer(player, getWorld(), getPos(), cachedState)
        }
    }

    override fun onScheduledTick() {
        if (!removed) {
            this.stateManager.updateViewerCount(getWorld(), getPos(), cachedState)
        }
    }

    fun serverTick() {
        markDirty()
    }

    override fun size(): Int {
        return inventory?.items?.size?: 0
    }

    override fun getStack(slot: Int): ItemStack {
        return inventory?.items?.get(slot)?: ItemStack.EMPTY
    }
}
