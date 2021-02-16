import renderer.Vector

inline fun<reified T> Pair<Int,Int>.createArray(initialValue: T) = Array(this.first) { Array(this.second) { initialValue } }

@ExperimentalUnsignedTypes
fun clamp(value: UInt, limit: UByte): UByte {
    return if (value > limit) limit else value.toUByte()
}

fun dot(a: Vector, b: Vector): Double {
    return a.first * b.first + a.second * b.second + a.third * b.third
}

fun subtract(left: Vector, right: Vector): Vector {
    return Vector(left.first - right.first, left.second - right.second, left.third - right.third)
}