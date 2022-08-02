package io.raveerocks.asteroidradar.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.raveerocks.asteroidradar.Configurations.PREFETCH_LIMIT
import io.raveerocks.asteroidradar.DateUtil.getToday
import io.raveerocks.asteroidradar.repository.RepositoryRegistry
import io.raveerocks.asteroidradar.repository.repos.AsteroidRepository
import io.raveerocks.asteroidradar.repository.repos.PictureRepository
import io.raveerocks.asteroidradar.ui.models.Asteroid
import io.raveerocks.asteroidradar.ui.models.Picture
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    enum class LoadingStatus { LOADING, DONE, FAILED }
    enum class AsteroidFilter { ALL, DAY }

    private val pictureRepository: PictureRepository by lazy { RepositoryRegistry.pictureRepository }
    private val asteroidRepository: AsteroidRepository by lazy { RepositoryRegistry.asteroidRepository }
    private val _pictureLoadingStatus = MutableLiveData<LoadingStatus>()
    private val _asteroidsLoadingStatus = MutableLiveData<LoadingStatus>()
    private val _asteroidsRefreshedEvent = MutableLiveData<Boolean>()
    private val _asteroidsRefreshFailedEvent = MutableLiveData<Boolean>()
    private val _asteroidsFilterChangedEvent = MutableLiveData<Boolean>()

    val pictureLoadingStatus: LiveData<LoadingStatus>
        get() = _pictureLoadingStatus
    val asteroidsLoadingStatus: LiveData<LoadingStatus>
        get() = _asteroidsLoadingStatus
    val asteroidsRefreshedEvent: LiveData<Boolean>
        get() = _asteroidsRefreshedEvent
    val asteroidsRefreshFailedEvent: LiveData<Boolean>
        get() = _asteroidsRefreshFailedEvent
    val asteroidsFilterChangedEvent: LiveData<Boolean>
        get() = _asteroidsFilterChangedEvent

    val pictureOfTheDay: LiveData<Picture>
    var asteroids: LiveData<List<Asteroid>>

    init {

        setStatus()
        launchRefresh()
        pictureOfTheDay = pictureRepository.pictureOfTheDay
        asteroids = asteroidRepository.viewAllAsteroids(getToday())
    }

    fun markAsteroidsAsLoaded() {
        _asteroidsLoadingStatus.value = LoadingStatus.DONE
    }

    fun onFilterChange(asteroidFilter: AsteroidFilter) {
        asteroids = when (asteroidFilter) {
            AsteroidFilter.ALL -> asteroidRepository.viewAllAsteroids(getToday())
            AsteroidFilter.DAY -> asteroidRepository.filterAsteroidsByDay(getToday())
        }
        _asteroidsFilterChangedEvent.value = true
    }

    fun markFilterChangedDone() {
        _asteroidsFilterChangedEvent.value = false
    }

    fun markAsteroidsRefreshedDone() {
        _asteroidsRefreshedEvent.value = false
    }

    fun markAsteroidsRefreshFailedDone() {
        _asteroidsRefreshFailedEvent.value = false
    }

    private fun setStatus() {
        _pictureLoadingStatus.value = LoadingStatus.LOADING
        _asteroidsLoadingStatus.value = LoadingStatus.LOADING
        _asteroidsRefreshFailedEvent.value = false
        _asteroidsFilterChangedEvent.value = false
    }

    private fun launchRefresh() {

        viewModelScope.launch {
            pictureRepository.refreshAPOD({ setPictureLoadingStatus(LoadingStatus.DONE) },
                { setPictureLoadingStatus(LoadingStatus.FAILED) })
        }

        viewModelScope.launch {
            asteroidRepository.refreshAsteroids(
                getToday(),
                PREFETCH_LIMIT,
                { markAsteroidsRefreshed() },
                { markAsteroidRefreshFailed() })
        }
    }

    private fun setPictureLoadingStatus(status: LoadingStatus) {
        _pictureLoadingStatus.value = status
    }

    private fun markAsteroidRefreshFailed() {
        _asteroidsRefreshFailedEvent.value = true
    }

    private fun markAsteroidsRefreshed() {
        _asteroidsRefreshedEvent.value = true
    }

}