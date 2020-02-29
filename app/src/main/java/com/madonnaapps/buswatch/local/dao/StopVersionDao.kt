package com.madonnaapps.buswatch.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madonnaapps.buswatch.local.model.StopVersionDbo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface StopVersionDao {

    @Query("SELECT * FROM stop_version WHERE id = 1 LIMIT 1")
    fun getStopVersion(): Single<StopVersionDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStopVersion(version: StopVersionDbo): Completable

}