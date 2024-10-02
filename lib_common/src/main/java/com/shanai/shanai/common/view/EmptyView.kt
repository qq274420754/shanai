package com.shanai.shanai.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shanai.common.R


/**
 * @Author: lzm
 * @Date: 2024-09-28 16:07
 * @Description: TODO
 */
class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var icon: ImageView
    private var title: TextView
    private var hint: TextView
    private var button: Button
    private var listener: OnEmptyViewClickListener? = null

    init {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true)

        // Initialize views
        icon = findViewById(R.id.iv_empty)
        title = findViewById(R.id.tv_emptyuser)
        hint = findViewById(R.id.tv_emptyuserhint)
        button = findViewById(R.id.rb_emptyuserrefre)

        // Retrieve custom attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.common_EmptyView, 0, 0).apply {
            try {
                // Set icon
                val iconResId = getResourceId(R.styleable.common_EmptyView_common_iconSrc, R.drawable.common_mascot_ic)
                icon.setImageDrawable(ContextCompat.getDrawable(context, iconResId))

                // Set title
                val titleText = getString(R.styleable.common_EmptyView_common_titleText) ?: "用户不见了？"
                title.text = titleText

                // Set hint
                val hintText = getString(R.styleable.common_EmptyView_common_hintText) ?: "重新获取试试吧"
                hint.text = hintText

                // Set button text
                val buttonText = getString(R.styleable.common_EmptyView_common_buttonText) ?: "重新获取"
                button.text = buttonText

            } finally {
                recycle()
            }
        }

        // Set button click listener
        button.setOnClickListener {
            listener?.onButtonClick()
        }
    }

    // 设置 icon 的方法
    fun setIcon(resourceId: Int) {
        icon.setImageResource(resourceId)
    }

    // 设置 titleText 的方法
    fun setTitleText(text: String) {
        title.text = text
    }


    // 设置 titleText 的方法
    fun setTitleHintText(text: String) {
        hint.text = text
    }

    // 设置 buttonText 的方法
    fun setButtonText(text: String) {
        button.text = text
    }


    // Public setter for the click listener
    fun setOnEmptyViewClickListener(listener: OnEmptyViewClickListener) {
        this.listener = listener
    }

    // Interface for handling button clicks
    interface OnEmptyViewClickListener {
        fun onButtonClick()
    }
}
