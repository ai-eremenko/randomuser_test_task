package com.example.randomuser_test_task

import android.app.Application
import com.example.randomuser_test_task.data.dataModule
import com.example.randomuser_test_task.domain.interactorModule
import com.example.randomuser_test_task.feature.generateuser.generateUserModule
import com.example.randomuser_test_task.feature.userdetail.userDetailModule
import com.example.randomuser_test_task.feature.userslist.usersListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                interactorModule,
                generateUserModule,
                userDetailModule,
                usersListModule
            )
        }
    }
}