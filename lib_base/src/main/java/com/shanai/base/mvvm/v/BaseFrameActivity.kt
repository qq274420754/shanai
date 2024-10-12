package com.shanai.base.mvvm.v

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.permissionx.guolindev.PermissionX
import com.shanai.base.R
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.*
import com.shanai.base.utils.network.AutoRegisterNetListener
import com.shanai.base.utils.network.NetworkStateChangeListener
import com.shanai.base.utils.network.NetworkTypeEnum
import com.shanai.base.view.actionsheet.ActionLoadingDialog
import me.jessyan.autosize.AutoSizeCompat

/**
 * Activity基类
 *
 * @author LZM
 * @since 8/27/20
 */
abstract class BaseFrameActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    FrameView<VB>, NetworkStateChangeListener {


    protected abstract val mViewModel: VM

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) { createVB() }

    // ActionLoadingDialog初始化
    protected var mActionLoadingDialog: ActionLoadingDialog? = null

    // 软键盘的管理器
    private val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * 是否有 [RegisterEventBus] 注解，避免重复调用 [Class.isAnnotation]
     */
    private var mHaveRegisterEventBus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)

        // 根据子类是否有 RegisterEventBus 注解決定是否进行注册 EventBus
        if (javaClass.isAnnotationPresent(RegisterEventBus::class.java)) {
            mHaveRegisterEventBus = true
            EventBusUtils.register(this)
        }

        setStatusBar()
        mBinding.initView()
        initNetworkListener()
        initObserve()
        initRequestData()
    }

    // 显示加载框
    fun showLoading(text: String) {
        if (mActionLoadingDialog == null || !mActionLoadingDialog!!.isShowing) {
            mActionLoadingDialog = ActionLoadingDialog(this)
            mActionLoadingDialog!!.setPrompt(text)
            mActionLoadingDialog!!.show()
        }
    }

    // 显示加载框（通过资源ID）
    fun showLoading(resId: Int) {
        showLoading(getString(resId))
    }

    // 隐藏加载框
    fun dismissLoading() {
        mActionLoadingDialog?.dismiss()
    }


    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(this))
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    open fun setStatusBar() {}

    /**
     * 显示toast提示
     */
    open fun showShortToast(text: String) {
        toast(text,Toast.LENGTH_SHORT)
    }

    open fun showLongToast(text: String) {
        toast(text,Toast.LENGTH_LONG)
    }

    /**
     * 请求权限的统一方法，子类可以直接调用此方法
     */
    protected fun requestPermissions(permissions: List<String>, callback: (Boolean, List<String>, List<String>) -> Unit) {
        PermissionX.init(this)
            .permissions(permissions)
            .request { allGranted, grantedList, deniedList ->
                callback(allGranted, grantedList, deniedList)
            }
    }
    /**
     * 网络类型更改回调
     * @param type Int 网络类型
     * @return Unit
     */
    override fun networkTypeChange(type: NetworkTypeEnum) {}

    /**
     * 网络连接状态更改回调
     * @param isConnected Boolean 是否已连接
     * @return Unit
     */
    override fun networkConnectChange(isConnected: Boolean) {
        toast(if (isConnected) getString(R.string.base_network_connected) else getString(R.string.base_network_disconnected))
    }

    override fun onDestroy() {
        // 根据子类是否有 RegisterEventBus 注解决定是否进行注册 EventBus
        if (mHaveRegisterEventBus) {
            EventBusUtils.unRegister(this)
        }
        dismissLoading()
        super.onDestroy()
    }

    override fun getResources(): Resources {
        // 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
        // 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
        // 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        }
        return super.getResources()
    }

    /**
     * 显示软键盘
     * @param view 需要获取焦点的视图
     */
    fun showSoftKeyboard(view: View) {
        view.requestFocus()
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftKeyboard() {
        currentFocus?.let { view ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 判断软键盘是否显示
     */
    fun isSoftKeyboardVisible(): Boolean {
        return inputMethodManager.isActive
    }
}