package com.mm.shanai.common.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: lzm
 * @Date: 2024-09-25 21:25
 * @Description: TODO
 */
class CustomSpacingItemDecoration(
    private val spanCount: Int,    // 列数
    private val spacing: Int,      // 间距
    private val includeEdge: Boolean  // 是否包括边缘
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // 当前item的位置
        val column = position % spanCount // 当前item的列位置

        if (includeEdge) {
            // 包含边缘的情况下
            outRect.left = spacing * (spanCount - column) / spanCount // 左边距：根据列数和当前列来分配
            outRect.right = spacing * (column + 1) / spanCount // 右边距：对称处理

            if (position < spanCount) { // 第一行，设置顶部间距
                outRect.top = spacing
            }
            outRect.bottom = spacing // 设置底部间距
        } else {
            // 不包含边缘的情况下
            outRect.left = spacing * column / spanCount // 左边距：根据列数和当前列来分配
            outRect.right = spacing * (spanCount - column - 1) / spanCount // 右边距：对称处理

            if (position >= spanCount) {
                outRect.top = spacing // 只有不是第一行的才设置顶部间距
            }
        }
    }
}



