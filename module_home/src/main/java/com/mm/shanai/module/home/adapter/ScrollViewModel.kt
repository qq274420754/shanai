package com.mm.shanai.module.home.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: lzm
 * @Date: 2024-10-02 20:32
 * @Description: TODO
 */
class ScrollViewModel : ViewModel() {
    private val _scrollEvent = MutableLiveData<Int>()
    val scrollEvent: LiveData<Int> get() = _scrollEvent

    fun onRecyclerViewScrolled(dy: Int) {
        _scrollEvent.value = dy
    }
}