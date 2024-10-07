package com.shanai.shanai.common.view.vcview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.TextViewCompat
import com.shanai.common.R
import java.lang.reflect.Field


/**
 * @Author: lzm
 * @Date: 2024-10-05 23:15
 * @Description: TODO
 */
class VerificationCodeView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs), TextWatcher, View.OnKeyListener,
    OnFocusChangeListener {
    private val mContext = context
    private var onCodeFinishListener: OnCodeFinishListener? = null

    /**
     * 输入框数量
     */
    private var mEtNumber: Int

    /**
     * 输入框类型
     */
    private var mEtInputType: VCInputType

    /**
     * 输入框的宽度
     */
    private var mEtWidth: Int

    /**
     * 输入框的高度
     */
    private var mEtHeight: Int

    /**
     * 文字颜色
     */
    private var mEtTextColor: Int

    /**
     * 文字大小
     */
    private var mEtTextSize: Float

    /**
     * 输入框背景
     */
    private var mEtTextBg: Int

    /**
     * 输入框间距
     */
    private var mEtSpacing = 0

    /**
     * 平分后的间距
     */
    private var mEtBisectSpacing = 0

    /**
     * 判断是否平分
     */
    private val isBisect: Boolean

    /**
     * 是否显示光标
     */
    private val cursorVisible: Boolean

    /**
     * 光标样式
     */
    private var mCursorDrawable: Int

    /**
     * 输入框宽度
     */
    private var mViewWidth = 0

    /**
     * 输入框间距
     */
    private val mViewMargin = 0

    fun getOnCodeFinishListener(): OnCodeFinishListener? {
        return onCodeFinishListener
    }

    fun setOnCodeFinishListener(onCodeFinishListener: OnCodeFinishListener?) {
        this.onCodeFinishListener = onCodeFinishListener
    }

    fun getmEtNumber(): Int {
        return mEtNumber
    }

    fun setmEtNumber(mEtNumber: Int) {
        this.mEtNumber = mEtNumber
    }

    fun getmEtInputType(): VCInputType {
        return mEtInputType
    }

    fun setmEtInputType(mEtInputType: VCInputType) {
        this.mEtInputType = mEtInputType
    }

    fun getmEtWidth(): Int {
        return mEtWidth
    }

    fun setmEtWidth(mEtWidth: Int) {
        this.mEtWidth = mEtWidth
    }

    fun getmEtHeight(): Int {
        return mEtHeight
    }

    fun setmEtHeight(mEtHeight: Int) {
        this.mEtHeight = mEtHeight
    }


    fun getmEtTextColor(): Int {
        return mEtTextColor
    }

    fun setmEtTextColor(mEtTextColor: Int) {
        this.mEtTextColor = mEtTextColor
    }

    fun getmEtTextSize(): Float {
        return mEtTextSize
    }

    fun setmEtTextSize(mEtTextSize: Float) {
        this.mEtTextSize = mEtTextSize
    }

    fun getmEtTextBg(): Int {
        return mEtTextBg
    }

    fun setmEtTextBg(mEtTextBg: Int) {
        this.mEtTextBg = mEtTextBg
    }

    fun getmCursorDrawable(): Int {
        return mCursorDrawable
    }

    fun setmCursorDrawable(mCursorDrawable: Int) {
        this.mCursorDrawable = mCursorDrawable
    }

    enum class VCInputType {
        /**
         * 数字类型
         */
        NUMBER,

        /**
         * 数字密码
         */
        NUMBERPASSWORD,

        /**
         * 文字
         */
        TEXT,

        /**
         * 文字密码
         */
        TEXTPASSWORD,
    }

    init {
        @SuppressLint("Recycle", "CustomViewStyleable") val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.common_vericationCodeView)
        mEtNumber = typedArray.getInteger(R.styleable.common_vericationCodeView_common_vcv_et_number, 4)
        val inputType = typedArray.getInt(
            R.styleable.common_vericationCodeView_common_vcv_et_inputType,
            VCInputType.NUMBER.ordinal
        )
        mEtInputType = VCInputType.values()[inputType]
        mEtWidth =
            typedArray.getDimensionPixelSize(R.styleable.common_vericationCodeView_common_vcv_et_width, 120)
        mEtHeight =
            typedArray.getDimensionPixelSize(R.styleable.common_vericationCodeView_common_vcv_et_height, 120)
        mEtTextColor =
            typedArray.getColor(R.styleable.common_vericationCodeView_common_vcv_et_text_color, Color.BLACK)
        mEtTextSize =
            typedArray.getDimensionPixelSize(R.styleable.common_vericationCodeView_common_vcv_et_text_size, 16)
                .toFloat()
        mEtTextBg = typedArray.getResourceId(
            R.styleable.common_vericationCodeView_common_vcv_et_bg,
            R.drawable.common_et_logincode
        )
        mCursorDrawable = typedArray.getResourceId(
            R.styleable.common_vericationCodeView_common_vcv_et_cursor,
            R.drawable.common_etcurson
        )
        cursorVisible =
            typedArray.getBoolean(R.styleable.common_vericationCodeView_common_vcv_et_cursor_visible, true)

        isBisect = typedArray.hasValue(R.styleable.common_vericationCodeView_common_vcv_et_spacing)
        if (isBisect) {
            mEtSpacing =
                typedArray.getDimensionPixelSize(R.styleable.common_vericationCodeView_common_vcv_et_spacing, 0)
        }
        initView()
        //释放资源
        typedArray.recycle()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {
        for (i in 0 until mEtNumber) {
            val editText = EditText(mContext)
            initEditText(editText, i)
            addView(editText)
            //设置第一个editText获取焦点
            if (i == 0) {
                editText.isFocusable = true
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initEditText(editText: EditText, i: Int) {
        editText.layoutParams = getETLayoutParams(i)
        editText.textAlignment = TEXT_ALIGNMENT_CENTER
        editText.gravity = Gravity.CENTER
        editText.id = i
        editText.isCursorVisible = false
        editText.maxEms = 1
        editText.setTextColor(mEtTextColor)
        editText.textSize = mEtTextSize
        editText.isCursorVisible = cursorVisible
        editText.maxLines = 1
        editText.filters = arrayOf<InputFilter>(LengthFilter(1))
        when (mEtInputType) {
            VCInputType.NUMBER -> editText.inputType = InputType.TYPE_CLASS_NUMBER
            VCInputType.NUMBERPASSWORD -> {
                editText.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                editText.transformationMethod = AsteriskPasswordTransformationMethod()
            }

            VCInputType.TEXT -> editText.inputType = InputType.TYPE_CLASS_TEXT
            VCInputType.TEXTPASSWORD -> editText.inputType = InputType.TYPE_CLASS_NUMBER
            else -> editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        editText.setPadding(0, 0, 0, 0)
        editText.setOnKeyListener(this)
        editText.setBackgroundResource(mEtTextBg)
        setEditTextCursorDrawable(editText)
        editText.addTextChangedListener(this)
        editText.setOnKeyListener(this)
        editText.onFocusChangeListener = this
    }

    /**
     * 获取EditText 的 LayoutParams
     */
    fun getETLayoutParams(i: Int): LayoutParams {
        val layoutParams = LayoutParams(mEtWidth, mEtHeight)
        if (!isBisect) {
            //平分Margin，把第一个EditText跟最后一个EditText的间距同设为平分
            mEtBisectSpacing = (mViewWidth - mEtNumber * mEtWidth) / (mEtNumber + 1)
            if (i == 0) {
                layoutParams.leftMargin = mEtBisectSpacing
                layoutParams.rightMargin = mEtBisectSpacing / 2
            } else if (i == mEtNumber - 1) {
                layoutParams.leftMargin = mEtBisectSpacing / 2
                layoutParams.rightMargin = mEtBisectSpacing
            } else {
                layoutParams.leftMargin = mEtBisectSpacing / 2
                layoutParams.rightMargin = mEtBisectSpacing / 2
            }
        } else {
            if (i == 0) {
                layoutParams.leftMargin = 0
                layoutParams.rightMargin = mEtSpacing / 2
            }else{
                layoutParams.leftMargin = mEtSpacing / 2
                layoutParams.rightMargin = mEtSpacing / 2
            }

        }

        layoutParams.gravity = Gravity.CENTER
        return layoutParams
    }


    @SuppressLint("SoonBlockedPrivateApi", "DiscouragedPrivateApi")
    fun setEditTextCursorDrawable(editText: EditText) {

        // 针对 API 29 及以上，使用公开的 setTextCursorDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                editText.setTextCursorDrawable(mCursorDrawable)
            } catch (e: Exception) {
                e.printStackTrace() // 捕获异常，避免崩溃
            }
        } else {
            // 针对 API 29 以下的设备，使用反射修改光标样式
            try {
                val field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field.set(editText, mCursorDrawable)
            } catch (e: Exception) {
                e.printStackTrace() // 捕获异常，避免崩溃
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewWidth = measuredWidth
        updateETMargin()
    }

    private fun updateETMargin() {
        for (i in 0 until mEtNumber) {
            val editText = getChildAt(i) as EditText
            editText.layoutParams = getETLayoutParams(i)
        }
    }


    override fun onFocusChange(view: View?, b: Boolean) {
        if (b) {
            focus()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (s.length != 0) {
            focus()
        }
        if (onCodeFinishListener != null) {
            onCodeFinishListener!!.onTextChange(this, getResult())
            //如果最后一个输入框有字符，则返回结果
            val lastEditText = getChildAt(mEtNumber - 1) as EditText
            if (lastEditText.text.length > 0) {
                onCodeFinishListener!!.onComplete(this, getResult())
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
            backFocus()
        }
        return false
    }

    override fun setEnabled(enabled: Boolean) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.isEnabled = enabled
        }
    }

    /**
     * 获取焦点
     */
    private fun focus() {
        val count = childCount
        var editText: EditText
        //利用for循环找出还最前面那个还没被输入字符的EditText，并把焦点移交给它。
        for (i in 0 until count) {
            editText = getChildAt(i) as EditText
            if (editText.text.length < 1) {
                if (cursorVisible) {
                    editText.isCursorVisible = true
                } else {
                    editText.isCursorVisible = false
                }
                editText.requestFocus()
                return
            } else {
                editText.isCursorVisible = false
                if (i == count - 1) {
                    editText.requestFocus()
                }
            }
        }
    }

    private fun backFocus() {
        var editText: EditText
        //循环检测有字符的`editText`，把其置空，并获取焦点。
        for (i in mEtNumber - 1 downTo 0) {
            editText = getChildAt(i) as EditText
            if (editText.text.length >= 1) {
                editText.setText("")
                if (cursorVisible) {
                    editText.isCursorVisible = true
                } else {
                    editText.isCursorVisible = false
                }
                editText.requestFocus()
                return
            }
        }
    }

    private fun getResult(): String {
        val stringBuffer = StringBuilder()
        var editText: EditText
        for (i in 0 until mEtNumber) {
            editText = getChildAt(i) as EditText
            stringBuffer.append(editText.text)
        }
        return stringBuffer.toString()
    }

    interface OnCodeFinishListener {
        /**
         * 文本改变
         */
        fun onTextChange(view: View?, content: String?)

        /**
         * 输入完成
         */
        fun onComplete(view: View?, content: String?)
    }

    /**
     * 清空验证码输入框
     */
    fun setEmpty() {
        var editText: EditText
        for (i in mEtNumber - 1 downTo 0) {
            editText = getChildAt(i) as EditText
            editText.setText("")
            if (i == 0) {
                if (cursorVisible) {
                    editText.isCursorVisible = true
                } else {
                    editText.isCursorVisible = false
                }
                editText.requestFocus()
            }
        }
    }
}