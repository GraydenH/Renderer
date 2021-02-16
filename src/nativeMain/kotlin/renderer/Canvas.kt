package renderer

import Color
import cnames.structs.SDL_Renderer
import cnames.structs.SDL_Window
import createArray
import kotlinx.cinterop.*
import sdl.*
import kotlinx.coroutines.*

@ExperimentalUnsignedTypes
class Canvas {
    val width = 800;
    val height = 600;

    private val canvas = Pair(height, width).createArray(Color(0u, 0u, 0u, 0u))

    private val window: CPointer<SDL_Window>
    private val renderer: CPointer<SDL_Renderer>

    @ExperimentalUnsignedTypes
    fun start() {
        GlobalScope.async {
            renderLoop()
        }
    }

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

    private fun renderLoop() {
        while (!pollEvents()) {
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

    fun putPixel(x: Int, y: Int, color: Color) {
        val transformX = width / 2 + x
        val transformY = height / 2 - y - 1
        canvas[transformY][transformX] = color
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