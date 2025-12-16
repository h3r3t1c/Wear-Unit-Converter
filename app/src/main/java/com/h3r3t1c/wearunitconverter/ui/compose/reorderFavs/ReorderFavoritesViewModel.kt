package com.h3r3t1c.wearunitconverter.ui.compose.reorderFavs

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.ext.favsDatabase
import com.h3r3t1c.wearunitconverter.tile.FavoritesTile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReorderFavoritesViewModel(context: Context): ViewModel() {

    val favs = mutableStateListOf<FavoriteConversion>()
    var loading by mutableStateOf(true)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val f = context.favsDatabase.getAllForSorting()
            viewModelScope.launch(Dispatchers.Main) {
                favs.addAll(f)
                loading = false
            }
        }
    }

    fun move(from: Int, to: Int){
        if(from >= favs.size || to >= favs.size) return
        favs.add(to, favs.removeAt(from))
    }

    fun save(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            favs.forEachIndexed { index, favoriteConversion ->
                context.favsDatabase.add(favoriteConversion.copy(sortOrder = index.toLong()))
            }
            FavoritesTile.refresh(context)
        }
    }
    companion object{
        fun getFactory(context: Context,): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    ReorderFavoritesViewModel(context)
                }
            }
            return factory
        }
    }
}