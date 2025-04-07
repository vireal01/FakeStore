package com.vireal.fakestore.ui.itemDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ItemDetailsScreen(
    itemId: Int,
    store: ItemDetailsStore = hiltViewModel()
) {

  val state by store.state.collectAsState()

  LaunchedEffect(true) {
    store.effects.collect { effect ->
      when (effect) {
        is ItemDetailsEffect.LoadItemDetails -> {
          store.dispatch(ItemDetailsMessage.LoadItemDetails(itemId))
        }
        else -> {
          // Handle other effects if needed
        }
      }
    }
  }

  Scaffold(
    topBar = {
      ItemDetailsTopBar(
        title = state.itemDetails?.title ?: "Item Details",
        onBackClick = { store.dispatch(ItemDetailsMessage.NavigateBackClicked) }
      )
    },
  ) { paddingValues ->
    Card {
      state.itemDetails?.let { item ->
        Text(
          text = item.title,
          modifier = Modifier.padding(paddingValues)
        )
        Text(
          text = item.description,
          modifier = Modifier.padding(paddingValues)
        )
        Text(
          text = "$${item.price}",
          modifier = Modifier.padding(paddingValues)
        )
      } ?: run {
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
          )
        }
      }
    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsTopBar(
  title: String,
  onBackClick: () -> Unit
) {
  TopAppBar(
    title = { Text(text = title) },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
      }
    },
  )
}