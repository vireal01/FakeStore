// itemlist/ItemListScreen.kt

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
import com.vireal.fakestore.ui.itemlist.ItemListEffect
import com.vireal.fakestore.ui.itemlist.ItemListMessage
import com.vireal.fakestore.ui.itemlist.ItemListStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
  onNavigateToDetail: (Int) -> Unit,
  store: ItemListStore = hiltViewModel()
) {
  // Получаем текущее состояние из Store
  val state by store.state.collectAsState()

  // Слушаем и обрабатываем эффекты навигации
  LaunchedEffect(true) {
    // Предполагается, что в Store есть flow эффектов для наблюдения
    store.effects.collect { effect ->
      when (effect) {
        is ItemListEffect.NavigateToItemDetails -> {
          onNavigateToDetail(effect.itemId)
        }
        // Другие эффекты не требуют обработки на уровне UI
        else -> { /* игнорируем */ }
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Список товаров") },
        actions = {
          // Фильтр товаров
          IconButton(onClick = {
//            store.dispatch(ItemListMessage.FilterChanged(ItemFilter.POPULAR))
          }) {
            Icon(Icons.Default.Search, contentDescription = "Фильтр")
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
          // Отображаем загрузку
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
          )
        }

        state.error != null -> {
          // Отображаем ошибку
//          ErrorView(
//            message = state.error ?: "Неизвестная ошибка",
//            onRetryClick = {
//              store.dispatch(ItemListMessage.RetryClicked)
//            },
//            modifier = Modifier.align(Alignment.Center)
//          )
        }

        state.items.isEmpty() -> {
          // Отображаем пустой список
//          EmptyView(
//            message = "Товары не найдены",
//            modifier = Modifier.align(Alignment.Center)
//          )
        }

        else -> {
          // Отображаем список товаров
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Фильтры (если нужны)
            item {
//              FilterChips(
//                selectedFilter = state.filter,
//                onFilterSelected = { filter ->
//                  store.dispatch(ItemListMessage.FilterChanged(filter))
//                }
//              )
            }

            // Список товаров
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

// Компонент для фильтров
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
    ItemFilter.values().forEach { filter ->
      FilterChip(
        selected = filter == selectedFilter,
        onClick = { onFilterSelected(filter) },
        label = { Text(filter.displayName) }
      )
    }
  }
}

// Enum для фильтров
enum class ItemFilter(val displayName: String) {
  ALL("Все"),
  POPULAR("Популярные"),
  NEW("Новые"),
  DISCOUNTED("Со скидкой")
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