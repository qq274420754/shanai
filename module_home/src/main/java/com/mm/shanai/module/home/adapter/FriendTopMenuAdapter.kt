package com.mm.shanai.module.home.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.shanai.module.home.R
import com.mm.shanai.module.home.entity.FriendTopMenuItem
import org.w3c.dom.Text

/**
 * @Author: lzm
 * @Date: 2024-09-25 13:32
 * @Description: TODO
 */
class FriendTopMenuAdapter : BaseQuickAdapter<FriendTopMenuItem, QuickViewHolder>() {

    private var iconGaryMap: Map<String, Int> = mapOf(
        "rank" to R.drawable.home_topmenu_rank,
        "task" to R.drawable.home_topmenu_task,
        "invite" to R.drawable.home_topmenu_invite,
        "videochat" to R.drawable.home_topmenu_videochat
    )

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: FriendTopMenuItem?) {
        (holder.getView<TextView>(R.id.title)).text = item?.title
        this.iconGaryMap.get(item?.key)?.let { (holder.getView<FrameLayout>(R.id.topmenubg)).setBackgroundResource(it) };
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.home_layout_friend_top_menu, parent)
    }

}