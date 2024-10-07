package com.shanai.shanai.common.view

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import androidx.appcompat.widget.AppCompatTextView
import java.util.regex.Pattern
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.shanai.shanai.common.PaseJsonData

/**
 * @Author: lzm
 * @Date: 2024-10-03 22:10
 * @Description: TODO
 */
class AlxUrlTextView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    var spanColor = "#e97a20"

    fun setText(text: String?) {
        super.setText("")
        if (text == null || text.length == 0) return
        val output: Array<Array<Any?>>? = getUrlFromJDP(text)
        var urlCount = 0
        if (output == null || output.size == 0 || output[0] == null || output[0]!!.size == 0) {
            super.setText(text)
            return
        }
        urlCount = output[0]!!.size
        Log.i("AlexUrl", "一共有" + urlCount + "个url")
        var remainText = text
        var lastStart = 0 //截取到一部分后截掉部分的长度
        for (i in 0 until urlCount) {
            val blueText = output[0]!![i] as String //带下划线的文字
            val href = output[1]!![i] as String //下划线文字所对应的url连接
            val start = output[2]!![i] as Int //<a>标签在源字符串的起始位置
            val end = output[3]!![i] as Int //<a>标签在源字符串的结束位置
            val spannableString = SpannableString(blueText)
            spannableString.setSpan(object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
                //在这里定义点击下划线文字的点击事件，不一定非要打开浏览器
                override fun onClick(widget: View) {
                    PaseJsonData.parseWebViewTag(href, getContext())
                }
            }, 0, blueText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setHighlightColor(0x00000000)
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor(spanColor)),
                0,
                blueText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val subStart = start - lastStart
            val front = remainText!!.substring(0, subStart) //截取出一段文字+一段url
            Log.i("Alex", "起始位置" + (end - lastStart))
            remainText = remainText.substring(end - lastStart, remainText.length) //剩下的部分
            lastStart = end
            Log.i("Alex", "front是$front")
            Log.i("Alex", "spann是$spannableString")
            Log.i("Alex", "remain是$remainText")
            if (front.length > 0) append(front)
            append(spannableString)
        }
        if (remainText != null && remainText.length > 0) append(remainText)
        setMovementMethod(LinkMovementMethod.getInstance()) //响应点击事件
        setOnLongClickListener(OnLongClickListener { true })
    }

    fun getUrlFromJDP(source: String?): Array<Array<Any?>>? {
        val hosts = ArrayList<String?>(4)
        val urls = ArrayList<String?>(4)
        val starts = ArrayList<Int?>(4)
        val ends = ArrayList<Int?>(4)
        val pattern = Pattern.compile("<a href=\".*?\">(.*?)</a>") //首先将a标签分离出来
        val matcher = pattern.matcher(source)
        var i = 0
        while (matcher.find()) {
            val raw = matcher.group(0)
            val url_pattern = Pattern.compile("<a href=\"(.*?)\">") //将href分离出来
            val url_matcher = url_pattern.matcher(raw)
            try {
                if (url_matcher.find()) {
                    val url = url_matcher.group(1)
                    Log.i("Alex", "真实url是$url") //括号里面的
                    urls.add(i, url)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            var host: String? = null //将要显示的文字分离出来
            try {
                host = matcher.group(1)
            } catch (e: Exception) {
                e.printStackTrace()
                continue
            }
            Log.i("Alex", "蓝色文字是$host") //括号里面的
            hosts.add(i, host)
            starts.add(i, matcher.start())
            ends.add(i, matcher.end())
            Log.i(
                "Alex",
                "字符串起始下标是" + matcher.start() + "结尾下标是" + matcher.end()
            ) //匹配出的字符串在源字符串的位置
            i++
        }
        if (hosts.size == 0) {
            Log.i("Alex", "没有发现url")
            return null
        }
        val outputs = Array(4) {
            arrayOfNulls<Any>(
                hosts.size
            )
        } //第一个下标是内容的分类，第二个下标是url的序号
        outputs[0] = hosts.toTypedArray<Any?>() //下标0是蓝色的文字
        outputs[1] = urls.toTypedArray<Any?>() //下标1是url
        outputs[2] = starts.toTypedArray<Any?>() //下标2是<a>标签起始位置
        outputs[3] = ends.toTypedArray<Any?>() //下标3是<a>标签结束位置
        return outputs
    }
}
