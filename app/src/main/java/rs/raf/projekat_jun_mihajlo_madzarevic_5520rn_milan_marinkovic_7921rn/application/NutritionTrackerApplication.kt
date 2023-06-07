package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import timber.log.Timber
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.modules.coreModule

class NutritionTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        initTimber()
        initKoin()
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin(){
        val modules = listOf(
            coreModule
        )
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@NutritionTrackerApplication)
            androidFileProperties()
            fragmentFactory()
        }
    }
}