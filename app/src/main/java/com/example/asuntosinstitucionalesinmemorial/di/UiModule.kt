package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.view.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiDomain = module {

}

val viewModelModule = module {
    viewModel {
        HomeViewModel()
    }
}