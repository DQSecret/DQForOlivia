package com.example.dqddu.list.paging3.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dqddu.list.paging3.data.DogImagesRepository.Companion.DEFAULT_PAGE_INDEX
import com.example.dqddu.list.paging3.model.DogImageModel
import com.example.dqddu.list.paging3.repository.remote.DogApiService
import retrofit2.HttpException
import java.io.IOException

class DogImagePagingSource(
    private val dogApiService: DogApiService
) : PagingSource<Int, DogImageModel>() {

    override fun getRefreshKey(state: PagingState<Int, DogImageModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DogImageModel> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = dogApiService.getDogImages(page, params.loadSize)
            LoadResult.Page(
                response,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
