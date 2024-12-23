package restoran_siparis_yonetim_sistemi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String GARSON_DOSYA_ADI = "garsonlar.oot";
    private static final String MENU_DOSYA_ADI = "menu.oot";
    private static final String MASA_DOSYA_ADI = "masalar.oot";

    // Garson verilerini kaydetme
    public static void saveGarsonlar(List<Garsonlar> garsonListesi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GARSON_DOSYA_ADI))) {
            // Her satırda: GARSON#garsonId#ad#soyad
            for (Garsonlar garson : garsonListesi) {
                writer.write("GARSON#" + garson.getGarsonId() + "#" + garson.getAd() + "#" + garson.getSoyad());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Garson verilerini yükleme
    public static List<Garsonlar> loadGarsonlar() {
        List<Garsonlar> garsonListesi = new ArrayList<>();
        File file = new File(GARSON_DOSYA_ADI);
        if (!file.exists()) {
            return garsonListesi; // Dosya yoksa boş liste döner
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(GARSON_DOSYA_ADI))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parca = satir.split("#");
                if (parca.length >= 4 && parca[0].equals("GARSON")) {
                    int id = Integer.parseInt(parca[1]);
                    String ad = parca[2];
                    String soyad = parca[3];
                    Garsonlar g = new Garsonlar(id, ad, soyad);
                    garsonListesi.add(g);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return garsonListesi;
    }

    // Menü verilerini kaydetme
    public static void saveMenu(Menu menu) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_DOSYA_ADI))) {
            // Her satırda: KATEGORI#URUN_ADI#FIYAT
            for (Map.Entry<String, Double> entry : menu.getAnaYemekler().entrySet()) {
                writer.write("ANA_YEMEK#" + entry.getKey() + "#" + entry.getValue());
                writer.newLine();
            }
            for (Map.Entry<String, Double> entry : menu.getIçecekler().entrySet()) {
                writer.write("ICECEK#" + entry.getKey() + "#" + entry.getValue());
                writer.newLine();
            }
            for (Map.Entry<String, Double> entry : menu.getTatlilar().entrySet()) {
                writer.write("TATLI#" + entry.getKey() + "#" + entry.getValue());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Menü verilerini yükleme
    public static Menu loadMenu() {
        Menu menu = new Menu();
        File file = new File(MENU_DOSYA_ADI);
        if (!file.exists()) {
            return menu; // Dosya yoksa boş menu döner
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_DOSYA_ADI))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parca = satir.split("#");
                if (parca.length >= 3) {
                    String kategori = parca[0];
                    String urunAdi = parca[1];
                    double fiyat = Double.parseDouble(parca[2]);
                    switch(kategori) {
                        case "ANA_YEMEK":
                            menu.anayemekEkle(urunAdi, fiyat);
                            break;
                        case "ICECEK":
                            menu.icecekEkle(urunAdi, fiyat);
                            break;
                        case "TATLI":
                            menu.tatliEkle(urunAdi, fiyat);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }

    // Masa verilerini kaydetme (örnek)
    public static void saveMasalar(List<Masa> masalar) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MASA_DOSYA_ADI))) {
            for (Masa masa : masalar) {
                writer.write("MASA#" + masa.getNumara() + "#" + masa.getKapasite() + "#" + masa.isAcikMi() + "#" );
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Masa verilerini yükleme (örnek)
    public static List<Masa> loadMasalar() {
        List<Masa> masalar = new ArrayList<>();
        File file = new File(MASA_DOSYA_ADI);
        if (!file.exists()) {
            return masalar; // Dosya yoksa boş liste döner
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(MASA_DOSYA_ADI))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parca = satir.split("#");
                if (parca.length >= 5 && parca[0].equals("MASA")) {
                    int numara = Integer.parseInt(parca[1]);
                    int kapasite = Integer.parseInt(parca[2]);
                    boolean acikMi = Boolean.parseBoolean(parca[3]);
                    Masa masa = new Masa(numara, kapasite);
                    if(acikMi) masa.masaAc();
                    masalar.add(masa);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return masalar;
    }
}
