package siyateagan.example.translatorapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import siyateagan.example.translatorapp.TranslatorApp
import siyateagan.example.translatorapp.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class, ViewModelFactoryModule::class,
        ActivitiesModule::class, AndroidInjectionModule::class, NetworkModule::class,
        SharedPreferencesModule::class, ObservableFieldModule::class, RoomModule::class]
)

interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(application: TranslatorApp)
}
