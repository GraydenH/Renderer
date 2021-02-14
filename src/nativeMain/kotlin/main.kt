import kotlinx.coroutines.*
import renderer.Color

@ExperimentalUnsignedTypes
fun main() {
    runBlocking {
        val app = App()
        app.start()
        
        val red = Color(255u, 0u, 0u, 0u)

        app.putPixel(0, 0, red)
        app.putPixel(1, 0, red)
        app.putPixel(0, 1, red)
        app.putPixel(1, 1, red)
        app.putPixel(2, 1, red)
        app.putPixel(1, 2, red)
        app.putPixel(2, 2, red)
        app.putPixel(3, 3, red)
        app.putPixel(4, 4, red)
        app.putPixel(5, 5, red)
        app.putPixel(6, 6, red)
        app.putPixel(7, 7, red)
        app.putPixel(8, 8, red)
        app.putPixel(9, 9, red)
    }
}