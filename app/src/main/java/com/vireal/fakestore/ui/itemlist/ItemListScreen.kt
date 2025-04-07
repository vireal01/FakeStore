package com.vireal.fakestore.ui.itemlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vireal.fakestore.domain.model.Item


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
  onNavigateToDetail: (Int) -> Unit,
  store: ItemListStore = hiltViewModel()
) {
  val state by store.state.collectAsState()

  LaunchedEffect(true) {
    store.effects.collect { effect ->
      when (effect) {
        is ItemListEffect.NavigateToItemDetails -> {
          onNavigateToDetail(effect.itemId)
        }
        else -> {  }
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Fake shop items") },
        actions = {
          IconButton(onClick = {
//            store.dispatch(ItemListMessage.FilterChanged(ItemFilter.POPULAR))
          }) {
            Icon(Icons.Default.Search, contentDescription = "Filter")
          }
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when {
        state.isLoading -> {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
          )
        }

        state.error != null -> {
//          ErrorView(
//            message = state.error ?: "Unknown error",
//            onRetryClick = {
//              store.dispatch(ItemListMessage.RetryClicked)
//            },
//            modifier = Modifier.align(Alignment.Center)
//          )
        }

        state.items.isEmpty() -> {
          Text(
            text = "No items found",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Center)
          )
        }

        else -> {
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            item {
//              FilterChips(
//                selectedFilter = state.filter,
//                onFilterSelected = { filter ->
//                  store.dispatch(ItemListMessage.FilterChanged(filter))
//                }
//              )
            }

            items(state.items) { item ->
              ItemCard(
                item = item,
                onClick = {
                  store.dispatch(ItemListMessage.ItemClicked(item.id))
                }
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun FilterChips(
  selectedFilter: ItemFilter,
  onFilterSelected: (ItemFilter) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ItemFilter.entries.forEach { filter ->
      FilterChip(
        selected = filter == selectedFilter,
        onClick = { onFilterSelected(filter) },
        label = { Text(filter.displayName) }
      )
    }
  }
}

enum class ItemFilter(val displayName: String) {
  ALL("All"),
  POPULAR("Popular"),
  NEW("New"),
  DISCOUNTED("On Sale");
}


@Composable
fun ItemCard(
  item: Item,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onClick() },
    elevation = CardDefaults.cardElevation(4.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = item.title,
        style = MaterialTheme.typography.titleMedium
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = item.description,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "$${item.price}",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
      )
    }
  }
}