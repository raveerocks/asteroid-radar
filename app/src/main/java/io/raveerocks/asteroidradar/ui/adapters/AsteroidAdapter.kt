package io.raveerocks.asteroidradar.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.raveerocks.asteroidradar.R
import io.raveerocks.asteroidradar.databinding.AsteroidItemBinding
import io.raveerocks.asteroidradar.ui.models.Asteroid

class AsteroidAdapter(private val clickListener: AsteroidItemClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidItemViewHolder>(
        AsteroidItemViewHolder.AsteroidItemDiffCallback()
    ) {

    class AsteroidItemViewHolder private constructor(private val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        class AsteroidItemDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem == newItem
            }
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AsteroidItemBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.asteroid_item, parent, false)
                return AsteroidItemViewHolder(binding)
            }
        }

        fun bind(item: Asteroid, clickListener: AsteroidItemClickListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class AsteroidItemClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return AsteroidItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(asteroidItemViewHolder: AsteroidItemViewHolder, position: Int) {
        asteroidItemViewHolder.bind(getItem(position), clickListener)
    }

}

