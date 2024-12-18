package restoran_siparis_yonetim_sistemi;

import java.util.ArrayList;
import java.util.List;

public class Garsonlar {
    private int garsonId;
    private String ad;
    private String soyad;
    private List<Adisyon> sorumluAdisyonlar;

    public Garsonlar(int garsonId, String ad, String soyad) {
        this.garsonId = garsonId;
        this.ad = ad;
        this.soyad = soyad;
        this.sorumluAdisyonlar = new ArrayList<>();
    }

    public void adisyonEkle(Adisyon adisyon) {
        sorumluAdisyonlar.add(adisyon);
        System.out.println("Adisyon " + adisyon.getAdisyonNo() + " garson " + this + " tarafından eklendi.");
    }

    public void sorumluAdisyonlariListele() {
        System.out.println("Garson: " + ad + " " + soyad + " - Sorumlu adisyonlar:");
        for (Adisyon adisyon : sorumluAdisyonlar) {
            System.out.println("Adisyon No: " + adisyon.getAdisyonNo() + ", Masa No: " + adisyon.getMasaNo() 
                               + ", Toplam Tutar: " + adisyon.toplamTutarHesapla());
        }
    }

    public void adisyonKapat(int adisyonNo) {
        for (Adisyon adisyon : sorumluAdisyonlar) {
            if (adisyon.getAdisyonNo() == adisyonNo && adisyon.isAcik()) {
                adisyon.adisyonKapat();
                return;
            }
        }
        System.out.println("Adisyon bulunamadı veya zaten kapalı.");
    }

    public int getGarsonId() {
        return garsonId;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public List<Adisyon> getSorumluAdisyonlar() {
        return sorumluAdisyonlar;
    }

    @Override
    public String toString() {
        return "ID: " + garsonId + " - " + ad + " " + soyad;
    }
}
