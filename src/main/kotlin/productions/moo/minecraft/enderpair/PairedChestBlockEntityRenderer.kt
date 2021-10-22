package productions.moo.minecraft.enderpair

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f

class PairedChestBlockEntityRenderer(): BlockEntityRenderer<PairedChestBlockEntity>,
    BlockEntityRendererFactory<PairedChestBlockEntity> {
    val stack: ItemStack = ItemStack(Items.JUKEBOX, 1)

    override fun render(
        entity: PairedChestBlockEntity?,
        tickDelta: Float,
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        overlay: Int
    ) {
        val offset: Double = Math.sin((entity?.world?.time!!.toDouble() + tickDelta) / 8.0) / 4.0
        matrices?.translate(0.5, 1.25 + offset, 0.5)
        matrices?.multiply(Quaternion(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time.toFloat() + tickDelta) * 4)))

        val lightAbove: Int = WorldRenderer.getLightmapCoordinates(entity.world, entity.pos.up())
        MinecraftClient.getInstance().itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 243324)
        matrices?.pop()
    }

    override fun create(ctx: BlockEntityRendererFactory.Context?): BlockEntityRenderer<PairedChestBlockEntity> {
        return PairedChestBlockEntityRenderer()
    }
}