package com.shanai.common.ui.fragment

import androidx.viewbinding.ViewBinding
import com.shanai.base.mvvm.v.BaseFrameFragment
import com.shanai.base.mvvm.vm.BaseViewModel

/**
 * Fragment基类
 *
 * @author LZM
 * @since 8/27/20
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameFragment<VB, VM>()