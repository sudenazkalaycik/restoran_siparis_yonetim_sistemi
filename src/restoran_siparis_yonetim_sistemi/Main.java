/*package restoran_siparis_yonetim_sistemi;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Program açıldığında metin dosyasından garsonları yükleyelim:
        List<Garsonlar> garsonlar = DataManager.loadGarsonlar(); 

        while (true) {
            System.out.println("\n--- Restoran Otomasyonu (Konsol) ---");
            System.out.println("1. Garson Ekle");
            System.out.println("2. Tüm Garsonları Listele");
            System.out.println("3. Adisyon İşlemleri");
            System.out.println("4. Kaydet ve Çıkış");
            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();

            switch (secim) {
                case 1:
                    System.out.print("Garson ID: ");
                    int garsonId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Garson Adı: ");
                    String ad = scanner.nextLine();
                    System.out.print("Garson Soyadı: ");
                    String soyad = scanner.nextLine();

                    Garsonlar yeniGarson = new Garsonlar(garsonId, ad, soyad);
                    garsonlar.add(yeniGarson);
                    System.out.println("Garson eklendi: " + ad + " " + soyad);
                    break;

                case 2:
                    if (garsonlar.isEmpty()) {
                        System.out.println("Hiç garson bulunmuyor.");
                    } else {
                        System.out.println("--- Garsonlar ---");
                        for (Garsonlar g : garsonlar) {
                            System.out.println("Garson ID: " + g.getGarsonId() + ", Ad: " + g.getAd()
                                    + ", Soyad: " + g.getSoyad());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Garson ID: ");
                    int secilenGarsonId = scanner.nextInt();
                    Garsonlar secilenGarson = null;

                    for (Garsonlar g : garsonlar) {
                        if (g.getGarsonId() == secilenGarsonId) {
                            secilenGarson = g;
                            break;
                        }
                    }
                    if (secilenGarson == null) {
                        System.out.println("Garson bulunamadı!");
                        break;
                    }

                    System.out.print("Adisyon No: ");
                    int adisyonNo = scanner.nextInt();
                    System.out.print("Masa No: ");
                    int masaNo = scanner.nextInt();
                    Adisyon adisyon = new Adisyon(adisyonNo, masaNo);
                    secilenGarson.adisyonEkle(adisyon);

                    while (true) {
                        System.out.println("\n--- Adisyon İşlemleri ---");
                        System.out.println("1. Sipariş Ekle");
                        System.out.println("2. Siparişleri Göster");
                        System.out.println("3. Adisyonu Kapat");
                        System.out.println("4. Geri Dön");
                        System.out.print("Seçiminiz: ");
                        int adisyonSecim = scanner.nextInt();

                        switch (adisyonSecim) {
                            case 1:
                                Siparis yeniSiparis = Siparis.kullanicidanSiparisAl();
                                adisyon.siparisEkle(yeniSiparis);
                                break;
                            case 2:
                                adisyon.siparisleriYazdir();
                                break;
                            case 3:
                                adisyon.adisyonKapat();
                                break;
                            case 4:
                                System.out.println("Adisyon işlemlerinden çıkılıyor.");
                                break;
                            default:
                                System.out.println("Geçersiz seçim.");
                        }
                        if (adisyonSecim == 4) break;
                    }// burayi yoruma alıyorum çünkü panel yerine konsoldan ver aldırıyor
                    break;

                case 4:
                    // Kaydet ve Programdan çık
                    DataManager.saveGarsonlar(garsonlar);
                    System.out.println("Veriler kaydedildi. Çıkış yapılıyor...");
                    return;

                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }
}*/