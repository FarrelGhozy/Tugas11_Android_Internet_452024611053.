# Tugas11_Android_Internet_452024611053

Aplikasi Android yang menampilkan daftar foto dari JSONPlaceholder API menggunakan Retrofit + Moshi + Coroutines + Glide + Jetpack Compose.

## Identitas

- **Nama**: FAZY
- **NIM**: 452024611053

## Screenshot

> (Tambahkan screenshot atau GIF aplikasi di sini)

## Tech Stack

| Komponen | Teknologi |
|---|---|
| Bahasa | Kotlin |
| UI | Jetpack Compose (Material3) |
| HTTP Client | Retrofit 2.11.0 |
| JSON Parser | Moshi 1.15.2 (KSP codegen) |
| Async | Kotlin Coroutines (viewModelScope) |
| Image Loader | Glide 4.16.0 (Compose integration) |
| Arsitektur | MVVM |

## API

**Endpoint**: `https://jsonplaceholder.typicode.com/photos`

Response berisi array objek `Photo` dengan field: `albumId`, `id`, `title`, `url`, `thumbnailUrl`.

## Fitur

- Menampilkan daftar foto (thumbnail + title) dari internet
- Loading state dengan `CircularProgressIndicator`
- Error state dengan pesan error
- Placeholder image saat loading dan error image saat gagal
- Glide `@BindingAdapter` kustom untuk pemuatan gambar

## Normal Permission vs Dangerous Permission

### Normal Permission (`INTERNET`, `ACCESS_NETWORK_STATE`)

- **Karakteristik**: Tidak mengancam privasi pengguna secara langsung. Izin ini hanya memungkinkan aplikasi mengakses jaringan internet dan membaca status koneksi.
- **Interaksi Pengguna**: Diberikan secara otomatis saat instalasi aplikasi. **Tidak ada dialog permintaan (runtime prompt)** yang muncul kepada pengguna.
- **Contoh lain**: `BLUETOOTH`, `VIBRATE`, `ACCESS_WIFI_STATE`, `SET_WALLPAPER`.

### Dangerous Permission (`CAMERA`, `LOCATION`, `READ_CONTACTS`)

- **Karakteristik**: Berpotensi mengakses data pribadi atau sensitif pengguna, seperti kamera, lokasi GPS, atau kontak.
- **Interaksi Pengguna**: Harus diminta secara eksplisit saat runtime melalui dialog Android. Pengguna dapat **menolak** permintaan izin kapan saja. Di Compose, digunakan `rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())`.
- **Contoh lain**: `ACCESS_FINE_LOCATION`, `RECORD_AUDIO`, `READ_EXTERNAL_STORAGE`, `SEND_SMS`.

### Ringkasan

| Aspek | Normal Permission | Dangerous Permission |
|---|---|---|
| **Contoh** | `INTERNET` | `CAMERA` |
| **Runtime Prompt** | Tidak | Ya |
| **Dapat ditolak user** | Tidak (otomatis) | Ya |
| **Dicabut sistem** | Tidak | Ya (Android 11+) |
