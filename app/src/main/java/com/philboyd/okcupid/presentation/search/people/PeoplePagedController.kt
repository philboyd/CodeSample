package com.philboyd.okcupid.presentation.search.people

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.philboyd.okcupid.domain.Person

class PeoplePagedController(
    private val personCallBack: PersonCallBack,
    var likedPeopleIds: Set<Int>
) : PagedListEpoxyController<Person>(
    diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    override fun buildItemModel(currentPosition: Int, item: Person?): EpoxyModel<*> {
        return if (item == null) {
            buildPlaceholder(currentPosition)
        } else {
            PersonModel(item, likedPeopleIds.contains(item.id), personCallBack)
                .id(item.id)
        }
    }

    private fun buildPlaceholder(currentPosition: Int): EpoxyModel<*> {
        val id = -currentPosition
        return PersonPlaceholderModel().id(id)
    }
}
