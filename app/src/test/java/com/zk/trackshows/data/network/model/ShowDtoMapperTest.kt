package com.zk.trackshows.data.network.model

import com.zk.trackshows.data.network.mapper.ShowDtoMapper
import com.zk.trackshows.utils.mockShow
import com.zk.trackshows.utils.mockShowDto
import org.hamcrest.MatcherAssert
import org.junit.Test

import org.junit.Before
import kotlin.random.Random

class ShowDtoMapperTest {

    private lateinit var sut: ShowDtoMapper

    @Before
    fun setup() {
        sut = ShowDtoMapper()
    }

    @Test
    fun test_mapToDomainModel_outputting_correct_data() {
        //Given
        val dataId: Int = Random(100).nextInt()
        val input = mockShowDto(dataId)

        //Expected
        val expectedResult = mockShow(dataId)

        //Actual
        val actual = sut.mapToDomainModel(input)

        //Assert
        MatcherAssert.assertThat("expectedResult ${expectedResult.name} " +
                "should match actual result ${actual.name} ",
            actual == expectedResult)
    }

    @Test
    fun mapFromDomainModel() {
        //Given
        val dataId: Int = Random(100).nextInt()
        val input = mockShow(dataId)

        //Expected
        val expectedResult = mockShowDto(dataId)

        //Actual
        val actual = sut.mapFromDomainModel(input)

        //Assert
        MatcherAssert.assertThat("expectedResult ${expectedResult.name}" +
                " should match actual result ${actual.name}",
            actual == expectedResult)
    }

    @Test
    fun toDomainList() {

        // Random ids
        val dataId1: Int = Random(10).nextInt()
        val dataId2: Int = Random(10).nextInt()
        val dataId3: Int = Random(10).nextInt()

        //Given
        val input = listOf(
            mockShowDto(dataId1),
            mockShowDto(dataId2),
            mockShowDto(dataId3)
        )

        //Expected
        val expectedResult = listOf(
            mockShow(dataId1),
            mockShow(dataId2),
            mockShow(dataId3)
        )

        //Actual
        val actual = sut.toDomainList(input)

        //Assert
        MatcherAssert.assertThat("expectedResult $expectedResult" +
                " should match actual result $actual",
            actual == expectedResult)

    }

    @Test
    fun fromDomainList() {

        // Random ids
        val dataId1: Int = Random(10).nextInt()
        val dataId2: Int = Random(10).nextInt()
        val dataId3: Int = Random(10).nextInt()

        //Given
        val input = listOf(
            mockShow(dataId1),
            mockShow(dataId2),
            mockShow(dataId3)
        )

        //Expected
        val expectedResult = listOf(
            mockShowDto(dataId1),
            mockShowDto(dataId2),
            mockShowDto(dataId3)
        )

        //Actual
        val actual = sut.fromDomainList(input)

        //Assert
        MatcherAssert.assertThat("expectedResult $expectedResult" +
                " should match actual result $actual",
            actual == expectedResult)

    }
}