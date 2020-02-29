package com.madonnaapps.buswatch.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madonnaapps.buswatch.cache.model.LastLocationDbo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LastLocationDao {

    @Query("SELECT * FROM last_location WHERE ID = 1 LIMIT 1")
    fun getLastLocation(): Single<LastLocationDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastLocation(lastLocationDbo: LastLocationDbo): Completable

}