package siyateagan.example.translatorapp.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import siyateagan.example.translatorapp.TranslatorApp
import siyateagan.example.translatorapp.di.module.ActivitiesModule
import siyateagan.example.translatorapp.di.module.ViewModelFactoryModule
import siyateagan.example.translatorapp.di.module.ViewModelModule

@Component(
    modules = [ViewModelModule::class, ViewModelFactoryModule::class,
        ActivitiesModule::class, AndroidInjectionModule::class]
)

interface AppComponent {
    fun inject(application: TranslatorApp)
}
