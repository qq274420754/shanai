package com.shanai.shanai.common.view.vcview

import android.text.method.PasswordTransformationMethod
import android.view.View


/**
 * @Author: lzm
 * @Date: 2024-10-05 23:19
 * @Description: TODO
 */
class AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View?): CharSequence {
        return PasswordCharSequence(source)
    }

    private inner class PasswordCharSequence(private val mSource: CharSequence) : CharSequence {

        override val length: Int
            get() = mSource.length

        override fun get(index: Int): Char {
            return '•'
        }

        override fun subSequence(start: Int, end: Int): CharSequence {
            return mSource.subSequence(start, end)
        }
    }


}