package com.example.bankingapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bankingapp.data.model.*

@Database(
    entities = [AtmCard::class, Transaction::class, Account::class, Loan::class,Customer::class],
    version = 2,
    exportSchema = true
)
abstract class BankingDatabase : RoomDatabase() {


    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, BankingDatabase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun getBankingDao() : BankingDao


}