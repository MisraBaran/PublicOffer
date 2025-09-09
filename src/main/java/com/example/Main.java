package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      
        dbProcess db1 = new dbProcess();
         //method çağırmA
        //db1.dbInsert("123456", "Mert","Baran","70","1000");
       
        //nesne oluştur o nesen eile o methoda eriş     


        dosyaokuma dosyaokuma1 = new dosyaokuma();

        //dosyaokuma1.dosyaOkuVeListele("/Users/mbaran/Desktop/denemeDosya");
        //dosyaokuma1.dosyayiOku("/Users/mbaran/Desktop/denemeDosya");



        List<Object[]> lotListesi = new ArrayList<>();
        lotListesi = dosyaokuma1.dosyayiOku("/Users/mbaran/Desktop/denemeDosya/");

        for (Object[] veri : lotListesi) {
            db1.dbInsert(veri[0].toString(),veri[1].toString(),veri[2].toString(),veri[3].toString(),(Long) veri[4],(Long)veri[5]);   
        }
   
        // Veritabanından request listesini al
        List<Request> requests = db1.getRequestsFromDatabase();


        int totalAmountToDistribute = 182;
        EqualDistribution distribution = new EqualDistribution(requests, totalAmountToDistribute);
        distribution.distribute();
        distribution.printDistribution();

        db1.updateDistributedAmounts(requests);

    }
        
    }
