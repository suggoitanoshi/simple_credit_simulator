# Credit Simulator

A credit simulator written in Java.

# Requirements:

- Java 17
- A PC

# How to build

Run `./gradlew shadowJar`. This will make a fat JAR.

The fat JAR will be available in `app/build/libs/app-all.jar`

# How to run

## Running with standard input

If you haven't built the jar, you can run by running `./gradlew run`.
Otherwise, just run the `app-all.jar`: `java -jar app/build/libs/app-all.jar`.

At first, you will be greeted by the menu.
```
Credit Simulator
mulai: mulai input simulasi kredit
menu: menu ini
hitung: tampilkan perhitungan
load: ambil data dari API
exit: keluar dari credit simulator
```

You can start the simulation input by entering `mulai`.
Then, input your vehicle type.

```
---Input Simulasi Kredit---
batal: batalkan simulasi kredit
jenis kendaraan [mobil/motor]: 
mobil
```

Then you need to input the condition.

```
kondisi kendaraan [bekas/baru]: 
bekas
```

After that, input the vehicle's year.
Remember, the year must be `current year - 1` if condition is "baru".

```
tahun kendaraan [0-9999]: 
2015
```

You will be prompted to input the loan amount, tenor, and the 
down payment amount.

```
jumlah pinjaman [0-1000000000]: 
100000000

tenor pinjaman dalam tahun [1-6]: 
3

Jumlah DP: 
25000000
```

After correctly entering all the prompted input, the calculation will show.
Then the menu will appear again.

```
Hasil perhitungan kredit:
Tahun 1: Rp.27000000.00, Rate: 8.00
Tahun 2: Rp.29187000.00, Rate: 8.10
Tahun 3: Rp.31697082.00, Rate: 8.60
Credit Simulator
mulai: mulai input simulasi kredit
menu: menu ini
hitung: tampilkan perhitungan
load: ambil data dari API
exit: keluar dari credit simulator
```

If at any point the input has a wrong format, it will retry the prompt.

You can show again the credit calculation from the last simulation by entering `hitung`.

```
Hasil perhitungan kredit:
Tahun 1: Rp.27000000.00, Rate: 8.00
Tahun 2: Rp.29187000.00, Rate: 8.10
Tahun 3: Rp.31697082.00, Rate: 8.60
Credit Simulator
mulai: mulai input simulasi kredit
menu: menu ini
hitung: tampilkan perhitungan
load: ambil data dari API
exit: keluar dari credit simulator
```

By using `load`, you can load a data from JSON Web Service API.
Currently, it is hardcoded to `https://mocki.io/v1/22072efd-9006-47e7-9a9b-34a53de59b17`

```
Hasil perhitungan kredit:
Tahun 1: Rp.90000000.00, Rate: 8.00
Tahun 2: Rp.97290000.00, Rate: 8.10
Tahun 3: Rp.105656940.00, Rate: 8.60
Tahun 4: Rp.114849093.79, Rate: 8.70
Tahun 5: Rp.125415210.42, Rate: 9.20
Tahun 6: Rp.137078824.99, Rate: 9.30
Credit Simulator
mulai: mulai input simulasi kredit
menu: menu ini
hitung: tampilkan perhitungan
load: ambil data dari API
exit: keluar dari credit simulator
```

Lastly, you can try using status to get the data you have inputted.

```
Tipe kendaraan: Mobil
Status kendaraan: Baru
Tahun kendaraan: 2025
Tenor: 6
Jumlah pinjaman: 1000000000
Jumlah DP: 500000000

```

Exit just exits the app.

```
Exiting...
Goodbye!
```

## Running with text input

If you haven't built the jar, you can run by running `./gradlew run --args="<input file absolute path>"`.
Otherwise, just run the `app-all.jar`: `java -jar app/build/libs/app-all.jar <input file path>`.

The expected format for the input file is exactly the same as if inputting to the simulator's prompts.

