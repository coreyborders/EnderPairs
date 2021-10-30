package productions.moo.minecraft.enderpair

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class PairedChestScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: Inventory = SimpleInventory(PairedChestBlockEntity.INVENTORY_SIZE)) : ScreenHandler(EnderPair.PAIRED_CHEST_SCREEN_HANDLER, syncId) {
    private lateinit var inventory: Inventory
    init {
        this.inventory = inventory
        checkSize(inventory, PairedChestBlockEntity.INVENTORY_SIZE)
        inventory.onOpen(playerInventory.player)

        for(i in 0..3) {
            for(j in 0..8) {
                this.addSlot(Slot(inventory, i * 9 + j, 8 + j * 18, 18 + i * 18))
            }
        }

        for(i in 0..3) {
            for(j in 0..8) {
                this.addSlot(Slot(playerInventory, i * 9 + j + 9, 8 + j * 18, 18 + i * 18 + 103 + 18))
            }
        }

        for(j in 0..8) {
            this.addSlot(Slot(playerInventory, j, 8 + j * 18, 18 + 161 + 18))
        }
    }
    override fun canUse(player: PlayerEntity?): Boolean {
        return this.inventory.canPlayerUse(player)
    }

    override fun transferSlot(player: PlayerEntity?, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = this.slots[index]

        if(slot?.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if(index < inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size()-1, this.slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if(!this.insertItem(originalStack, 0, this.inventory.size()-1, false)) {
                return ItemStack.EMPTY
            }
            if(originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }
}

