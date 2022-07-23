package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.model.MovieResponseModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieResponseToDomainModel @Inject constructor() : Mapper<MovieResponseModel, MovieDomainModel> {
    override fun map(type: MovieResponseModel): MovieDomainModel {
        return with(type) {
            MovieDomainModel(
                id = id,
                title = title,
                posterPath = posterPath,
                overview = overview,
                voteAverage = voteAverage,
            )
        }
    }
}
