package com.fphoenixcorneae.numberlayout

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.fphoenixcorneae.numberlayout.databinding.ViewNumKeyboardBinding

/**
 * @desc: 数字键盘
 * @date：2021-03-31 09:48
 */
@SuppressLint("ClickableViewAccessibility")
class NumberKeyboardLayout(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet), View.OnClickListener {

    /**
     * 已经输入的数字数组
     */
    private var mNumbers = mutableListOf<String>()

    /**
     * 最大数字个数
     */
    var maxNumberCount = 4

    /**
     * 点击监听器
     */
    var onClickListener: ((MutableList<String>) -> Unit)? = null

    private lateinit var mDataBinding: ViewNumKeyboardBinding

    override fun onFinishInflate() {
        super.onFinishInflate()
        mDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_num_keyboard,
            this,
            true
        )
        mDataBinding.onClickListener = this
        mDataBinding.ibDelete.setOnTouchListener { v, event ->
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(
                        -android.R.attr.state_pressed,
                        -android.R.attr.state_selected
                    ),
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_selected)
                ),
                intArrayOf(
                    Color.parseColor("#0099cc"),
                    Color.parseColor("#ffffff"),
                    Color.parseColor("#ffffff")
                )
            )
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 删除按钮按下
                    v.performClick()
                    v.isPressed = true
                    colorStateList.also {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            (v as ImageButton).imageTintList = it
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // 删除按钮抬起
                    v.isPressed = false
                    colorStateList.also {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            (v as ImageButton).imageTintList = it
                        }
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    override fun onClick(v: View?) {
        when {
            v is ImageButton -> {
                // 点击删除按钮
                if (mNumbers.isNotEmpty()) {
                    mNumbers.removeLastOrNull()
                    onClickListener?.invoke(mNumbers)
                }
            }
            v is TextView && mNumbers.size < maxNumberCount -> {
                // 点击数字
                mNumbers.add(v.text.toString())
                onClickListener?.invoke(mNumbers)
            }
        }
    }
}