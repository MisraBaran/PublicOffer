package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dosyaokuma {

    public List<Object[]> dosyayiOku(String dosyaYolu) {
        List<Object[]> lotListesi = new ArrayList<>();

        File dosya = new File(dosyaYolu);
        if (dosya.isDirectory()) {
            File[] txtDosyalari = dosya.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (txtDosyalari != null) {
                for (File txtDosyasi : txtDosyalari) {
                    lotListesi.addAll(dosyayiOku(txtDosyasi.getAbsolutePath()));
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
                String satir;
                while ((satir = br.readLine()) != null) {
                    String[] veri = satir.split(";"); // Noktalı virgül ile satırı parçala

                    // Veri türlerini dönüştürerek Object[] dizisi oluştur
                    Object[] row = new Object[6];
                    row[0] = veri[0]; // tc 
                    row[1] = veri[1]; // username TEXT NOT NULL
                    row[2] = veri[2]; // surname TEXT
                    row[3] = veri[3]; // organizationName
                    try {
                        row[4] = Long.parseLong(veri[4]); // requestedAmount
                        row[5] = Long.parseLong(veri[5]); // minLotAmount
                    } catch (NumberFormatException e) {
                        System.err.println("Veri format hatası: " + e.getMessage());
                        // Burada uygun bir işlem yapabilirsiniz, örneğin default bir değer atayabilirsiniz
                    }

                    lotListesi.add(row); // Parçalanan veriyi listeye ekle
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Dosya okuma hatası: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return lotListesi;
    }

    public void dosyaOkuVeListele(String dosyaYolu) {
        List<Object[]> lotListesi = dosyayiOku(dosyaYolu);

        // Listeyi ekrana basma (test için)
        for (Object[] veri : lotListesi) {
            // Her bir satırdaki verileri doğru türde almak için String.valueOf() gibi
            // yöntemlerle tür dönüşümü yapılabilir.
            System.out.println(veri[0] + ", " + veri[1] + ", " + veri[2] + ", " + veri[3] + ", " + veri[4] + ", " + veri[5]);
        }
    }


}
