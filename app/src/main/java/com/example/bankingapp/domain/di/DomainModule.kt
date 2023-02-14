package com.example.bankingapp.domain.di

import com.example.bankingapp.data.repository.CustomerRepository
import com.example.bankingapp.domain.use_cases.CreateAccountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideCreateAccountUseCase(customerRepository: CustomerRepository): CreateAccountUseCase {
        return CreateAccountUseCase(customerRepository)
    }

}