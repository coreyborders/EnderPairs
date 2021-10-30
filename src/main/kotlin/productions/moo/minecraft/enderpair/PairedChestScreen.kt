package productions.moo.minecraft.enderpair

import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.impl.networking.NetworkingImpl.MOD_ID
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
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
        private val TEXTURE = Identifier("textures/gui/container/shulker_box.png")
    }

    init {
        this.backgroundHeight = 114 + 6 * 18
    }

    override fun drawBackground(matrices: MatrixStack?, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }
}