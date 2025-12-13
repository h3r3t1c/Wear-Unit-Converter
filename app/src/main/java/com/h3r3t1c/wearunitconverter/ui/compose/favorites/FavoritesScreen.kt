package com.h3r3t1c.wearunitconverter.ui.compose.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.ConfirmDialog
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.NumberInputDialog
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.UnitHelper

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel = viewModel<FavoritesViewModel>(factory = FavoritesViewModel.getFactory(context))
    val padding = rememberResponsiveColumnPadding(
        first = ColumnItemType.Button,
        last = ColumnItemType.Button
    )
    val listState = rememberScalingLazyListState(
        0
    )
    val favs = viewModel.favs.collectAsLazyPagingItems()
    ScreenScaffold(
        scrollState = listState,
        contentPadding = padding
    ) {
        ScalingLazyColumn(
            state = listState,
            contentPadding = padding,
            autoCentering = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            item("btn_add") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(
                        onClick = { viewModel.showAddFavoriteDialog = true },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                            contentDescription = null,
                        )
                    }
                    ListHeader {
                        Text(stringResource(R.string.favorites))
                    }
                }
            }
            items(favs.itemCount, key = favs.itemKey { it.id.toString() }){
                val fav = favs[it]!!
                Favorite(fav, {viewModel.deleteDialog = fav}) {
                    viewModel.selected = fav
                }
            }
            if(favs.itemCount == 0 && favs.loadState.refresh !is LoadState.Loading){
                item{
                    NoFavorites()
                }
            }
            if(favs.loadState.refresh is LoadState.Loading || favs.loadState.append is LoadState.Loading) {
                item {
                    Loading()
                }
            }
        }
        Dialogs(viewModel, navController)
    }
}
@Composable
private fun Dialogs(viewModel: FavoritesViewModel, navController: NavHostController){
    val context = LocalContext.current
    AddFavoriteDialog(viewModel.showAddFavoriteDialog, onDismiss = { viewModel.showAddFavoriteDialog = false },) {
        viewModel.addFavorite(context, it)
    }
    ConfirmDialog(viewModel.deleteDialog != null, title = "Delete Favorite", text = "${viewModel.deleteDialog?.let { f ->  "${UnitHelper.unitToString(f.from)} → ${UnitHelper.unitToString(f.to)}\n"} ?: ""}Are you sure you want to delete this favorite?", onDismiss = { viewModel.deleteDialog = null }){
        viewModel.deleteFavorite(context, viewModel.deleteDialog!!)
    }
    NumberInputDialog(
        viewModel.selected != null,
        onDismiss = { viewModel.selected = null },
    ) {
        val selected = viewModel.selected!!
        viewModel.selected = null
        navController.navigate(NavDestination.getConvertPath(selected.type, it, selected.from, selected.to))
    }
}
@Composable
private fun NoFavorites(){
    Button(
        onClick = { },
        label = {
            Text(text = "No Favorites")
        },
        secondaryLabel = {
            Text(text = "Tap the + button above to add a favorite")
        },
        colors = ButtonDefaults.filledVariantButtonColors(),
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
private fun Loading(){
    CircularProgressIndicator()
}
@Composable
private fun Favorite(f: FavoriteConversion, onDelete: () -> Unit, onClick: () -> Unit){
    Button(
        onClick = onClick,
        onLongClick = onDelete,
        label = {
            Text(text = "${UnitHelper.unitToString(f.from)} → ${UnitHelper.unitToString(f.to)}", style = MaterialTheme.typography.titleMedium)
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(ConverterType.getIconForType(f.type)),
                contentDescription = null,
            )
        },
        colors = ButtonDefaults.filledTonalButtonColors(),
        modifier = Modifier.fillMaxWidth()
    )
}