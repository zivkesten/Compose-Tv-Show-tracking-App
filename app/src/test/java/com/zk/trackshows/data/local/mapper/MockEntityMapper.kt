package com.zk.trackshows.data.local.mapper

import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.domain.mapper.DomainMapper
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.utils.mockShow
import com.zk.trackshows.utils.mockShowEntity

class MockEntityMapper: DomainMapper<ShowEntity, Show> {

    override fun mapToDomainModel(model: ShowEntity): Show {
        return mockShow(model.id)
    }

    override fun mapFromDomainModel(domainModel: Show): ShowEntity {
        return mockShowEntity(domainModel.id)
    }

    override fun toDomainList(initial: List<ShowEntity>): List<Show>{
        return initial.map { mapToDomainModel(it) }
    }

    override fun fromDomainList(initial: List<Show>): List<ShowEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}
