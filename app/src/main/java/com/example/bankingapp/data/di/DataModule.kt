package com.example.bankingapp.data.di

import android.content.Context
import com.example.bankingapp.data.room.BankingDao
import com.example.bankingapp.data.room.BankingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideBankingDatabase(@ApplicationContext context: Context): BankingDatabase {
        return BankingDatabase.getInstance(context)
    }

    @Provides
    fun provideBankingDao(bankingDatabase: BankingDatabase): BankingDao {
        return bankingDatabase.getBankingDao()
    }

}