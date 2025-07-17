# 🌦 Android Weather App Template

A **production-ready Android template** built with **Kotlin** and the latest Android tools & architecture. Perfect for API-driven apps with a clean separation of concerns.

## 🚀 Features
- 🖌 **Jetpack Compose** UI (Material 3)
- 🏛 **MVVM Architecture** with Repository pattern
- 🗂 **Repository Layer** as single source of truth
- ⚡ **Hilt** for dependency injection
- 🌐 **Retrofit** for API calls
- 🗄 **Room** for local caching & favorites
- 📦 Modular, scalable codebase
- 💡 Modern Kotlin (coroutines, Flow, StateFlow)

## 📂 Project Structure
app/
├ data/
│ ├ local/ # Room database, DAO, entities
│ ├ remote/ # Retrofit API service
│ └ model/ # API response models
├ repository/ # Repository layer for API + Room mediation
├ viewmodel/ # UI logic with StateFlow
├ ui/ # Compose screens and components
├ di/ # Hilt modules

## ✨ Why Use This Template?
This template is designed for **API-first Android apps** with:
✅ Clean architecture  
✅ Repository pattern (single source of truth)  
✅ Production-ready patterns and best practices.