package restoran_siparis_yonetim_sistemi;

public class Masa {
    private int numara;
    private boolean acikMi;
    private int kapasite;

    public Masa(int numara, int kapasite) {
        this.numara = numara;
        this.kapasite = kapasite;
        this.acikMi = false;
    }

    public void masaAc() {
        if (!acikMi) {
            acikMi = true;
            System.out.println(numara + " numaralı masa açıldı.");
        }
    }

    public void masaKapat() {
        if (acikMi) {
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

    // Masa bilgisini dosyaya kaydederken satır olarak dönüştürüyoruz.
    // Örnek satır formatı: "numara;kapasite;acikMi"
    public String toDataString() {
        return numara + ";" + kapasite + ";" + acikMi;
    }

    // Dosyadan okunan satırı parse edip Masa nesnesi oluşturan yardımcı metot.
    public static Masa fromDataString(String dataLine) {
        // dataLine örneği: "5;4;true"
        String[] parts = dataLine.split(";");
        if (parts.length < 3) return null; // Geçersiz satır
        try {
            int numara = Integer.parseInt(parts[0]);
            int kapasite = Integer.parseInt(parts[1]);
            boolean acikMi = Boolean.parseBoolean(parts[2]);
            Masa m = new Masa(numara, kapasite);
            if (acikMi) m.masaAc(); // Eğer dosyada açık görünüyorsa, masaAc() çağır
            return m;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
