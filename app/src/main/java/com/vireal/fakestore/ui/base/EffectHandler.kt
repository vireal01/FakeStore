package com.vireal.fakestore.ui.base

interface EffectHandler<E : Effect, M : Message> {
  suspend fun handle(effect: E): M?
}
