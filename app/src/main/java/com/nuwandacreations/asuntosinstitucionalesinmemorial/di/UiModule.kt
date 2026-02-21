package com.nuwandacreations.asuntosinstitucionalesinmemorial.di

import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.CreateEventViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests.GuestDetailViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.home.HomeViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.materialstorage.MaterialStorageViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.regalosstorage.RegalosStorageViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.storagedetail.StorageDetailViewModel
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
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        CreateEventViewModel(
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
            get(),
            get()
        )
    }
}