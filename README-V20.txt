JAM AZAN SURAU UBUDIAH — V20 (Iqamah Countdown + Mod Solat)

Berdasarkan sumber V19; reka bentuk asal dan sistem admin dikekalkan.

BARU:
- Mod Solat automatik selepas tempoh iqamah tamat.
- Paparan 'SILA RAPATKAN SAF' menutup slideshow sementara.
- Tempoh mod solat boleh ditetapkan 5–60 minit dalam Panel Admin.
- Boleh hidup/matikan Mod Solat.
- Boleh abaikan Zohor pada Jumaat untuk elak mengganggu khutbah.
- Tidak akan mengaktifkan Mod Solat tanpa jadual import hari semasa.
- Notifikasi masuk waktu 30 saat diberi keutamaan.

SEDIA ADA:
- Countdown iqamah setiap waktu (tetapan minit sudah ada dalam V19).
- Import jadual CSV JHR04, slideshow, PIN, pelayan admin LAN, dan gambar asal.

CARA BINA APK:
1. Backup repositori V19 atau buat branch baharu v20.
2. Upload fail projek ini ke root repo GitHub (replace fail lama).
3. Pastikan .github/workflows/build-apk.yml kekal.
4. GitHub Actions > Build APK > Run workflow (atau tunggu auto-run selepas commit).
5. Muat turun artifact APK dan install sebagai update. Pastikan applicationId sama.

PENTING:
- Uji pada telefon dahulu, dan uji di TV sebelum kegunaan sebenar.
- Jangan uninstall APK lama sebelum backup; uninstall boleh padam data jadual dan slide.
- Jadual JAKIM hendaklah disahkan untuk hari sebenar sebelum azan digunakan.
- PIN demo dalam MainActivity.java wajib ditukar sebelum dipasang secara kekal.
- Fail ini belum diuji bina Android dalam persekitaran ini; hanya sintaks JS baharu disemak.
