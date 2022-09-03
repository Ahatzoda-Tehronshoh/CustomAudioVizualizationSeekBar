package com.tehron.shoh.customVizualSeekBar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.tehron.shoh.customVizualSeekBar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listColumns = mutableListOf<CustomSeekBarViewColumn>()

        for (i in 0..55) {
            listColumns.add(
                CustomSeekBarViewColumn(
                    (16..100).random().toFloat(),
                    0f,
                    if (i < 25) ColumnStatus.PLAYED else ColumnStatus.NOT_PLAYED
                )
            )
        }

        binding.myCustomSeekBarView.onProgressChangedListener = {
            listColumns.forEachIndexed { index, customSeekBarViewColumn ->
                customSeekBarViewColumn.columnState =
                    if (index <= it) ColumnStatus.PLAYED else ColumnStatus.NOT_PLAYED
            }
            binding.myCustomSeekBarView.columns = listColumns
        }

        binding.myCustomSeekBarView.columns = listColumns
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}