package renderer

import Color
import Sphere
import dot
import subtract
import kotlin.math.sqrt

@ExperimentalUnsignedTypes
class Raytracer(private val scene: Scene) {
    private val canvas = Canvas()

    private val projectionPlaneZ = 1.0

    private val viewportSize = 1.0

    private val backgroundColor = Color(255u, 255u, 255u, 0u)

    //    CanvasToViewport(x, y) {
    //        return (x*Vw/Cw, y*Vh/Ch, d)
    //    }
    private fun canvasToViewport(x: Int, y: Int): Vector {
        val widthFactor: Double = viewportSize / canvas.width.toDouble()
        val heightFactor: Double = viewportSize / canvas.height.toDouble()
        return Vector(x * widthFactor, y * heightFactor, projectionPlaneZ)
    }

    //    TraceRay(O, D, t_min, t_max) {
    //        closest_t = inf
    //        closest_sphere = NULL
    //        for sphere in scene.spheres {
    //            t1, t2 = IntersectRaySphere(O, D, sphere)
    //            if t1 in [t_min, t_max] and t1 < closest_t {
    //                closest_t = t1
    //                closest_sphere = sphere
    //            }
    //            if t2 in [t_min, t_max] and t2 < closest_t {
    //                closest_t = t2
    //                closest_sphere = sphere
    //            }
    //        }
    //        if closest_sphere == NULL {
    //            ❶return BACKGROUND_COLOR
    //        }
    //        return closest_sphere.color
    //    }
    private fun traceRay(origin: Vector, direction: Vector, minT: Int, maxT: Int): Color {
        var closestT = Double.MAX_VALUE
        var closestSphereColor: Color = backgroundColor
        for (sphere in scene.spheres) {
            val (t1, t2) = intersectSphere(origin, direction, sphere)
            if (t1 > minT && t1 < maxT && t1 < closestT) {
                closestT = t1
                closestSphereColor = sphere.color
            }
            if (t2 > minT && t2 < maxT && t2 < closestT) {
                closestT = t2
                closestSphereColor = sphere.color
            }
        }
        return closestSphereColor
    }

    //    IntersectRaySphere(O, D, sphere) {
    //        r = sphere.radius
    //        CO = O - sphere.center
    //
    //        a = dot(D, D)
    //        b = 2*dot(CO, D)
    //        c = dot(CO, CO) - r*r
    //
    //        discriminant = b*b - 4*a*c
    //        if discriminant < 0 {
    //            return inf, inf
    //        }
    //
    //        t1 = (-b + sqrt(discriminant)) / (2*a)
    //        t2 = (-b - sqrt(discriminant)) / (2*a)
    //        return t1, t2
    //    }
    private fun intersectSphere(origin: Vector, direction: Vector, sphere: Sphere): Pair<Double, Double> {
        val radius = sphere.radius
        val sphereOrigin = subtract(origin, sphere.center)

        val a = dot(direction, direction)
        val b = 2 * dot(sphereOrigin, direction)
        val c = dot(sphereOrigin, sphereOrigin) - radius * radius

        val discriminant = b * b - 4 * a * c
        if (discriminant < 0) {
            return Pair(Double.MAX_VALUE, Double.MAX_VALUE)
        }

        val t1 = (-b + sqrt(discriminant)) / (2 * a)
        val t2 = (-b - sqrt(discriminant)) / (2 * a)
        return Pair(t1, t2)
    }

//    O = (0, 0, 0)
//    for x = -Cw/2 to Cw/2 {
//        for y = -Ch/2 to Ch/2 {
//            D = CanvasToViewport(x, y)
//            color = TraceRay(O, D, 1, inf)
//            canvas.PutPixel(x, y, color)
//        }
//    }
    fun start() {
        val origin = Vector(0.0, 0.0, 0.0)
        val widthStart: Int = -canvas.width / 2
        val widthEnd: Int = canvas.width / 2
        val heightStart: Int = -canvas.height / 2
        val heightEnd: Int = canvas.height / 2
        for (x in widthStart until widthEnd) {
            for (y in heightStart until heightEnd) {
                val direction = canvasToViewport(x, y)
                val color = traceRay(origin, direction, 1, Int.MAX_VALUE)
                canvas.putPixel(x, y, color)
            }
        }
        canvas.start()
    }

}