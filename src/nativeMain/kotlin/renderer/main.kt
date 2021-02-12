package renderer

@ExperimentalUnsignedTypes
fun main() {
    val canvas = Canvas()
    canvas.putPixel(0, 0, 255u, 0u, 0u, 0u)
    canvas.putPixel(1, 0, 255u, 0u, 0u, 0u)
    canvas.putPixel(0, 1, 255u, 0u, 0u, 0u)
    canvas.putPixel(1, 1, 255u, 0u, 0u, 0u)
    canvas.putPixel(2, 1, 255u, 0u, 0u, 0u)
    canvas.putPixel(1, 2, 255u, 0u, 0u, 0u)
    canvas.putPixel(2, 2, 255u, 0u, 0u, 0u)
    canvas.putPixel(3, 3, 255u, 0u, 0u, 0u)
    canvas.putPixel(4, 4, 255u, 0u, 0u, 0u)
    canvas.putPixel(5, 5, 255u, 0u, 0u, 0u)
    canvas.putPixel(6, 6, 255u, 0u, 0u, 0u)
    canvas.putPixel(7, 7, 255u, 0u, 0u, 0u)
    canvas.putPixel(8, 8, 255u, 0u, 0u, 0u)
    canvas.putPixel(9, 9, 255u, 0u, 0u, 0u)
    canvas.renderLoop()
}