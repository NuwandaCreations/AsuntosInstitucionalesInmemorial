package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.ui.home.HomeViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage.ProtocolStorageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiDomain = module {

}

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel {
        ProtocolStorageViewModel(
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