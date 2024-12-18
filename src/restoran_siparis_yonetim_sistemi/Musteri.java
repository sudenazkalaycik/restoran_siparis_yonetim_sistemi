package restoran_siparis_yonetim_sistemi;

import java.util.ArrayList;

public class Musteri {
    private int musteriID;
    private String isim;
    private String telNo;
    private ArrayList<Adisyon> siparisGecmisi; 

    public Musteri(int musteriID, String isim, String telNo) {
        this.musteriID = musteriID;
        this.isim = isim;
        this.telNo = telNo;
        this.siparisGecmisi = new ArrayList<>();
    }

    // Müşteriye yeni adisyon ekleme
    public void addSiparis(Adisyon adisyon) {
        siparisGecmisi.add(adisyon);
    }

    // Sipariş geçmişini listeleme
    public void listeleSiparisGecmisi() {
        System.out.println(isim + " adlı müşterinin geçmiş siparişleri:");
        for(Adisyon ad : siparisGecmisi) {
            System.out.println("Adisyon No: " + ad.getAdisyonNo() + ", Masa No: " + ad.getMasaNo()
                               + ", Toplam: " + ad.toplamTutarHesapla());
        }
    }

    public int getMusteriID() {
        return musteriID;
    }

    public String getIsim() {
        return isim;
    }

    public String getTelNo() {
        return telNo;
    }

    public void guncelleTelefon(String yeniTel) {
        this.telNo = yeniTel;
    }
}
