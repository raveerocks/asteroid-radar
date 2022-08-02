package io.raveerocks.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.raveerocks.asteroidradar.ui.models.Picture
import io.raveerocks.asteroidradar.ui.viewModel.MainViewModel

@BindingAdapter("loadingStatus")
fun bindImageLoadingStatus(imgView: ImageView, loadingStatus: MainViewModel.LoadingStatus?) {
    loadingStatus.let {
        when (it) {
            MainViewModel.LoadingStatus.FAILED -> imgView.setImageResource(R.drawable.ic_broken_image)
            MainViewModel.LoadingStatus.LOADING -> imgView.setImageResource(R.drawable.loading_img)
            else -> {}
        }
    }
}

@BindingAdapter("picture")
fun bindImagePicture(imgView: ImageView, picture: Picture?) {
    picture?.let {
        val imgUri = picture.url.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
    imgView.contentDescription = if (picture != null) String.format(
        imgView.context.getString(R.string.nasa_picture_of_day_content_description_format),
        picture.title
    ) else imgView.contentDescription
}

@BindingAdapter("loaded")
fun bindProgressBarVisibility(progressBar: ProgressBar, status: MainViewModel.LoadingStatus) {
    progressBar.visibility = when (status) {
        MainViewModel.LoadingStatus.LOADING -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("isPotentiallyHazardousAsteroid")
fun bindImageWithAsteroidType(imgView: ImageView, isPotentiallyHazardousAsteroid: Boolean) {
    isPotentiallyHazardousAsteroid.let {
        val imgResource: Int =
            if (isPotentiallyHazardousAsteroid) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal
        imgView.setImageResource(imgResource)
        imgView.contentDescription =
            if (isPotentiallyHazardousAsteroid) imgView.context.getString(R.string.potentially_hazardous_asteroid_image) else imgView.context.getString(
                R.string.not_hazardous_asteroid_image
            )
    }
}

@BindingAdapter("potentiallyHazardousAsteroidDetailed")
fun bindPotentiallyHazardousAsteroidDetailed(
    imgView: ImageView,
    isPotentiallyHazardousAsteroid: Boolean
) {
    isPotentiallyHazardousAsteroid.let {
        val imgResource: Int =
            if (isPotentiallyHazardousAsteroid) R.drawable.asteroid_hazardous else R.drawable.asteroid_safe
        imgView.setImageResource(imgResource)
        imgView.contentDescription =
            if (isPotentiallyHazardousAsteroid) imgView.context.getString(R.string.potentially_hazardous_asteroid_image) else imgView.context.getString(
                R.string.not_hazardous_asteroid_image
            )
    }
}

@BindingAdapter("absoluteMagnitude")
fun bindAbsoluteMagnitude(textView: TextView, absoluteMagnitude: Double) {
    textView.text = String.format(
        textView.context.getString(R.string.astronomical_unit_format),
        absoluteMagnitude
    )
}

@BindingAdapter("estimatedDiameter")
fun bindEstimatedDiameter(textView: TextView, estimatedDiameter: Double) {
    textView.text =
        String.format(textView.context.getString(R.string.km_unit_format), estimatedDiameter)
}

@BindingAdapter("relativeVelocity")
fun bindRelativeVelocity(textView: TextView, relativeVelocity: Double) {
    textView.text =
        String.format(textView.context.getString(R.string.km_s_unit_format), relativeVelocity)
}

@BindingAdapter("missDistance")
fun bindMissDistance(textView: TextView, missDistance: Double) {
    textView.text = String.format(textView.context.getString(R.string.km_unit_format), missDistance)
}




