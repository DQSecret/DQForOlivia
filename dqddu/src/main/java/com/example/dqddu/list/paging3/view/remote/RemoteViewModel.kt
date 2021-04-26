package com.example.dqddu.list.paging3.view.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.dqddu.list.paging3.data.DogImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteViewModel(
    private val repository: DogImagesRepository = DogImagesRepository.getInstance()
) : ViewModel() {

    fun fetchDogImages(): Flow<PagingData<String>> {
        return repository.letDogImagesFlow()
            .map { pagingData ->
                pagingData.map { model ->
                    model.url
                }
            }
            .cachedIn(viewModelScope)
    }
}
