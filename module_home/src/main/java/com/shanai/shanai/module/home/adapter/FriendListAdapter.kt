package com.shanai.shanai.module.home.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.elvishew.xlog.XLog
import com.shanai.module.home.R
import com.shanai.module.home.databinding.HomeAdViewBinding
import com.shanai.module.home.databinding.HomeUserinfoItemViewBinding
import com.shanai.shanai.module.home.entity.FriendInfoBean
import com.shanai.shanai.module.home.utils.ToolsUtils
import javax.inject.Inject
import kotlin.math.log

/**
 * @Author: lzm
 * @Date: 2024-09-26 15:41
 * @Description: TODO
 */
class FriendListAdapter @Inject constructor() : BaseMultiItemAdapter<FriendInfoBean>() {

    // 类型 1 的 viewholder
    class ItemVH(val viewBinding: HomeUserinfoItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root)

    // 类型 2 的 viewholder
    class AdVH(val viewBinding: HomeAdViewBinding) : RecyclerView.ViewHolder(viewBinding.root)

    // 在 init 初始化的时候，添加多类型
    init {
        addItemType(USERINFO_TYPE, object : OnMultiItemAdapterListener<FriendInfoBean, ItemVH> { // 类型 1
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ItemVH {
                // 创建 viewholder
                val viewBinding = HomeUserinfoItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
                return ItemVH(viewBinding)
            }

            override fun onBind(holder: ItemVH, position: Int, item: FriendInfoBean?) {
                // 绑定 item 数据
                if (item != null) {
                    if (!item.headpho.isNullOrEmpty()) {
                        Glide.with(context)
                            .load(item.headpho)
                            .circleCrop() // 圆形裁剪
                            .skipMemoryCache(false)
                            .error(ToolsUtils.defaultHead(item.sex))
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(holder.viewBinding.ivHead)
                    }else{
                        holder.viewBinding.ivHead.setImageDrawable(null)  // 清空图片
                    }
                    if (!item.nickname.isNullOrEmpty()) {
                        holder.viewBinding.tvNickname.text = item.nickname
                    }else{
                        holder.viewBinding.tvNickname.text = ""
                    }
                    if (!item.memotext.isNullOrEmpty()) {
                        holder.viewBinding.tvMemotext.text = item.memotext
                    }else{
                        holder.viewBinding.tvMemotext.text = ""
                    }
                    if (!item.userIntroduce.isNullOrEmpty()) {
                        holder.viewBinding.tvUserinfo.text = item.userIntroduce
                    }else{
                        holder.viewBinding.tvUserinfo.text = ""
                    }
                    when(item.verify){
                        0->{
                            holder.viewBinding.tvUserrealpersonstatus.setImageDrawable(null)  // 清空图片
                        }
                        1->{
                            holder.viewBinding.tvUserrealpersonstatus.setImageResource(R.drawable.friennuserphone_ic)
                        }
                        2->{
                            holder.viewBinding.tvUserrealpersonstatus.setImageResource(R.drawable.friennuserrealname_ic)
                        }
                        3->{
                            holder.viewBinding.tvUserrealpersonstatus.setImageResource(R.drawable.friennuserrealshiming_ic)
                        }
                    }

                    // 首先重置所有控件的状态，防止继承之前的状态
                    holder.viewBinding.lavstatus.cancelAnimation()  // 重置 lavstatus 动画状态
                    holder.viewBinding.animationview.cancelAnimation()  // 重置 animationview 动画状态

                    holder.viewBinding.lavstatus.visibility = View.GONE
                    holder.viewBinding.ivOnlion.visibility = View.GONE
                    holder.viewBinding.animationview.visibility = View.GONE
                    // 根据 item.online 设置 lavstatus 和 ivOnlion 的状态
                    when (item.online) {
                        0 -> {
                            // 不在线
                            holder.viewBinding.ivOnlion.visibility = View.GONE
                            holder.viewBinding.lavstatus.visibility = View.GONE
                        }
                        1 -> {
                            // 直播状态
                            holder.viewBinding.ivOnlion.visibility = View.GONE
                            holder.viewBinding.lavstatus.visibility = View.VISIBLE
                            holder.viewBinding.lavstatus.setImageAssetsFolder("friend_social_live/images")
                            holder.viewBinding.lavstatus.setAnimation("friend_social_live/data.json")
                            holder.viewBinding.lavstatus.setRepeatCount(ValueAnimator.INFINITE)
                            holder.viewBinding.lavstatus.playAnimation()
                        }
                        2 -> {
                            // 交友状态
                            holder.viewBinding.ivOnlion.visibility = View.GONE
                            holder.viewBinding.lavstatus.visibility = View.VISIBLE
                            holder.viewBinding.lavstatus.setImageAssetsFolder("friend_social_chatting/images")
                            holder.viewBinding.lavstatus.setAnimation("friend_social_chatting/data.json")
                            holder.viewBinding.lavstatus.setRepeatCount(ValueAnimator.INFINITE)
                            holder.viewBinding.lavstatus.playAnimation()
                        }
                        3 -> {
                            // 普通在线状态
                            holder.viewBinding.ivOnlion.visibility = View.VISIBLE
                            holder.viewBinding.lavstatus.visibility = View.GONE
                        }
                    }

// 根据 position 的值设置 animationview 的状态
                    if (position % 10 == 1) {
                        // 展示特殊动效
                        holder.viewBinding.animationview.visibility = View.VISIBLE
                        holder.viewBinding.animationview.setAnimation("v_dashan_sixin.json")
                        holder.viewBinding.animationview.setMinAndMaxFrame(0, 47)
                        holder.viewBinding.animationview.setRepeatCount(ValueAnimator.INFINITE)
                        holder.viewBinding.animationview.playAnimation()
                    } else {
                        // 显示静态图片
                        holder.viewBinding.animationview.setVisibility(View.VISIBLE)
                        holder.viewBinding.animationview.setAnimation("v_dashan_sixin.json")
                        holder.viewBinding.animationview.setFrame(48)  // 设置静态帧
                        holder.viewBinding.animationview.setRepeatCount(0)  // 停止重复
                        holder.viewBinding.animationview.cancelAnimation()  // 停止动画
                    }




                }

            }
        }).addItemType(AD_TYPE, object : OnMultiItemAdapterListener<FriendInfoBean, AdVH> { // 类型 2
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): AdVH {
                // 创建 viewholder
                val viewBinding = HomeAdViewBinding.inflate(LayoutInflater.from(context), parent, false)
                return AdVH(viewBinding)
            }

            override fun onBind(holder: AdVH, position: Int, item: FriendInfoBean?) {
                // 绑定 item 数据
            }

            override fun isFullSpanItem(itemType: Int): Boolean {
                // 使用GridLayoutManager时，此类型的 item 是否是满跨度
                return true;
            }

        }).onItemViewType { position, list -> // 根据数据，返回对应的 ItemViewType
            list[position].itemtype
        }
    }

    companion object {
        private const val USERINFO_TYPE = 0
        private const val AD_TYPE = 1
    }


}