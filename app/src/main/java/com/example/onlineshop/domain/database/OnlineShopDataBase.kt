package com.example.onlineshop.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineshop.domain.dao.OnlineShopDao
import com.example.onlineshop.domain.models.Account

@Database(entities = [Account::class], version = 1)
abstract class OnlineShopDataBase: RoomDatabase() {
    abstract fun todoDao(): OnlineShopDao

    companion object {
        private var INSTANCE: OnlineShopDataBase? = null

        fun getDatabase(context: Context): OnlineShopDataBase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, OnlineShopDataBase::class.java, "onlineshop.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as OnlineShopDataBase
        }
    }
}