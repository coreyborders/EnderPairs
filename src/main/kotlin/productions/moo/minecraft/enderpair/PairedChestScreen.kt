package productions.moo.minecraft.enderpair

import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.impl.networking.NetworkingImpl.MOD_ID
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PairedChestScreen(
    handler: PairedChestScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<PairedChestScreenHandler>(handler, playerInventory, title) {

    companion object {
        private val TEXTURE = Identifier(MOD_ID, "resources/assets/ender_pair/textures/block/paired_chest.png")
    }

    init {
        this.backgroundHeight = 114 + 6 * 18
    }

    override fun drawForeground(matrices: MatrixStack?, mouseX: Int, mouseY: Int) {
        this.textRenderer.draw(matrices, this.title.asString(), 8.0F, 6.0F, 4210752)
        this.textRenderer.draw(matrices, playerInventoryTitle.asOrderedText(), 8.0F, (this.backgroundHeight - 96 + 2).toFloat(), 4210752)
    }

    override fun drawBackground(matrices: MatrixStack?, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        this.client?.getTextureManager()?.bindTexture(TEXTURE)
        val i = (this.width - this.backgroundWidth) / 2
        val j = (this.height - this.backgroundHeight) / 2
    }
}