# Jawaban Week 4 Lab

## Latihan 1

### Eksperimen 1: @Controller vs @RestController
- /test/view → **Dengan `@Controller` dan return `"test"` akan merender halaman HTML `test.html` (view Thymeleaf). Dengan `@RestController`, string `"test"` dikirim apa adanya sebagai teks, bukan dicari templatenya.**
- /test/text → **Dengan `@Controller` + `@ResponseBody` atau dengan `@RestController`, nilai `String` (mis. `"Hello World"`) akan langsung muncul sebagai teks di browser, bukan sebagai nama view.**
- Kesimpulan: **`@Controller` digunakan untuk mengembalikan *nama view* (HTML/Thymeleaf) dan butuh `@ResponseBody` jika ingin mengembalikan teks/data mentah. `@RestController` selalu menganggap nilai return sebagai *response body*, sehingga nama view tidak lagi di‑resolve sebagai template.**

### Eksperimen 2: Template Tidak Ada
- HTTP Status: **500 Internal Server Error**
- Error: **`org.thymeleaf.exceptions.TemplateInputException: Error resolving template 'namaTemplate', template might not exist or might not be accessible by any of the configured Template Resolvers` (pesan intinya: template tidak ditemukan atau tidak bisa diakses).**

### Eksperimen 3: @RequestParam vs @PathVariable
| URL | Hasil |
|---|---|
| /greet | **Menampilkan salam dengan nilai default, misalnya `"Hello, World"` karena parameter `name` tidak dikirim (`@RequestParam` punya `defaultValue`).** |
| /greet?name=Budi | **Menampilkan `"Hello, Budi"` karena nilai diambil dari query parameter `name` lewat `@RequestParam`.** |
| /greet/Budi | **Menampilkan `"Hello, Budi"` jika mapping menggunakan `@GetMapping("/greet/{name}")` dan mengambil nilai dari bagian path lewat `@PathVariable`.** |
| /greet?nama=Budi | **Tetap memakai nilai default (mis. `"World"`) karena nama parameter yang dibaca `@RequestParam` adalah `name`, bukan `nama`, sehingga query param yang salah tidak terbaca.** |

### Pertanyaan Refleksi
1. **Perbedaan utama `@Controller` dan `@RestController` adalah pada cara Spring menangani nilai yang direturn: `@Controller` mengarah ke view (template) sedangkan `@RestController` langsung menuliskan nilai return sebagai body HTTP (cocok untuk API).**
2. **`@ResponseBody` pada method di dalam `@Controller` membuat perilakunya sama seperti method di dalam `@RestController`, yaitu mengirimkan nilai return langsung ke body response tanpa melewati view resolver.**
3. **Error ketika template tidak ada terjadi karena view resolver (ThymeleafViewResolver) tidak menemukan file HTML dengan nama yang diminta, sehingga melempar `TemplateInputException` dan menghasilkan HTTP 500.**
4. **`@RequestParam` cocok ketika data dikirim lewat query string/form (mis. `?name=Budi`) dan lebih fleksibel untuk parameter opsional; `@PathVariable` cocok ketika data adalah bagian dari struktur URL (mis. `/greet/Budi`) sehingga URL lebih rapi dan RESTful.**
5. **Jika nama parameter di URL tidak sesuai dengan nama yang diminta di `@RequestParam`/`@PathVariable`, value tidak akan terbaca sehingga bisa jatuh ke nilai default atau memicu error (mis. 400) jika param tersebut wajib (`required = true`).**
6. **Model pada Spring MVC berfungsi sebagai “wadah data” yang diteruskan dari controller ke view, sehingga template Thymeleaf bisa menampilkan nilai dinamis menggunakan ekspresi seperti `th:text="${nama}"`.**
7. **Pemisahan peran antara controller, model, dan view (pola MVC) memudahkan pemeliharaan kode karena logika bisnis, data, dan tampilan tidak tercampur, sehingga lebih mudah diuji, dikembangkan, dan di‑refactor.**

## Latihan 2

### Eksperimen 1: Fragment Tidak Ada
- **Ketika Thymeleaf mencoba `th:replace` atau `th:insert` ke fragment yang tidak ada, aplikasi akan menghasilkan HTTP 500 dengan error `TemplateInputException`/`TemplateProcessingException` yang menjelaskan bahwa fragment atau template tujuan tidak bisa ditemukan.**

### Eksperimen 2: Static Resource
- **File statis (CSS, JS, gambar) yang ditempatkan di `src/main/resources/static` bisa diakses langsung oleh browser melalui URL seperti `/css/style.css` atau `/images/logo.png` tanpa perlu controller. Jika path atau nama file salah, server akan mengembalikan HTTP 404 Not Found.**

### Pertanyaan Refleksi
1. **Kelebihan menggunakan fragment di Thymeleaf adalah kita bisa membuat bagian layout yang dapat digunakan ulang (navbar, footer, header) sehingga kode HTML lebih DRY, mudah dikelola, dan konsisten di banyak halaman.**
2. **Jika fragment tidak ditemukan, biasanya berarti salah penulisan nama template/fragment atau struktur direktori tidak sesuai; solusi umumnya adalah memastikan nama dan path benar serta fragment dideklarasikan dengan `th:fragment`.**
3. **Static resource dipisahkan dari template agar server dapat menyajikannya secara efisien tanpa logika tambahan dan agar struktur proyek lebih rapi (template untuk tampilan dinamis, static untuk aset yang tidak berubah).**
4. **Spring Boot secara otomatis meng‑serve static resource dari lokasi standar (`/static`, `/public`, `/resources`, `/META-INF/resources`) sehingga kita cukup meletakkan file di sana tanpa konfigurasi tambahan.**
5. **Dari percobaan, saya belajar alur lengkap request di Spring MVC: URL dicocokkan ke controller, controller menyiapkan data (Model), view resolver mencari template/fragment, lalu browser memuat HTML dan mengambil static resource yang dibutuhkan (CSS/JS/gambar).**

