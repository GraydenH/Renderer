import kotlinx.coroutines.*
import renderer.Raytracer
import renderer.Scene
import renderer.Vector

@ExperimentalUnsignedTypes
fun main() {
    runBlocking {
        val spheres = listOf(
            Sphere(Vector(0.0, -1.0, 3.0), 1.0, Color(0u, 0u, 255u, 255u)),
            Sphere(Vector(2.0, 0.0, 4.0), 1.0, Color(255u, 0u, 0u, 255u)),
            Sphere(Vector(-2.0, 0.0, 4.0), 1.0, Color(0u, 255u, 0u, 255u))
        )
        val scene = Scene(spheres)
        val raytracer = Raytracer(scene)
        raytracer.start()
    }
}