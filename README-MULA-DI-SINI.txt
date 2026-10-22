JAM AZAN SURAU UBUDIAH V18 — PROJEK SUMBER ANDROID TV

UI: V17 (font hitam tebal, outline putih, gambar Surau Ubudiah).
Sambungan: telefon AJK hidupkan hotspot, TV sambung ke hotspot.
TV menjalankan pelayan HTTP pada port 8765; telefon buka http://ALAMAT-IP-TV:8765
PIN demo: 684291 (WAJIB tukar dalam MainActivity.java sebelum penggunaan sebenar).
Tekan simpan di panel admin telefon; TV semak tetapan setiap 4 saat.

PERHATIAN:
- Ini SOURCE CODE, bukan APK; belum dibina atau diuji pada TV sebenar.
- Sambungan HTTP LAN sahaja. Jangan dedahkan port 8765 ke internet.
- PIN demo tidak selamat untuk produksi; tambah pengesahan dan pengurusan PIN sebenar.
- Hotspot sesetengah telefon mengasingkan peranti atau tidak benarkan telefon mengakses klien hotspot. Perlu ujian peranti.
- Jika telefon dan TV tidak dapat berhubung, gunakan router Wi-Fi tanpa internet sebagai alternatif.
- Waktu solat masih data contoh sehingga jadual rasmi JHR04 dimasukkan.
- Backup JSON disyorkan; gambar yang besar mungkin melebihi had penghantaran.
- Untuk membina APK perlu Android SDK / GitHub Actions.


CARA BUILD MELALUI TELEFON (GITHUB ACTIONS)
1. Buka github.com dalam browser telefon, login/daftar.
2. Buat repository PRIVATE baharu bernama jam-azan-surau.
3. Upload SEMUA fail dan folder dalam ZIP ini ke root repository (bukan ZIP sahaja).
   Pastikan .github/workflows/build-apk.yml turut dimuat naik.
4. Pergi Actions > Bina APK Jam Azan Surau > Run workflow.
5. Selepas build berjaya, buka run > Artifacts > JamAzanSurau-debug-APK.
6. Download artifact ZIP dan ekstrak app-debug.apk.
7. Pindahkan APK ke TV menggunakan USB/kaedah pindahan fail yang disokong.

Ini debug APK untuk UJIAN, bukan release rasmi. Workflow belum dijalankan di GitHub dan build belum disahkan berjaya.
PENTING: jadual waktu solat masih contoh, jangan guna untuk menentukan azan sebenar sehingga data rasmi disahkan.
