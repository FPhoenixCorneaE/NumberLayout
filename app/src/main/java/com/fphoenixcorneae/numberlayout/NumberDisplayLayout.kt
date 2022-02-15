package com.fphoenixcorneae.numberlayout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.core.view.children

/**
 * @desc：数字展示布局
 * @date：2021-03-31 16:30
 */
class NumberDisplayLayout(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {
    /**
     * 数字：个数、大小、颜色
     */
    var numberCount = 4
    var numberSize = 20f
    var numberColor = Color.parseColor("#001b37")

    /**
     * 描边颜色、验证失败描边颜色、验证成功描边颜色、描边宽度
     */
    var strokeColor = Color.parseColor("#c9c9c9")
    var errorStrokeColor = Color.parseColor("#ff0000")
    var correctStrokeColor = Color.parseColor("#00ff00")
    var strokeWidth = 1

    /**
     * 圆角半径
     */
    var radius = dp2px(2f)

    /**
     * 盒子：大小、间距
     */
    var boxSize = dp2px(50f).toInt()
    var spaceSize = dp2px(15f).toInt()

    /**
     * 正确的数字字符串
     */
    var correctNumbers: String? = null

    /**
     * 填充完成监听器
     */
    var onFillFinishedListener: ((Boolean) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        for (index in 0 until numberCount) {
            val child = TextView(context).apply {
                background = GradientDrawable().apply {
                    setStroke(strokeWidth, strokeColor)
                    cornerRadius = radius
                }
                gravity = Gravity.CENTER
                textSize = numberSize
                setTextColor(numberColor)
                paint.isFakeBoldText = true
            }
            addView(child, LayoutParams(boxSize, boxSize))

            if (index < numberCount - 1) {
                addView(Space(context), LayoutParams(spaceSize, boxSize))
            }
        }
    }

    /**
     * 填充数字
     */
    fun fillNumbers(numbers: List<String>) {
        clearNumbers()
        for (index in numbers.indices) {
            if (index * 2 < childCount) {
                val textChild = getChildAt(index * 2) as TextView
                textChild.text = numbers[index]
            }
            if (index * 2 == childCount - 1) {
                val filledNumbers = numbers.subList(0, index + 1).joinToString("")
                val isCorrect = filledNumbers == correctNumbers
                onFillFinishedListener?.invoke(isCorrect)
                children.forEach {
                    if (it.background is GradientDrawable) {
                        (it.background as GradientDrawable).setStroke(
                            strokeWidth,
                            if (isCorrect) correctStrokeColor else errorStrokeColor
                        )
                    }
                }
                break
            }
        }
    }

    /**
     * 清空数字
     */
    private fun clearNumbers() {
        children.forEach {
            if (it is TextView) {
                if (it.background is GradientDrawable) {
                    (it.background as GradientDrawable).setStroke(strokeWidth, strokeColor)
                }
                it.text = ""
            }
        }
    }

    /**
     * dp值转换为px
     */
    private fun dp2px(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }
}