import kotlinx.coroutines.*
import renderer.Raytracer
import renderer.Scene
import renderer.Vector

@ExperimentalUnsignedTypes
fun main() {
    runBlocking {
        val spheres = listOf(
            Sphere(Vector(0.0, 0.0, 0.0), .001, Color(100u, 100u, 0u, 255u))
        )
        val scene = Scene(spheres)
        val raytracer = Raytracer(scene)
        raytracer.start()
    }
}