package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.ui.home.HomeViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.regalosstorage.RegalosStorageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiDomain = module {

}

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel {
        RegalosStorageViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}