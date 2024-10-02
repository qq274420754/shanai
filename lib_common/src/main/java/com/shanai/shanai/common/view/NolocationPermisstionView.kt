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
class NolocationPermisstionView @JvmOverloads constructor(
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
        LayoutInflater.from(context).inflate(R.layout.view_nolocationpermission, this, true)

        // Initialize views
        icon = findViewById(R.id.iv_nolocation)
        title = findViewById(R.id.tv_openlocation)
        hint = findViewById(R.id.tv_nolocationhint)
        button = findViewById(R.id.rb_openlocation)

        // Retrieve custom attributes
//        context.theme.obtainStyledAttributes(attrs, R.styleable.common_NoLocation, 0, 0).apply {
//            try {
//                // Set icon
//                val iconResId = getResourceId(R.styleable.common_NoLocation_common_iconSrc, R.drawable.common_mascot_ic)
//                icon.setImageDrawable(ContextCompat.getDrawable(context, iconResId))
//
//                // Set title
//                val titleText = getString(R.styleable.common_NoLocation_common_titleText) ?: "开启附近功能"
//                title.text = titleText
//
//                // Set hint
//                val hintText = getString(R.styleable.common_NoLocation_common_hintText) ?: "需要到手机系统设置中，开启位置权限，\n才可以看到附近的人"
//                hint.text = hintText
//
//                // Set button text
//                val buttonText = getString(R.styleable.common_NoLocation_common_buttonText) ?: "去开启"
//                button.text = buttonText
//
//            } finally {
//                recycle()
//            }
//        }

        // Set button click listener
        button.setOnClickListener {
            listener?.onButtonClick()
        }
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
