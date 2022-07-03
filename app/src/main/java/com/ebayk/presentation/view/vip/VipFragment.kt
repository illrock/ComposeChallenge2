package com.ebayk.presentation.view.vip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ebayk.databinding.FragmentVipBinding
import com.ebayk.presentation.view.util.ViewModelResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VipFragment : Fragment() {
    private val vm: VipViewModel by viewModels()
    private var _binding: FragmentVipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo srl?

        vm.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ViewModelResult.Loading -> {}
                is ViewModelResult.Success -> binding.tvTitle.text = result.data.title
                is ViewModelResult.Error -> {}
            }
        }
    }
}