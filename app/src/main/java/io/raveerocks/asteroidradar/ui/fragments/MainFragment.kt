package io.raveerocks.asteroidradar.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import io.raveerocks.asteroidradar.R
import io.raveerocks.asteroidradar.databinding.FragmentMainBinding
import io.raveerocks.asteroidradar.ui.adapters.AsteroidAdapter
import io.raveerocks.asteroidradar.ui.models.Asteroid
import io.raveerocks.asteroidradar.ui.viewModel.MainViewModel


class MainFragment : Fragment() {


    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var binding: FragmentMainBinding
    private lateinit var asteroidAdapter: AsteroidAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        bind()
        setupObservers()
        setupMenu()
        return binding.root
    }

    private fun bind() {
        asteroidAdapter =
            AsteroidAdapter(AsteroidAdapter.AsteroidItemClickListener(this::onAsteroidItemClick))
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = this.viewModel
        binding.asteroidRecycler.adapter = asteroidAdapter
    }

    private fun setupObservers() {
        viewModel.asteroids.observe(viewLifecycleOwner, this::onAsteroidDataChange)
        viewModel.asteroidsFilterChangedEvent.observe(viewLifecycleOwner, this::onFilterChange)
        viewModel.asteroidsRefreshedEvent.observe(viewLifecycleOwner, this::onAsteroidDataRefresh)
        viewModel.asteroidsRefreshFailedEvent.observe(
            viewLifecycleOwner,
            this::onAsteroidDataRefreshFailure
        )
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(
            getMenuProvider(),
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun onAsteroidItemClick(asteroid: Asteroid) {
        binding.root.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid))
    }

    private fun onAsteroidDataChange(asteroids: List<Asteroid>) {
        asteroids.let {
            asteroidAdapter.submitList(it)
            viewModel.markAsteroidsAsLoaded()
        }
    }

    private fun onFilterChange(filterChange: Boolean) {
        if (filterChange) {
            viewModel.asteroids.observe(viewLifecycleOwner, this::onFilterAsteroidDataChange)
        }
    }

    private fun onAsteroidDataRefresh(status: Boolean) {
        status.let {
            if (status) {
                Toast.makeText(
                    context,
                    "Asteroids data successfully refreshed in background.",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.markAsteroidsRefreshedDone()
            }
        }
    }

    private fun onAsteroidDataRefreshFailure(status: Boolean) {
        status.let {
            if (status) {
                Snackbar.make(
                    this.binding.asteroidRecycler,
                    "Background data refresh failed, we will retry later.",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("OK") {}
                    .show()
                viewModel.markAsteroidsRefreshFailedDone()
            }
        }
    }

    private fun getMenuProvider(): MenuProvider {
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.show_all_menu -> viewModel.onFilterChange(MainViewModel.AsteroidFilter.ALL)
                    R.id.show_today_menu -> viewModel.onFilterChange(MainViewModel.AsteroidFilter.DAY)
                    else -> return false
                }
                return true
            }
        }
        return menuProvider
    }

    private fun onFilterAsteroidDataChange(asteroids: List<Asteroid>) {
        asteroids.let {
            asteroidAdapter.submitList(it)
            viewModel.markFilterChangedDone()
        }
    }

}