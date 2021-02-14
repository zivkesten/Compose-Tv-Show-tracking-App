package com.zk.trackshows.data.local.mapper

import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.utils.mockPopularShow
import com.zk.trackshows.utils.mockShow
import com.zk.trackshows.utils.mockShowEntity
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

import org.junit.Before
import kotlin.random.Random

class ShowEntityMapperTest {

    private lateinit var sut: ShowEntityMapper

    @Before
    fun setup() {
        sut = ShowEntityMapper()
    }

    @Test
    fun test_mapToDomainModel_outputting_correct_data() {
        //Given
        val dataId: Int = Random(100).nextInt()
        val input = mockPopularShow(dataId)

        //Expected
        val expectedResult = mockShow(dataId)

        //Actual
        val actual = sut.mapToDomainModel(input.show)

        //Assert
        assertThat("expectedResult ${expectedResult.name} " +
                "should match actual result ${actual.name} ",
            actual == expectedResult)
    }

    @Test
    fun mapFromDomainModel() {
        //Given
        val dataId: Int = Random(100).nextInt()
        val input = mockShow(dataId)

        //Expected
        val expectedResult = mockShowEntity(dataId)

        //Actual
        val actual = sut.mapFromDomainModel(input)

        //Assert
        assertThat("expectedResult ${expectedResult.name}" +
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
            mockShowEntity(dataId1),
            mockShowEntity(dataId2),
            mockShowEntity(dataId3)
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
        assertThat("expectedResult $expectedResult" +
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
            mockShowEntity(dataId1),
            mockShowEntity(dataId2),
            mockShowEntity(dataId3)
        )

        //Actual
        val actual = sut.fromDomainList(input)

        //Assert
        assertThat("expectedResult $expectedResult" +
                " should match actual result $actual",
            actual == expectedResult)

    }
}