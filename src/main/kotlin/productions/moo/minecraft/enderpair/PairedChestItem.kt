import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import productions.moo.minecraft.enderpair.EnderPair

class PairedChestItem: BlockItem(EnderPair.PAIRED_CHEST_BLOCK, FabricItemSettings().group(ItemGroup.MISC))