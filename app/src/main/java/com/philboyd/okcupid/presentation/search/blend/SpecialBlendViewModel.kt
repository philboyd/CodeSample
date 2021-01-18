package com.philboyd.okcupid.presentation.search.blend

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.philboyd.okcupid.domain.ObserveLikedPeopleUseCase
import com.philboyd.okcupid.domain.Person
import com.philboyd.okcupid.domain.ToggleLikedPersonUseCase
import com.philboyd.okcupid.presentation.core.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import remotedata.RemoteData

typealias MatchesData = RemoteData<Throwable, PagedList<Person>>

class SpecialBlendViewModel(
    private val likedPersonUseCase: ObserveLikedPeopleUseCase,
    private val toggleLikedPersonUseCase: ToggleLikedPersonUseCase,
    private val observerScheduler: Scheduler
) : ViewModel<SpecialBlendViewModel.ViewState, SpecialBlendViewModel.Action>(
    ViewState(),
    update
) {

    data class ViewState(
        val matches: MatchesData = RemoteData.Loading,
        val liked: Set<Int> = emptySet()
    )

    sealed class Action {
        data class MatchDataReceived(val data: MatchesData) : Action()
        data class LikedPeopleReceived(val likedPeople: Set<Person>) : Action()
    }

    fun start() {
        likedPersonUseCase.execute()
            .observeOn(observerScheduler)
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .subscribe {
                dispatch(Action.LikedPeopleReceived(it))
            }
            .addTo(disposables)

    }

    fun stub() {
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

    fun toggleLike(person: Person) {
        toggleLikedPersonUseCase.execute(person)
    }
}

private val update: (SpecialBlendViewModel.ViewState, SpecialBlendViewModel.Action) -> SpecialBlendViewModel.ViewState =
    { state, action ->
        when (action) {
            is SpecialBlendViewModel.Action.MatchDataReceived -> {
                state.copy(matches = action.data)
            }
            is SpecialBlendViewModel.Action.LikedPeopleReceived -> {
                state.copy(liked = action.likedPeople.map { it.id }.toSet())
            }
        }
    }

val data = listOf(
    Person(
        id = 0,
        userName = "Phil",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k1.okccdn.com/php/load_okc_image.php/images/640x560/640x560/0x0/0x0/2/17436090354355705210.jpg"
    ),

    Person(
        id = 1,
        userName = "Philb",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),

    Person(
        id = 2,
        userName = "Phil3",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),

    Person(
        id = 3,
        userName = "Phild",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),
    Person(
        id = 4,
        userName = "Phila",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = "https://k0.okccdn.com/php/load_okc_image.php/images/120x120/120x120/36x36/684x684/2/15743311334557165678.jpg"
    ),
    Person(
        id = 5,
        userName = "Phild",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 6,
        userName = "Philz",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 7,
        userName = "Philx",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 8,
        userName = "Phildc",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 9,
        userName = "Phildv",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 10,
        userName = "Phildb",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 11,
        userName = "Philzq",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 12,
        userName = "Philxq",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 13,
        userName = "Phildcq",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 14,
        userName = "Phildvq",
        matchPercentage = 34,
        isLiked = false,
        age = 27,
        city = "royal oka",
        region = "MI",
        image = ""
    ),
    Person(
        id = 15,
        userName = "Phildbq",
        matchPercentage = 34,
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
        ), config
    )
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
