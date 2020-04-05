package siyateagan.example.translatorapp.di.module

import androidx.databinding.ObservableField
import dagger.Module
import dagger.Provides

@Module
class ObservableFieldModule {
    @Provides
    fun provideObservableField(): ObservableField<String> {
        return ObservableField("")
    }
}