package com.shanai.base.utils

import androidx.annotation.StringRes
import com.shanai.base.BaseApplication.Companion.application as app

fun getString(@StringRes stringRes: Int): String {
    return app.getString(stringRes)
}