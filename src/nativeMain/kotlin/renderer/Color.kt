package renderer

data class Color @ExperimentalUnsignedTypes constructor(val red: UByte, val green: UByte, val blue: UByte, val alpha: UByte) {
    @ExperimentalUnsignedTypes
    private fun clamp(value: UInt, limit: UByte): UByte {
        return if (value > limit) limit else value.toUByte()
    }

    @ExperimentalUnsignedTypes
    fun add(color: Color): Color {
        return Color(
            clamp(red + color.red, 255u),
            clamp(green + color.green, 255u),
            clamp(blue + color.blue, 255u),
            clamp(alpha + color.alpha, 255u)
        )
    }

    @ExperimentalUnsignedTypes
    fun intensity(factor: UInt): Color {
        return Color(
            clamp(factor * red, 255u),
            clamp(factor * green, 255u),
            clamp(factor * blue, 255u),
            clamp(factor * alpha, 255u),
        )
    }
}
