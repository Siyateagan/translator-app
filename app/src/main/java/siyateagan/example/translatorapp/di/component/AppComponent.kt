package siyateagan.example.translatorapp.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import siyateagan.example.translatorapp.TranslatorApp
import siyateagan.example.translatorapp.di.module.ActivitiesModule
import siyateagan.example.translatorapp.di.module.NetworkModule
import siyateagan.example.translatorapp.di.module.ViewModelFactoryModule
import siyateagan.example.translatorapp.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class, ViewModelFactoryModule::class,
        ActivitiesModule::class, AndroidInjectionModule::class, NetworkModule::class]
)

interface AppComponent {
    fun inject(application: TranslatorApp)
}
