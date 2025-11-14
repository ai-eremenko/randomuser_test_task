package com.example.randomuser_test_task.data

import android.app.Application
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.randomuser_test_task.data.db.AppDatabase
import com.example.randomuser_test_task.data.generateuser.GenerateUserRepositoryImpl
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.data.network.UserApi
import com.example.randomuser_test_task.data.userdetail.UserDetailsRepositoryImpl
import com.example.randomuser_test_task.data.userslist.UsersListRepositoryImpl
import com.example.randomuser_test_task.domain.generateuser.GenerateUserRepository
import com.example.randomuser_test_task.domain.userdetail.UserDetailsRepository
import com.example.randomuser_test_task.domain.userslist.UsersListRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    factory { Gson() }
    single { provideOkHttpClient(androidApplication()) }
    single { provideRetrofit(get()) }
    single { provideBinApiService(get()) }
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "users.db"
        )
            .build()
    }
    single {
        get<AppDatabase>().userDao()
    }
    single<GenerateUserRepository> {
        GenerateUserRepositoryImpl(get(), get(), get())
    }
    single<UserDetailsRepository> {
        UserDetailsRepositoryImpl(get(), get())
    }
    single<UsersListRepository> {
        UsersListRepositoryImpl(get(), get())
    }
    single { UserMapper }
}

private fun provideOkHttpClient(application: Application): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(ChuckerInterceptor(application))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun provideBinApiService(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
}