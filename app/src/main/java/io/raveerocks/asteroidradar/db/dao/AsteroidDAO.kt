package io.raveerocks.asteroidradar.db.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import io.raveerocks.asteroidradar.db.DateConverter
import io.raveerocks.asteroidradar.db.entity.AsteroidEntity
import java.sql.Date


@Dao
@TypeConverters(DateConverter::class)
interface AsteroidDAO {

    @Query("SELECT * FROM asteroids WHERE date>=:date ORDER BY date ASC")
    fun getAllAsteroidsData(date: Date): List<AsteroidEntity>

    @Query("SELECT * FROM asteroids WHERE date>=:date ORDER BY date ASC")
    fun getAllAsteroids(date: Date): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE date=:date ORDER BY name ASC")
    fun getAllAsteroidsByDate(date: Date): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidEntity)

    @Query("DELETE FROM asteroids WHERE date<:date")
    fun deleteOldData(date: Date): Int

}