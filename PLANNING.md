# PLANNING — Tugas11: Android Internet Integration

## 1. Ringkasan Tugas
Membangun aplikasi Android yang terhubung ke internet menggunakan:
- **Retrofit** untuk HTTP client → REST API
- **Moshi** untuk JSON → Kotlin data class parsing
- **Kotlin Coroutines (viewModelScope)** untuk async execution
- **Glide** untuk image loading dari URL
- **Jetpack Compose** untuk UI

## 2. API yang Digunakan
**JSONPlaceholder** — `https://jsonplaceholder.typicode.com/photos`
- Gratis, tanpa API key
- Response: array of `{ albumId, id, title, url, thumbnailUrl }`
- Berisi data teks (title) dan gambar (url / thumbnailUrl)

## 3. Arsitektur
MVVM + Clean Architecture sederhana:

```
com.example.tugas11/
├── model/
│   └── Photo.kt              → Data class Moshi
├── network/
│   ├── ApiService.kt         → Retrofit interface (suspend fun)
│   └── RetrofitInstance.kt   → Singleton Retrofit builder
├── ui/
│   ├── viewmodel/
│   │   └── MainViewModel.kt  → ViewModel + viewModelScope
│   ├── screens/
│   │   └── MainScreen.kt     → Compose UI
│   └── theme/                → (existing)
├── util/
│   └── BindingAdapters.kt    → Glide @BindingAdapter
└── MainActivity.kt           → Entry point
```

## 4. Dependencies

### Version Catalog (`gradle/libs.versions.toml`)
| Alias | Library | Version |
|---|---|---|
| retrofit | `com.squareup.retrofit2:retrofit` | `2.11.0` |
| retrofit-converter-moshi | `com.squareup.retrofit2:converter-moshi` | `2.11.0` |
| moshi | `com.squareup.moshi:moshi` | `1.15.2` |
| moshi-kotlin-codegen | `com.squareup.moshi:moshi-kotlin-codegen` | `1.15.2` |
| glide | `com.github.bumptech.glide:glide` | `4.16.0` |
| glide-compose | `com.github.bumptech.glide:compose` | `1.0.0` |
| glide-compiler | `com.github.bumptech.glide:compiler` | `4.16.0` |
| lifecycle-viewmodel-compose | `androidx.lifecycle:lifecycle-viewmodel-compose` | `2.8.7` |
| lifecycle-runtime-compose | `androidx.lifecycle:lifecycle-runtime-compose` | `2.8.7` |

### Plugins
- `com.google.devtools.ksp` version `2.2.10-1.0.31` (for Moshi codegen)

## 5. Langkah Implementasi

### Langkah 1: Konfigurasi Gradle
- Tambah KSP plugin ke root `build.gradle.kts`
- Tambah KSP + dependencies ke `app/build.gradle.kts`

### Langkah 2: AndroidManifest.xml
- Tambah `<uses-permission android:name="android.permission.INTERNET" />`
- Tambah `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`

### Langkah 3: Model Layer
- Buat `model/Photo.kt` dengan `@JsonClass(generateAdapter = true)`
- Fields: `albumId`, `id`, `title`, `url`, `thumbnailUrl`

### Langkah 4: Network Layer
- `network/ApiService.kt`: interface dengan `@GET("photos") suspend fun getPhotos(): List<Photo>`
- `network/RetrofitInstance.kt`: singleton object, baseUrl JSONPlaceholder, MoshiConverterFactory

### Langkah 5: ViewModel
- `MainViewModel`: extends `ViewModel()`
- Gunakan `viewModelScope.launch` untuk panggil API
- Simpan state dengan `MutableStateFlow` / `mutableStateOf`

### Langkah 6: UI (Compose)
- `MainScreen.kt`: `LazyColumn` menampilkan daftar photo
- Tiap item: `title` (Text) + thumbnail (GlideImage)
- Loading state: `CircularProgressIndicator`
- Error state: pesan error

### Langkah 7: Glide Integration
- Gunakan `GlideImage` dari `com.bumptech.glide:compose`
- Parameter: `contentDescription`, `placeholder`, `error`
- Buat custom `@BindingAdapter` di `util/BindingAdapters.kt` untuk ImageView

### Langkah 8: MainActivity
- Panggil `setContent { MainScreen() }`
- Integrasikan ViewModel via `viewModel()`

## 6. File Structure Final

```
Tugas11/
├── PLANNING.md
├── AGENTS.md
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/tugas11/
│       │   ├── MainActivity.kt
│       │   ├── model/
│       │   │   └── Photo.kt
│       │   ├── network/
│       │   │   ├── ApiService.kt
│       │   │   └── RetrofitInstance.kt
│       │   ├── ui/
│       │   │   ├── viewmodel/
│       │   │   │   └── MainViewModel.kt
│       │   │   ├── screens/
│       │   │   │   └── MainScreen.kt
│       │   │   └── theme/
│       │   └── util/
│       │       └── BindingAdapters.kt
│       └── res/
└── .gitignore
```

## 7. Git Workflow
Setiap selesai satu stage implementasi, WAJIB melakukan git commit:

| Stage | Files to Commit | Commit Message |
|---|---|---|
| Gradle | `gradle/libs.versions.toml`, `build.gradle.kts`, `app/build.gradle.kts` | `chore: add Retrofit, Moshi, Glide, KSP dependencies` |
| Permission | `app/src/main/AndroidManifest.xml` | `feat: add INTERNET and ACCESS_NETWORK_STATE permissions` |
| Model | `app/src/main/java/com/example/tugas11/model/Photo.kt` | `feat: add Photo model with Moshi @JsonClass` |
| Network | `network/ApiService.kt`, `network/RetrofitInstance.kt` | `feat: add Retrofit ApiService and RetrofitInstance` |
| ViewModel | `ui/viewmodel/MainViewModel.kt` | `feat: add MainViewModel with coroutine network call` |
| UI | `ui/screens/MainScreen.kt`, `MainActivity.kt` | `feat: add MainScreen composable with photo list` |
| Glide | `util/BindingAdapters.kt` | `feat: add Glide BindingAdapter with placeholder and error` |
| README | `README.md` | `docs: add README with identity, screenshots, permission explanation` |

**Aturan:**
- `git add` hanya file yang relevan per stage (jangan `git add .`)
- `git status` dan `git diff` sebelum commit
- Build harus sukses (`./gradlew assembleDebug`) sebelum commit

## 8. Normal vs Dangerous Permission (untuk README)

### Normal Permission: `INTERNET`, `ACCESS_NETWORK_STATE`
- **Karakteristik**: Tidak mengancam privasi user secara langsung
- **Interaksi**: Otomatis diberikan saat install, **tidak perlu runtime prompt**
- **Contoh**: INTERNET, ACCESS_NETWORK_STATE, BLUETOOTH, VIBRATE

### Dangerous Permission: `CAMERA`, `LOCATION`, `READ_CONTACTS`
- **Karakteristik**: Berpotensi mengakses data pribadi user
- **Interaksi**: Harus meminta izin secara eksplisit saat runtime (runtime prompt dialog)
- **Contoh**: CAMERA, ACCESS_FINE_LOCATION, READ_EXTERNAL_STORAGE
- **Mekanisme**: Gunakan `ActivityResultContracts.RequestPermission()` di Compose
