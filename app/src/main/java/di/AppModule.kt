package di

import api.ApiInterface
import repository.MainRepository
import utils.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dao.NewsDao
import repository.DBRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    // create instance and initialize new MainRepository class for mainRepository in Repository
    fun provideRepo(apiInterface: ApiInterface): MainRepository = MainRepository(apiInterface)

    @Singleton
    @Provides
    // create instance of retrofitinstance
    fun provideInstanceRetrofit(): ApiInterface{
        return  Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideDao(newsDao: NewsDao) : DBRepository = DBRepository(newsDao)


}
