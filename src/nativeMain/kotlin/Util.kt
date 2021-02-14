inline fun<reified T> Pair<Int,Int>.createArray(initialValue: T) = Array(this.first) { Array(this.second) { initialValue } }

@ExperimentalUnsignedTypes
fun clamp(value: UInt, limit: UByte): UByte {
    return if (value > limit) limit else value.toUByte()
}