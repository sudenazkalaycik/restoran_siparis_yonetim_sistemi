package restoran_siparis_yonetim_sistemi;

import java.util.Scanner;

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

    // Konsol tabanlı metodu kaldırıyoruz
    /*
    public static Siparis kullanicidanSiparisAl() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ürün adı: ");
        String urunAdi = scanner.nextLine();
        System.out.print("Fiyat: ");
        double fiyat = scanner.nextDouble();
        System.out.print("Miktar: ");
        int miktar = scanner.nextInt();
        scanner.nextLine(); // buffer temizliği
        return new Siparis(urunAdi, fiyat, miktar);
    }
    */
}
