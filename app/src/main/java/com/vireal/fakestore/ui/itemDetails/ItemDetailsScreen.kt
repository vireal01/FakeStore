package com.vireal.fakestore.ui.itemDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vireal.fakestore.domain.model.Item

@Composable
fun ItemDetailsScreen(
  itemId: Int, store: ItemDetailsStore = hiltViewModel(), onNavigateBack: () -> Unit
) {

  val state by store.state.collectAsState()

  LaunchedEffect(itemId) {
    store.dispatch(ItemDetailsMessage.LoadItemDetails(itemId))
  }

  LaunchedEffect(true) {
    store.effects.collect { effect ->
      when (effect) {
        is ItemDetailsEffect.NavigateToCart -> {
          // Handle navigation to cart
        }

        else -> { /* No action needed */
        }
      }
    }
  }

  Scaffold(
    topBar = {
      ItemDetailsTopBar(
        title = state.itemDetails?.title ?: "Item Details", onBackClick = {
          onNavigateBack()
        })
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          top = paddingValues.calculateTopPadding(),
          start = 16.dp,
          end = 16.dp,
          bottom = paddingValues.calculateBottomPadding()
        )
    ) {
      if (state.isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      } else {
        state.itemDetails?.let { item ->
          ItemDetailsCard(item)
        }
      }
    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsTopBar(
  title: String, onBackClick: () -> Unit
) {
  TopAppBar(
    title = {
      TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
          Card(modifier = Modifier.padding(8.dp)) {
            Text(
              text = title,
              modifier = Modifier.padding(8.dp),
              style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 12.sp,  // Adjust font size if needed
                lineHeight = 12.sp // Adjust line height
              )
            )
          }
        },
        state = rememberTooltipState(),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = title,
          maxLines = 1,
          softWrap = false,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 18.sp,  // Adjust font size if needed
            lineHeight = 24.sp // Adjust line height
          )
        )
      }
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
      }
    },
  )
}

@Composable
fun ItemDetailsCard(
  item: Item,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp),
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