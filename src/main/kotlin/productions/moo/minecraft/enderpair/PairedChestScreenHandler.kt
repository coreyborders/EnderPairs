package productions.moo.minecraft.enderpair

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class PairedChestScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    inventory: Inventory = SimpleInventory(EnderPair.INVENTORY_SIZE)
) : ScreenHandler(EnderPair.PAIRED_CHEST_SCREEN_HANDLER, syncId) {
    private var inventory: Inventory

    init {
        this.inventory = inventory
        checkSize(inventory, EnderPair.INVENTORY_SIZE)
        inventory.onOpen(playerInventory.player)

        // Chest inventory
        for (i in 0 until EnderPair.INVENTORY_HEIGHT) {
            for (j in 0 until EnderPair.INVENTORY_WIDTH) {
                this.addSlot(Slot(inventory, j + i * 9, 8 + j * 18, 18 + i * 18))
            }
        }

        // Player inventory
        for (i in 0 until EnderPair.INVENTORY_HEIGHT) {
            for (j in 0 until EnderPair.INVENTORY_WIDTH) {
                this.addSlot(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        // Player hotbar
        for (i in 0 until EnderPair.INVENTORY_WIDTH) {
            this.addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
        }
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return this.inventory.canPlayerUse(player)
    }

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = this.slots[index]

        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }
}

