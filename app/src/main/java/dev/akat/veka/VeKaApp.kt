package dev.akat.veka

import android.app.Application
import dev.akat.veka.di.AppComponent
import dev.akat.veka.di.AppModule
import dev.akat.veka.di.DaggerAppComponent

class VeKaApp : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}
