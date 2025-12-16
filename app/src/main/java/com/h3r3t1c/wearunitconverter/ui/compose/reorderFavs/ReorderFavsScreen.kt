package com.h3r3t1c.wearunitconverter.ui.compose.reorderFavs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.dragHandle
import com.h3r3t1c.wearunitconverter.ui.compose.common.draggableItemsIndexed
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberDraggableListState
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.util.CategoryHelper


@Composable
fun ReorderFavoritesScreen(){
    val context = LocalContext.current
    val viewModel = viewModel<ReorderFavoritesViewModel>(factory = ReorderFavoritesViewModel.getFactory(context))
    val listState = rememberScalingLazyListState(
        0
    )

    val padding = rememberResponsiveColumnPadding(
        first = ColumnItemType.Button,
        last = ColumnItemType.Button
    )

    val draggableState = rememberDraggableListState(
        lazyListState = listState,
        onMove = { from, to ->
            viewModel.move(from, to)
        }
    )
    ScreenScaffold(
        scrollState = listState,
        contentPadding = padding,

    ) {
        ScalingLazyColumn(
            state = listState,
            contentPadding = padding,
            autoCentering = null,
            anchorType = ScalingLazyListAnchorType.ItemStart,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            item("header"){
                ListHeader {
                    Text(text = stringResource(R.string.reorder_favorites))
                }
            }
            draggableItemsIndexed(
                state = draggableState,
                items = viewModel.favs,
                key = { index, item -> index }
            ) { index, item, isDragging ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer, ButtonDefaults.shape)
                        .padding(ButtonDefaults.ContentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = CategoryHelper.getIconForCategory(item.type)),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = ButtonDefaults.IconSpacing)
                            .size(ButtonDefaults.IconSize),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = item.displayString(), modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurface)
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_drag_hanle),
                        contentDescription = null,
                        modifier = Modifier.dragHandle(
                            state = draggableState,
                            index = index,
                        ),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        if(viewModel.loading){
            Loading()
        }
    }
}

@Composable
private fun Loading(){
    Box(Modifier
        .fillMaxSize()
        .background(Color.Black.copy(0.6f)), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}