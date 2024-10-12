package com.shanai.base.view.actionsheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shanai.base.R

/**
 * @Author: lzm
 * @Date: 2024-10-10 12:22
 * @Description: TODO
 */
class ActionLoadingDialog @JvmOverloads constructor(
    context: Context,
    private var isActionShow: Boolean = false
) : Dialog(context, R.style.base_ActionLoadingDialog) {

    private var mTxtTip: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var isKeybackEnable = false
    private val handler = Handler()

    init {
        init(isActionShow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun init(withAction: Boolean) {
        if (withAction) {
            setContentView(R.layout.base_view_dialog_action)
            mTxtTip = findViewById(R.id.txt_tip)
            mProgressBar = findViewById(R.id.progressBar)
            mProgressBar?.indeterminateDrawable = ContextCompat.getDrawable(context, R.drawable.base_daisy_loading_rotate)
        } else {
            setContentView(R.layout.base_view_dialog_loading)
            mTxtTip = findViewById(R.id.txt_tip)
            mProgressBar = findViewById(R.id.progressBar)
            mProgressBar?.indeterminateDrawable = ContextCompat.getDrawable(context, R.drawable.original_loading_rotate)
        }
        setCanceledOnTouchOutside(false)
    }

    fun setPrompt(prompt: String?) {
        mTxtTip?.text = prompt
        mTxtTip?.visibility = if (prompt.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    fun setIndeterminateDrawable(resId: Int) {
        mProgressBar?.indeterminateDrawable = ContextCompat.getDrawable(context, resId)
    }

    fun setErrorInfo(error: String) {
        mProgressBar?.indeterminateDrawable = null
        mProgressBar?.setBackgroundResource(R.drawable.base_tips_negative)
        mTxtTip?.text = error
        handler.postDelayed({
            if (isShowing) {
                dismiss()
            }
        }, 3000L)
    }

    fun setKeybackEnable(enable: Boolean) {
        isKeybackEnable = enable
    }

    override fun dismiss() {
        isActionShow = false
        super.dismiss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && isActionShow && !isKeybackEnable) {
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    companion object {
        fun show(context: Context, prompt: String?): ActionLoadingDialog {
            return ActionLoadingDialog(context).apply {
                setPrompt(prompt)
                show()
            }
        }

        fun show(context: Context, resId: Int): ActionLoadingDialog {
            return ActionLoadingDialog(context).apply {
                setPrompt(context.resources.getString(resId))
                show()
            }
        }

        fun actionShow(context: Context, prompt: String?): ActionLoadingDialog {
            return ActionLoadingDialog(context, true).apply {
                setPrompt(prompt)
                show()
            }
        }

        fun actionShow(context: Context, resId: Int): ActionLoadingDialog {
            return ActionLoadingDialog(context, true).apply {
                setPrompt(context.resources.getString(resId))
                show()
            }
        }
    }
}