package com.monkeymantech.monkeymote.modules

import com.monkeymantech.monkeymote.configuration.ApplicationPrefs
import com.monkeymantech.monkeymote.repositories.SonyRepository
import com.monkeymantech.monkeymote.ui.main.MainFragment
import com.monkeymantech.monkeymote.ui.main.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val fragmentModule = module {
    factory { MainFragment() }
}

val viewModelModule = module {
    factory { MainViewModel() }
}

val prefModule = module {
    single { ApplicationPrefs(androidContext()) }
}


val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideSonyApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideSonyApi(retrofit: Retrofit): SonyRepository = retrofit.create(SonyRepository::class.java)