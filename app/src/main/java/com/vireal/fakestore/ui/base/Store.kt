package com.vireal.fakestore.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Store<S : State, M : Message, E : Effect>(
  initialState: S,
  private val reducer: Reducer<S, M, E>,
  private val effectHandler: suspend (E) -> M?
) {
  private val _state = MutableStateFlow(initialState)
  val state: StateFlow<S> = _state.asStateFlow()

  private val _effects = MutableSharedFlow<E>()
  val effects: SharedFlow<E> = _effects.asSharedFlow()

  fun dispatch(message: M) {
    val (newState, effect) = reducer.reduce(_state.value, message)

    _state.value = newState
    effect?.let { handleEffect(it) }
  }

  private fun handleEffect(effect: E) {
    CoroutineScope(Dispatchers.Main).launch {
      _effects.emit(effect)
    }

    CoroutineScope(Dispatchers.IO).launch {
      val newMessage = effectHandler(effect)

      newMessage?.let {
        withContext(Dispatchers.Main) {
          dispatch(it)
        }
      }
    }
  }
}