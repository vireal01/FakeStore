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
  // Состояние как StateFlow для реактивного UI
  private val _state = MutableStateFlow(initialState)
  val state: StateFlow<S> = _state.asStateFlow()

  // Поток эффектов
  private val _effects = MutableSharedFlow<E>()
  val effects: SharedFlow<E> = _effects.asSharedFlow()

  // Функция для отправки сообщений в Store
  fun dispatch(message: M) {
    val (newState, effect) = reducer.reduce(_state.value, message)

    // Обновляем состояние
    _state.value = newState

    // Если есть эффект, обрабатываем его
    effect?.let { handleEffect(it) }
  }

  // Обработка эффектов
  private fun handleEffect(effect: E) {
    // Отправляем эффект в поток для обработки UI слоем (например, навигация)
    CoroutineScope(Dispatchers.Main).launch {
      _effects.emit(effect)
    }

    // Обрабатываем эффект и получаем новое сообщение
    CoroutineScope(Dispatchers.IO).launch {
      val newMessage = effectHandler(effect)

      // Если обработчик эффекта вернул сообщение, диспатчим его
      newMessage?.let {
        withContext(Dispatchers.Main) {
          dispatch(it)
        }
      }
    }
  }
}