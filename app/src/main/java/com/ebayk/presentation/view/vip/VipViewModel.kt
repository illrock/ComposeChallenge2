package com.ebayk.presentation.view.vip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.data.network.response.ApiResult
import com.ebayk.data.network.response.ad.AdResponse
import com.ebayk.data.repository.AdRepository
import com.ebayk.presentation.view.util.ViewModelResult
import com.ebayk.presentation.view.vip.model.VipAd
import com.ebayk.util.print
import com.ebayk.util.toViewModelError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VipViewModel @Inject constructor(
    private val adRepository: AdRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result = MutableLiveData<ViewModelResult<VipAd>>(ViewModelResult.Loading)
    val result: LiveData<ViewModelResult<VipAd>> = _result

    internal fun loadAd(id: Long) {
        _result.value = ViewModelResult.Loading
        viewModelScope.launch(dispatcher) {
            adRepository.get(id).let { result ->
                withContext(Dispatchers.Main) {
                    handleAdResult(result)
                }
            }
        }
    }

    private fun handleAdResult(result: ApiResult<AdResponse>) = when (result) {
        is ApiResult.Success -> _result.value = ViewModelResult.Success(VipAd(result.data))
        is ApiResult.Error -> showError(result.exception)
    }

    private fun showError(e: Exception) {
        val vmError = e.toViewModelError()
        _result.value = vmError
        e.print()
    }
}