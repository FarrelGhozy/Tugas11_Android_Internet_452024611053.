# AGENTS — Tugas11: Android Internet Integration

## Project Overview
Aplikasi Android (Jetpack Compose) yang menampilkan daftar photo dari JSONPlaceholder API menggunakan Retrofit + Moshi + Coroutines + Glide.

## Stack
| Komponen | Teknologi |
|---|---|
| UI | Jetpack Compose (Material3) |
| Bahasa | Kotlin |
| HTTP Client | Retrofit 2.11.0 |
| JSON Parser | Moshi 1.15.2 (dengan KSP codegen) |
| Async | Kotlin Coroutines (viewModelScope) |
| Image Loader | Glide 4.16.0 (Compose integration) |
| Arsitektur | MVVM |
| Build | Gradle 9.3.1, AGP 9.1.1, Kotlin 2.2.10 |
| Min SDK | 35 |
| Target SDK | 36 |

## Package Structure
```
com.example.tugas11/
├── model/          → Data class dengan Moshi @JsonClass
├── network/        → Retrofit ApiService + RetrofitInstance
├── ui/
│   ├── viewmodel/  → ViewModel dengan viewModelScope
│   ├── screens/    → Composable screens
│   └── theme/      → (existing)
└── util/           → BindingAdapters, utilities
```

## Key Files
| File | Path | Purpose |
|---|---|---|
| `Photo.kt` | `model/Photo.kt` | Moshi data class untuk response JSONPlaceholder |
| `ApiService.kt` | `network/ApiService.kt` | Retrofit interface dengan suspend fun |
| `RetrofitInstance.kt` | `network/RetrofitInstance.kt` | Singleton Retrofit builder |
| `MainViewModel.kt` | `ui/viewmodel/MainViewModel.kt` | ViewModel dengan viewModelScope.launch |
| `MainScreen.kt` | `ui/screens/MainScreen.kt` | Compose UI dengan LazyColumn + Glide |
| `BindingAdapters.kt` | `util/BindingAdapters.kt` | Custom @BindingAdapter untuk Glide |

## Dependencies (from libs.versions.toml)
- `libs.retrofit` — Retrofit core
- `libs.retrofit-converter-moshi` — Moshi converter for Retrofit
- `libs.moshi` — Moshi core
- `libs.moshi-kotlin-codegen` — Moshi KSP codegen
- `libs.glide` — Glide core
- `libs.glide-compose` — Glide Compose integration
- `libs.glide-compiler` — Glide annotation processor
- `libs.lifecycle-viewmodel-compose` — ViewModel in Compose
- `libs.lifecycle-runtime-compose` — Lifecycle runtime for Compose

## Commands
```bash
# Build project
./gradlew assembleDebug

# Run tests
./gradlew test

# Clean build
./gradlew clean assembleDebug

# Check dependencies
./gradlew :app:dependencies
```

## API
**Base URL**: `https://api.github.com/`
**Endpoint**: `GET /users`
**Response**: `List<User>`
```json
{
  "login": "mojombo",
  "id": 1,
  "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4"
}
```

## Coding Standards
- Gunakan `suspend fun` untuk semua network call di Retrofit
- Gunakan `viewModelScope.launch` + try-catch untuk async execution
- Gunakan `GlideImage` composable untuk loading gambar
- Jangan block main thread
- Ikuti konvensi penamaan Kotlin/Android standar

## Git Rules
- Jangan commit file di `build/`, `.gradle/`, `local.properties`
- Commit message harus jelas dan deskriptif
- Jangan push tanpa diminta
- Pastikan repo public sebelum submit
- **WAJIB**: Git commit setiap selesai satu stage implementasi (Gradle → Permission → Model → Network → ViewModel → UI → Glide)
- Selalu cek `git status` dan `git diff` sebelum commit
- Gunakan `git add` secara selektif (hanya file yang relevan per stage)
