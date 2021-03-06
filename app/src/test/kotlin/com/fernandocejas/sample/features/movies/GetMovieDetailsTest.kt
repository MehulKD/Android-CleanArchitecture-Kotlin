package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.TestScheduler
import com.fernandocejas.sample.TestScheduler.Function.applyHighPriorityScheduler
import com.fernandocejas.sample.UnitTest
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMovieDetailsTest : UnitTest() {
    private val MOVIE_ID = 1

    private lateinit var getMovieDetails: GetMovieDetails

    @Mock private lateinit var moviesRepository: MoviesRepository

    private val testScheduler = TestScheduler()

    @Before fun setUp() {
        getMovieDetails = GetMovieDetails(moviesRepository, testScheduler)
        given { moviesRepository.movieDetails(MOVIE_ID) } .willReturn(createMovieDetailsObservable())
    }

    @Test fun shouldGetDataFromRepository() {
        getMovieDetails.buildObservable(GetMovieDetails.Params(MOVIE_ID))

        verify(moviesRepository).movieDetails(MOVIE_ID)
        verifyNoMoreInteractions(moviesRepository)

        testScheduler verify applyHighPriorityScheduler
    }

    private fun createMovieDetailsObservable() = Observable.empty<MovieDetails>()
}
