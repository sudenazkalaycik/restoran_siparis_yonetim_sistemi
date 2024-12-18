package restoran_siparis_yonetim_sistemi;

import java.util.HashMap;
import java.util.Map;

public class Masa {
    private int numara;
    private boolean acikMi; 
    private int kapasite;
    private boolean rezerveMi;
    private Map<String, String> rezervasyonBilgileri;

    public Masa(int numara, int kapasite) {
        this.numara = numara;
        this.kapasite = kapasite;
        this.acikMi = false;
        this.rezerveMi = false;
        this.rezervasyonBilgileri = new HashMap<>();
    }

    public void rezerveEt(String musteriAdi, String telNo) {
        this.rezerveMi = true;
        rezervasyonBilgileri.put("musteriAdi", musteriAdi);
        rezervasyonBilgileri.put("telNo", telNo);
        System.out.println("Masa " + numara + " rezerve edildi. Müşteri: " + musteriAdi);
    }

    public void rezervasyonIptal() {
        this.rezerveMi = false;
        rezervasyonBilgileri.clear();
        System.out.println("Masa " + numara + " rezervasyonu iptal edildi.");
    }

    public void masaAc() {
        if(!acikMi) {
            acikMi = true;
            System.out.println(numara + " numaralı masa açıldı.");
        }
    }

    public void masaKapat() {
        if(acikMi) {
            acikMi = false;
            System.out.println(numara + " numaralı masa kapatıldı.");
        }
    }

    public int getNumara() {
        return numara;
    }

    public boolean isAcikMi() {
        return acikMi;
    }

    public int getKapasite() {
        return kapasite;
    }

    public boolean isRezerveMi() {
        return rezerveMi;
    }
}
