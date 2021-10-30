import net.minecraft.screen.slot.Slot
import org.junit.Test

class PairedChestTest {
    @Test
    fun testingCounting() {
        var total = 0
        for(i in 0..3) {
            for(j in 0..8) {
                println("i = $i, j = $j")
                total++
                println("total = $total")
            }
        }
    }
}