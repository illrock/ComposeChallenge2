package com.ebayk.presentation.view.util

import com.ebayk.R

sealed class ViewModelResult<out R> {
    object Loading : ViewModelResult<Nothing>()
    data class Success<out T>(val data: T) : ViewModelResult<T>()
    data class Error(
        val errorRes: Int? = null,
        val errorMessage: String? = null,
        val iconRes: Int = R.drawable.ic_mood_bad
    ) : ViewModelResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorRes=$errorRes, errorMessage=$errorMessage]"
        }
    }
}