package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.guests.GuestDetailViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.home.HomeViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.materialstorage.MaterialStorageViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.regalosstorage.RegalosStorageViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.storagedetail.StorageDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        RegalosStorageViewModel(
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
    viewModel {
        MaterialStorageViewModel(
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
    viewModel {
        EventsViewModel(
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
    viewModel {
        StorageDetailViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        GuestDetailViewModel(
            get(),
            get(),
            get()
        )
    }
}