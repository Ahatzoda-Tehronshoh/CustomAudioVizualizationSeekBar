package com.tehron.shoh.customVizualSeekBar

enum class ColumnStatus {
    PLAYED,
    PLAYING,
    NOT_PLAYED
}

typealias OnProgressChangedListener = (columnNumber: Int) -> (Unit)

class CustomSeekBarViewColumn(
    val columnHeight : Float,
    val columnWidth: Float,
    var columnState: ColumnStatus
)