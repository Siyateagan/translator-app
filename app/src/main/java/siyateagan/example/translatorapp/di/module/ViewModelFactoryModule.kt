package siyateagan.example.translatorapp.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import siyateagan.example.translatorapp.di.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}