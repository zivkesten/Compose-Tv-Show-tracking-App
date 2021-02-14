package com.zk.trackshows.data.network.mapper

import com.zk.trackshows.data.network.model.ShowDto
import com.zk.trackshows.domain.mapper.DomainMapper
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.utils.mockShow
import com.zk.trackshows.utils.mockShowDto

class MockDtoMapper: DomainMapper<ShowDto, Show> {

    override fun mapToDomainModel(model: ShowDto): Show {
        return mockShow(model.id)
    }

    override fun mapFromDomainModel(domainModel: Show): ShowDto {
        return mockShowDto(domainModel.id)
    }

    override fun toDomainList(initial: List<ShowDto>): List<Show>{
        return initial.map { mapToDomainModel(it) }
    }

    override fun fromDomainList(initial: List<Show>): List<ShowDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
