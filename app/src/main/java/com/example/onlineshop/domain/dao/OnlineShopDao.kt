package com.example.onlineshop.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.onlineshop.domain.models.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface OnlineShopDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insetAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Query("SELECT * FROM accounts WHERE first_name = :firstName LIMIT 1")
    fun selectAccountData(firstName: String): Flow<Account>

    @Query("SELECT EXISTS(SELECT 1 FROM accounts WHERE email = :searchEmail)")
    fun checkEmailExists(searchEmail: String): Flow<Boolean>
}