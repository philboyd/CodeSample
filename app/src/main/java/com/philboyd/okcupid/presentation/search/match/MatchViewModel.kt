package com.philboyd.okcupid.presentation.search.match

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.philboyd.okcupid.domain.Person
import com.philboyd.okcupid.presentation.core.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData

typealias MatchesData =  RemoteData<Throwable, PagedList<Person>>

class SearchViewModel(

) : ViewModel<SearchViewModel.ViewState, SearchViewModel.Action>(
    ViewState(),
    update
) {

    data class ViewState(
        val matches : MatchesData = RemoteData.Loading,
        val likedPeople : RemoteData<Throwable, PagedList<Person>> = RemoteData.Loading
    )

    sealed class Action {
        data class MatchDataReceived(val data: MatchesData): Action()
    }

    fun start() {
        stubPagedList(
            data,
            DEFAULT_CONFIG
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                val pagedData = it
                dispatch(
                    Action.MatchDataReceived(
                        RemoteData.succeed(pagedData)
                    )
                )
            }
            .addTo(disposables)
    }
}

private val update : (SearchViewModel.ViewState, SearchViewModel.Action) -> SearchViewModel.ViewState = { state, action ->
    when (action) {
        is SearchViewModel.Action.MatchDataReceived -> {
            state.copy(matches = action.data)
        }
    }
}

val data = listOf(
    Person(userName = "Phil",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k1.okccdn.com/php/load_okc_image.php/images/640x560/640x560/0x0/0x0/2/17436090354355705210.jpg"
    ),

    Person(userName = "Philb",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),

    Person(userName = "Phil3",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),

    Person(userName = "Phild",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),
    Person(userName = "Phila",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),
    Person(userName = "Phild",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Philz",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Philx",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildc",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildv",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildb",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Philzq",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Philxq",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildcq",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildvq",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(userName = "Phildbq",
        matchPercentage = "34",
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    )
)

private val DEFAULT_PAGE_SIZE = 20

private val DEFAULT_CONFIG = PagedList.Config.Builder()
    .setPageSize(DEFAULT_PAGE_SIZE)
    .build()

fun stubPagedList(
    data: List<Person>,
    config: PagedList.Config
): Observable<PagedList<Person>> =
    RxPagedListBuilder<Int, Person>(
        stubDataSourceFactory(
            data
        ), config)
        .buildObservable()


fun <T : Any> stubDataSourceFactory(data: List<T>): DataSource.Factory<Int, T> =
    object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> =
            stubDataSource(data)
    }

fun <T : Any> stubDataSource(data: List<T>): DataSource<Int, T> =
    object : PositionalDataSource<T>() {
        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {}

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
            callback.onResult(data, 0, data.size)
        }
    }
