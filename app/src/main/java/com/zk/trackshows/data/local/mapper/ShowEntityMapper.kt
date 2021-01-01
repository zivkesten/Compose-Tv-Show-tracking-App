package com.zk.trackshows.data.local.mapper

import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.domain.mapper.DomainMapper
import com.zk.trackshows.domain.model.Show

class ShowEntityMapper: DomainMapper<ShowEntity, Show> {

    override fun mapToDomainModel(model: ShowEntity): Show {
        return Show(
            id = model.id,
            backdrop_path = model.backdrop_path,
            first_air_date = model.first_air_date,
            //genre_ids = model.genre_ids,
            name = model.name,
            //origin_country = model.origin_country,
            original_language = model.original_language,
            original_name = model.original_name,
            overview = model.overview,
            popularity = model.popularity,
            poster_path = model.poster_path,
            vote_average = model.vote_average,
            vote_count= model.vote_count
        )
    }

    override fun mapFromDomainModel(domainModel: Show): ShowEntity {
        return ShowEntity(
            id = domainModel.id,
            backdrop_path = domainModel.backdrop_path,
            first_air_date = domainModel.first_air_date,
            //genre_ids = domainModel.genre_ids,
            name = domainModel.name,
            //origin_country = domainModel.origin_country,
            original_language = domainModel.original_language,
            original_name = domainModel.original_name,
            overview = domainModel.overview,
            popularity = domainModel.popularity ?: 0.0,
            poster_path = domainModel.poster_path,
            vote_average = domainModel.vote_average ?: 0.0,
            vote_count= domainModel.vote_count?: 0
        )
    }

    fun toDomainList(initial: List<ShowEntity>): List<Show>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Show>): List<ShowEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}
