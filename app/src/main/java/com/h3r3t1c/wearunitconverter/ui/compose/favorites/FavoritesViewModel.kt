package com.h3r3t1c.wearunitconverter.ui.compose.favorites

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.ext.favsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(context: Context): ViewModel() {

    var showAddFavoriteDialog by mutableStateOf(false)
    var deleteDialog by mutableStateOf<FavoriteConversion?>(null)
    var selected by mutableStateOf<FavoriteConversion?>(null)

    val favs = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { context.favsDatabase.getAll() }
    ).flow.cachedIn(viewModelScope)

    fun addFavorite(context: Context, f: FavoriteConversion){
        showAddFavoriteDialog = false
        viewModelScope.launch(Dispatchers.IO) {
            context.favsDatabase.add(f)
        }
    }
    fun deleteFavorite(context: Context, f: FavoriteConversion){
        deleteDialog = null
        viewModelScope.launch(Dispatchers.IO) {
            context.favsDatabase.remove(f)
        }
    }

    companion object{
        fun getFactory(context: Context,): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    FavoritesViewModel(context)
                }
            }
            return factory
        }
    }
}