package com.inspiredcoda.quizdemoapp.di

import com.inspiredcoda.quizdemoapp.domain.ApiService
import com.inspiredcoda.quizdemoapp.domain.DispatcherProvider
import com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository.InstructorRepository
import com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository.InstructorRepositoryImpl
import com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository.QuizQuestionImpl
import com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository.QuizRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun default(): CoroutineDispatcher = Dispatchers.Default

    }


    @Provides
    @Singleton
    fun provideQuizRepository(
        moshi: Moshi,
        apiService: ApiService,
        dispatcherProvider: DispatcherProvider
    ): QuizRepository {
        return QuizQuestionImpl(moshi, apiService, dispatcherProvider)
    }

    @Provides
    @Singleton
    fun provideInstructorsRepository(
        moshi: Moshi,
        apiService: ApiService,
        dispatcherProvider: DispatcherProvider
    ): InstructorRepository {
        return InstructorRepositoryImpl(moshi, apiService, dispatcherProvider)
    }

}