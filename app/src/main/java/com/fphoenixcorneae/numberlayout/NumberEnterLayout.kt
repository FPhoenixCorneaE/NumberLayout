package com.fphoenixcorneae.numberlayout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.view.children

/**
 * @desc：数字输入布局
 * @date：2021-04-25 09:52
 */
class NumberEnterLayout(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet), TextWatcher, View.OnKeyListener {
    /**
     * 数字：个数、大小、颜色
     */
    var numberCount = 4
    var numberSize = 20f
    var numberColor = Color.parseColor("#001b37")

    /**
     * 描边颜色、验证失败描边颜色、描边宽度
     */
    var strokeColor = Color.parseColor("#c9c9c9")
    var strokeWidth = dp2px(1f).toInt()

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
     * 数字改变监听器
     */
    var onNumberChangedListener: ((String) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        for (index in 0 until numberCount) {
            val child = EditText(context).apply {
                background = GradientDrawable().apply {
                    setStroke(strokeWidth, strokeColor)
                    cornerRadius = radius
                }
                gravity = Gravity.CENTER
                textSize = numberSize
                setTextColor(numberColor)
                // 字体加粗
                paint.isFakeBoldText = true
                // 只可输入数字
                inputType = InputType.TYPE_CLASS_NUMBER
                // 是否可点击
                isClickable = index == 0
                // 是否获取鼠标的焦点
                isFocusable = isClickable
                // 是否可在触摸模式下对焦
                isFocusableInTouchMode = isClickable
                // 光标是否可见
                isCursorVisible = isClickable
                if (isClickable) {
                    // 请求焦点
                    requestFocus()
                }
                // 最大长度为 1
                filters = arrayOf<InputFilter>(LengthFilter(1))
                addTextChangedListener(this@NumberEnterLayout)
                setOnKeyListener(this@NumberEnterLayout)
            }
            addView(child, LayoutParams(boxSize, boxSize))

            if (index < numberCount - 1) {
                // 添加间隔
                addView(Space(context), LayoutParams(spaceSize, boxSize))
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val enterNumber = StringBuilder()
        children.forEach {
            if (it is EditText) {
                enterNumber.append(it.text.toString())
            }
        }
        children.forEachIndexed { index, view ->
            // 是否可点击
            view.isClickable = when {
                enterNumber.isEmpty() -> {
                    index == 0
                }
                enterNumber.length < numberCount -> {
                    index / 2 == enterNumber.length && index % 2 == 0
                }
                else -> {
                    index == childCount - 1
                }
            }
            if (view is EditText) {
                view.apply {
                    // 是否获取鼠标的焦点
                    isFocusable = isClickable
                    // 是否可在触摸模式下对焦
                    isFocusableInTouchMode = isClickable
                    // 光标是否可见
                    isCursorVisible = isClickable
                    if (isClickable) {
                        // 请求焦点
                        requestFocus()
                    }
                }
            }
        }
        onNumberChangedListener?.invoke(enterNumber.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }

    /**
     * dp值转换为px
     */
    private fun dp2px(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event?.action == KeyEvent.ACTION_DOWN) {
            // 发现执行了两次因为 onKey 事件包含了 down 和 up 事件，所以只需要加入其中一个即可。
            kotlin.run breaking@{
                for (index in childCount - 1 downTo 0) {
                    if (index % 2 == 0 && getChildAt(index) is EditText) {
                        val editText = getChildAt(index) as EditText
                        if (editText.text.isNullOrEmpty() && index - 2 >= 0) {
                            val lastEditText = getChildAt(index - 2) as EditText
                            // 当前 editText 的 text 为空，并且前一个 editText 的 text 不为空
                            if (lastEditText.text.isNullOrEmpty().not()) {
                                (getChildAt(index - 2) as EditText).text?.clear()
                                return@breaking
                            }
                        } else if (editText.text.isNullOrEmpty().not()) {
                            // 当前 editText 的 text 不为空
                            editText.text?.clear()
                            return@breaking
                        }
                    }
                }
            }
            return true
        }
        return false
    }

    /**
     * 清空数字
     */
    fun clearNumbers() {
        children.forEach {
            if (it is EditText) {
                if (it.background is GradientDrawable) {
                    (it.background as GradientDrawable).setStroke(strokeWidth, strokeColor)
                }
                it.text?.clear()
            }
        }
    }
}