# FakeStore App

A modern Android application showcasing best practices in Android development using Kotlin, Jetpack Compose, Clean Architecture, and The Elm Architecture (TEA).

## Overview

FakeStore is a sample e-commerce application that displays products from the [Platzi Fake Store API](https://fakeapi.platzi.com/en). It demonstrates how to combine Clean Architecture principles with the reactive UI pattern inspired by The Elm Architecture (TEA).

## Architecture

This application is built using two complementary architectural patterns:

### Clean Architecture

The codebase is organized into three main layers:

1. **Presentation Layer (UI)**
    - Composable screens
    - ViewModels (Store implementation)
    - UI state, messages, and effects

2. **Domain Layer**
    - Use cases (business logic)
    - Domain models
    - Repository interfaces

3. **Data Layer**
    - Repository implementations
    - Remote data sources (Retrofit)
    - Local data sources (Room)
    - DTOs and mappers

### The Elm Architecture (TEA)

The UI follows a unidirectional data flow pattern inspired by The Elm Architecture:

- **State**: Immutable data class representing the UI state
- **Message**: Events that can change the state
- **Effect**: Side effects that occur as a result of state changes
- **Reducer**: Pure function that takes current state and a message, and returns new state and optional effect
- **Store**: Manages state, dispatches messages, and handles effects

## Features

- Product listing with clean UI
- Product details view
- Offline support using Room database

## Technologies

- **UI**: Jetpack Compose
- **Navigation**: Compose Navigation
- **Dependency Injection**: Hilt
- **Networking**: Retrofit
- **Local Storage**: Room
- **Concurrency**: Kotlin Coroutines and Flow
- **Build System**: Gradle with Kotlin DSL

## TEA Pattern Implementation

The app implements The Elm Architecture (TEA) pattern through these key components:

- `Store`: Central component that manages state and side effects
- `State`: Immutable representation of UI state
- `Message`: Represents user actions or system events
- `Effect`: Side effects triggered by state changes
- `Reducer`: Pure function that computes new state based on current state and messages
- `EffectHandler`: Processes side effects and may produce new messages

### Flow of Data

1. User interacts with UI, generating a `Message`
2. `Message` is dispatched to the `Store`
3. `Reducer` processes the message and current state, returning new state and optional `Effect`
4. UI updates based on new state (via state flows)
5. If an `Effect` was produced, the `EffectHandler` processes it
6. `EffectHandler` may dispatch new messages to the `Store`


## License

This project is licensed under the MIT License - see the LICENSE file for details.