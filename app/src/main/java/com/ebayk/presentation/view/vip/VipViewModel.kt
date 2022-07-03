package com.ebayk.presentation.view.vip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.R
import com.ebayk.data.network.response.ApiResult
import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.data.repository.AdRepository
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class VipViewModel @Inject constructor(
    private val adRepository: AdRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result = MutableLiveData<ViewModelResult<AdResponse>>()
    val result: LiveData<ViewModelResult<AdResponse>> = _result

    init {
        loadAd(AD_ID)
    }

    private fun loadAd(id: Long) {
        _result.value = ViewModelResult.Loading
        viewModelScope.launch(dispatcher) {
            adRepository.get(id).let { result ->
                withContext(Dispatchers.Main) {
                    handleAdResult(result)
                }
            }
        }
    }

    private fun handleAdResult(result: ApiResult<AdResponse>) {
        when (result) {
            is ApiResult.Success -> _result.value = ViewModelResult.Success(result.data)
            is ApiResult.Error -> showError(result.exception)
        }
    }

    private fun showError(e: Exception) {
        val vmError = when {
            e is UnknownHostException -> ViewModelResult.Error(errorRes = R.string.error_connection)
            e.message != null -> ViewModelResult.Error(errorMessage = e.message)
            else -> ViewModelResult.Error(errorRes = R.string.error_unknown)
        }
        _result.value = vmError
        e.print()
    }

    companion object {
        private const val AD_ID = 1118635128L
    }
}