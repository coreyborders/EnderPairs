package productions.moo.minecraft.enderpair

import it.unimi.dsi.fastutil.floats.Float2FloatFunction
import it.unimi.dsi.fastutil.ints.Int2IntFunction
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ChestBlock
import net.minecraft.block.DoubleBlockProperties
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.client.block.ChestAnimationProgress
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f

@Environment(EnvType.CLIENT)
class PairedChestBlockEntityRenderer(context: BlockEntityRendererFactory.Context) :
    ChestBlockEntityRenderer<PairedChestBlockEntity>(context) {
    private val lid: ModelPart
    private val bottom: ModelPart
    private val lock: ModelPart

    companion object {
        val PAIRED_CHEST_TEXTURE = Identifier(EnderPair.MOD_ID, "block/paired_chest")
    }

    init {
        val modelPart: ModelPart = context.getLayerModelPart(EntityModelLayers.CHEST)
        lid = modelPart.getChild("lid")
        bottom = modelPart.getChild("bottom")
        lock = modelPart.getChild("lock")
    }

    override fun render(
        entity: PairedChestBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val world = entity.world
        val blockState: BlockState = if (world != null) entity.cachedState else Blocks.CHEST.defaultState.with(
            ChestBlock.FACING,
            Direction.SOUTH
        )

        if (blockState.block is PairedChestBlock) {
            val chest = blockState.block as PairedChestBlock

            matrices.push()

            val rotation = (blockState.get(ChestBlock.FACING) as Direction).asRotation()
            matrices.translate(0.5, 0.5, 0.5)
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-rotation))
            matrices.translate(-0.5, -0.5, -0.5)

            val properties = chest.getBlockEntitySource(blockState, world, entity.pos, true)
            val animationProgress =
                (properties.apply(ChestBlock.getAnimationProgressRetriever(entity as ChestAnimationProgress)) as Float2FloatFunction).get(
                    tickDelta
                )

            @Suppress("UNCHECKED_CAST")
            val retriever =
                LightmapCoordinatesRetriever<PairedChestBlockEntity>() as DoubleBlockProperties.PropertyRetriever<in ChestBlockEntity, Int2IntFunction>
            val lighting = properties.apply(retriever).applyAsInt(light)

            val spriteIdentifier = SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, PAIRED_CHEST_TEXTURE)
            val vertexConsumer: VertexConsumer =
                spriteIdentifier.getVertexConsumer(vertexConsumers) { texture: Identifier? ->
                    RenderLayer.getEntityCutout(texture)
                }
            handleModelRender(matrices, vertexConsumer, animationProgress, lighting, overlay)
            matrices.pop()
        }
    }

    private fun handleModelRender(
        matrices: MatrixStack,
        vertices: VertexConsumer,
        openFactor: Float,
        light: Int,
        overlay: Int
    ) {
        lid.pitch = -openFactor * 1.5707964f
        lock.pitch = lid.pitch
        lid.render(matrices, vertices, light, overlay)
        lock.render(matrices, vertices, light, overlay)
        bottom.render(matrices, vertices, light, overlay)
    }


}