package com.fphoenixcorneae.numberlayout

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("selected")
fun isSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter(value = ["onSingleClick"], requireAll = false)
fun setOnSingleClick(
    view: View,
    onClickListener: View.OnClickListener
) {
    val hits = LongArray(2)
    view.setOnClickListener {
        System.arraycopy(hits, 1, hits, 0, hits.size - 1)
        hits[hits.size - 1] = SystemClock.uptimeMillis()
        if (hits[0] < SystemClock.uptimeMillis() - 500) {
            onClickListener.onClick(it)
        }
    }
}



