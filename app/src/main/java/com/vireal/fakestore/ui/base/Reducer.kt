package com.vireal.fakestore.ui.base

interface Reducer<S : State, M : Message, E : Effect> {
  fun reduce(state: S, message: M): Pair<S, E?>
}