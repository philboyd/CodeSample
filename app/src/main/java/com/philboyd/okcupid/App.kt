package com.philboyd.okcupid

import android.app.Application
import com.philboyd.okcupid.di.AppContainer

class App : Application() {

    val appContainer = AppContainer()
}
