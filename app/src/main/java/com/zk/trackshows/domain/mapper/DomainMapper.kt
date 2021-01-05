package com.zk.trackshows.domain.mapper

import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.domain.model.Show

interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

    fun toDomainList(initial: List<T>): List<DomainModel>

    fun fromDomainList(initial: List<DomainModel>): List<T>
}