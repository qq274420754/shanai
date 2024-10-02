package com.shanai.base.utils

/**
 * @Author: lzm
 * @Date: 2024-09-28 9:28
 * @Description: TODO
 */
sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Success<T>(val data: T) : UiState<T>()
    class Empty<T> : UiState<T>()
    class Error<T>(val message: String) : UiState<T>()
}
