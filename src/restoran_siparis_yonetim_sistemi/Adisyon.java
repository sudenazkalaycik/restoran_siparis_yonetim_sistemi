package restoran_siparis_yonetim_sistemi;

import java.util.ArrayList;
import java.util.List;

public class Adisyon {
    private int adisyonNo;
    private int masaNo;
    private List<Siparis> siparisler;
    private double toplamTutar;
    private boolean acik;

    public Adisyon(int adisyonNo, int masaNo) {
        this.adisyonNo = adisyonNo;
        this.masaNo = masaNo;
        this.siparisler = new ArrayList<>();
        this.toplamTutar = 0.0;
        this.acik = true;
    }

    public void siparisEkle(Siparis siparis) {
        if (acik) {
            siparisler.add(siparis);
            toplamTutar += siparis.getFiyat() * siparis.getMiktar();
            System.out.println(siparis.getMiktar() + " x " + siparis.getUrunAdi() + " eklendi. Ara toplam: "
                               + (siparis.getFiyat() * siparis.getMiktar()) + " TL");
        } else {
            System.out.println("Adisyon kapalı, sipariş eklenemez.");
        }
    }

    public double toplamTutarHesapla() {
        return toplamTutar;
    }

    public void adisyonKapat() {
        if (acik) {
            acik = false;
            System.out.println("Adisyon kapatıldı. Toplam tutar: " + toplamTutar + " TL");
        } else {
            System.out.println("Adisyon zaten kapalı.");
        }
    }

    public void siparisleriYazdir() {
        if (siparisler.isEmpty()) {
            System.out.println("Adisyonda sipariş yok.");
        } else {
            System.out.println("Adisyon Siparişleri:");
            for (Siparis siparis : siparisler) {
                siparis.siparisDetaylariYazdir();
            }
        }
    }

    public List<Siparis> getSiparisler() {
        return siparisler;
    }

    public int getAdisyonNo() {
        return adisyonNo;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public boolean isAcik() {
        return acik;
    }
}
