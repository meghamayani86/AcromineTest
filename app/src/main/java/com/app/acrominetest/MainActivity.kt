package com.app.acrominetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.acrominetest.adapter.AbbreviationAdapter
import com.app.acrominetest.databinding.ActivityMainBinding
import com.app.acrominetest.models.AcromineResponse
import com.app.acrominetest.utils.NetworkResult
import com.app.acrominetest.viewmodels.AcromineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = AbbreviationAdapter()
    private lateinit var acromineViewModel: AcromineViewModel

    private var myObserver: Observer<NetworkResult<List<AcromineResponse>>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get a reference to the binding object and extend the views.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Create an instance of the ViewModel.
        acromineViewModel = ViewModelProvider(this)[AcromineViewModel::class.java]

        binding.apply {

            // To use the View Model with data binding, you have to explicitly
            // give the binding object a reference to it.
            abbreviationViewModel = acromineViewModel

            // Specify the current activity as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe LiveData updates.
            lifecycleOwner = this@MainActivity

            abbreviationList.adapter = adapter
            removeTextButton.setOnClickListener {
                with(binding) { editTextInput.setText("") }
                clearList()
            }

        }

        // Add an Observer for fetch data from live data
        Observer(function = this::setAbbreviation).also { myObserver = it }

        myObserver?.let { acromineViewModel.acromineLiveData.observe(this, it) }

    }

    private fun setAbbreviation(it: NetworkResult<List<AcromineResponse>>) {
        when (it) {
            is NetworkResult.Success<*> -> {
                it.data?.forEach { item ->
                    adapter.submitList(item.lfs)
                    acromineViewModel.inputAbbreviation.value = true
                }
            }
            is NetworkResult.Error<*> -> {
                acromineViewModel.inputAbbreviation.value = false
                acromineViewModel.abbreviationErrorMessage.value = it.message
            }
            is NetworkResult.Loading -> {

            }
        }
    }

    private fun clearList() {
        adapter.submitList(emptyList())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}