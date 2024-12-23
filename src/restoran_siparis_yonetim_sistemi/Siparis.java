package restoran_siparis_yonetim_sistemi;


public class Siparis {
    private String urunAdi;
    private double fiyat;
    private int miktar;

    public Siparis(String urunAdi, double fiyat, int miktar) {
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
        this.miktar = miktar;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public double getFiyat() {
        return fiyat;
    }

    public int getMiktar() {
        return miktar;
    }

    public void siparisDetaylariYazdir() {
        System.out.println(miktar + " x " + urunAdi + " = " + (fiyat * miktar) + " TL");
    }


}
