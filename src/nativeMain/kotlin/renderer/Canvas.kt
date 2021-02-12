package renderer

import cnames.structs.SDL_Renderer
import cnames.structs.SDL_Window
import kotlinx.cinterop.*
import sdl.*

@ExperimentalUnsignedTypes
class Canvas {
    private val width = 800;
    private val height = 600;

    private data class Colour(val red: Uint8, val green: Uint8, val blue: Uint8, val alpha: Uint8)

    private inline fun<reified T> Pair<Int,Int>.createArray(initialValue: T) = Array(this.first) { Array(this.second) { initialValue } }

    private val canvas = Pair(height, width).createArray(Colour(0u, 0u, 0u, 0u))

    private val window: CPointer<SDL_Window>
    private val renderer: CPointer<SDL_Renderer>

    private fun pollEvents(): Boolean {
        memScoped {
            val event = alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr.reinterpret()) != 0) {
                when (event.type) {
                    SDL_QUIT -> return true
                    SDL_KEYDOWN -> return true
                    SDL_MOUSEBUTTONDOWN -> return true
                }
            }
        }

        return false
    }

    fun renderLoop() {
        var exit = false
        while (!exit) {
            exit = pollEvents()
            for (i in 0 until height) {
                val row = canvas[i]
                for (j in 0 until width) {
                    val colour = row[j]
                    SDL_SetRenderDrawColor(renderer, colour.red, colour.green, colour.blue, colour.alpha)
                    SDL_RenderDrawPoint(renderer, j, i)
                }
            }
            SDL_RenderPresent(renderer)
        }

        SDL_DestroyRenderer(renderer)
        SDL_DestroyWindow(window)
        SDL_Quit()
    }

    fun putPixel(x: Int, y: Int, r: Uint8, g: Uint8, b: Uint8, a: Uint8) {
        val midX: Int = width / 2
        val midY: Int = height / 2
        val transformX = x + midX
        val transformY = -y + midY
        canvas[transformY][transformX] = Colour(r, g, b, a)
    }

    init {
        SDL_Init(SDL_INIT_VIDEO)
        window = SDL_CreateWindow(
            "Renderer", 90, 90, width, height,
            SDL_WINDOW_SHOWN or SDL_WINDOW_ALLOW_HIGHDPI
        ) ?: throw Error()
        renderer = SDL_CreateRenderer(
            window,
            -1,
            SDL_RENDERER_ACCELERATED or SDL_RENDERER_PRESENTVSYNC
        ) ?: throw Error()

        SDL_SetRenderDrawColor(renderer, 0, 0, 0, 0);
        SDL_RenderClear(renderer)
    }
}