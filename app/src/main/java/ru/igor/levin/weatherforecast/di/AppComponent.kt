package ru.igor.levin.weatherforecast.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.igor.levin.weatherforecast.data.di.RepositoryModule
import ru.igor.levin.weatherforecast.data.network.NetworkModule
import ru.igor.levin.weatherforecast.presentation.di.PresenterModule
import ru.igor.levin.weatherforecast.presentation.view.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

//   Альтернативный вариант создания компонента
//
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun appContext(context: Context): Builder
//        fun build(): AppComponent
//    }

    fun inject(activity: MainActivity)
}