import renderer.Canvas
import kotlinx.coroutines.*
import renderer.Color

class App {
    @ExperimentalUnsignedTypes
    private val canvas = Canvas()

    @ExperimentalUnsignedTypes
    fun start() {
        GlobalScope.async {
            canvas.renderLoop()
        }
    }

    @ExperimentalUnsignedTypes
    fun putPixel(x: Int, y: Int, color: Color) {
        canvas.putPixel(x, y, color)
    }
}