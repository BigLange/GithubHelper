package com.gengqiquan.githubhelper.base

import android.content.Context
import com.gengqiquan.githubhelper.App
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by gengqiquan on 2017/6/21.
 */
@Module
class AppModule(private val application: App, private val okHttpClient: OkHttpClient, private var retrofit: Retrofit) {

    @Provides @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    //接口不能作为对象被注入
//    fun provideGithubService(): GithubService {
//        return retrofit.create(GithubService::class.java)
//    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return retrofit
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return okHttpClient
    }
}