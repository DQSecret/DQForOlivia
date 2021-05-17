package com.example.dqddu.list.paging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.dqddu.list.paging3.model.DogImageModel
import com.example.dqddu.list.paging3.repository.remote.DogApiService
import com.example.dqddu.list.paging3.repository.remote.RemoteInjector
import kotlinx.coroutines.flow.Flow

class DogImagesRepository(
    private val dogApiService: DogApiService = RemoteInjector.injectDogApiService()
) {

    companion object {

        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = DogImagesRepository()
    }

    fun letDogImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<DogImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { DogImagePagingSource(dogApiService) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}
