package io.raveerocks.asteroidradar.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.raveerocks.asteroidradar.db.dao.AsteroidDAO
import io.raveerocks.asteroidradar.db.database.CacheDatabase
import io.raveerocks.asteroidradar.db.entity.AsteroidEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.sql.Date


@RunWith(AndroidJUnit4::class)
class CacheDataBaseTest {


    private lateinit var testDatabase: CacheDatabase
    private lateinit var asteroidDAO:AsteroidDAO

    private val id: String = "Id"
    private val name: String = "Name"
    private val date: Date = Date.valueOf("2022-08-02")
    private val futureDate: Date = Date.valueOf("2022-08-03")
    private val absoluteMagnitude: Double = 45775.0
    private val estimatedDiameterMax: Double = 535367.0
    private val isPotentiallyHazardousAsteroid: Boolean = true
    private val relativeVelocity: Double = 6575.23
    private val missDistance: Double = 45672.76
    private val asteroidEntity:AsteroidEntity = AsteroidEntity(id, name, date, absoluteMagnitude, estimatedDiameterMax, isPotentiallyHazardousAsteroid, relativeVelocity, missDistance)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        testDatabase = DatabaseServiceRegistry(context).testDatabase
        asteroidDAO = testDatabase.asteroidDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        testDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAsteroid() {
        asteroidDAO.insertAll(asteroidEntity)
        val receivedAsteroidEntity = asteroidDAO.getAllAsteroidsData(date)
        Assert.assertEquals(receivedAsteroidEntity[0].id,asteroidEntity.id)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAndCheckAsteroid() {
        asteroidDAO.insertAll(asteroidEntity)
        val count = asteroidDAO.deleteOldData(futureDate)
        Assert.assertNotEquals(count,0)
    }

}