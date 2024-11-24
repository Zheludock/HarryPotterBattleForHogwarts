data class Location(val name: String, val numberOfDarkArts: Int, val maxBlackMark: Int, val numberOfChapter: Int) {
    var currentBlackMark = 0
}

val kosoleya = Location("Косой переулок", 1, 4, 1)
val mirror = Location("Зеркало Еиналеж", 1, 4, 1)