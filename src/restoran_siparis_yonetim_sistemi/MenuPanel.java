package restoran_siparis_yonetim_sistemi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MenuPanel extends JPanel {
    private Menu menu;
    private JTextArea textArea;
    private JButton btnEkle, btnSil, btnFiyatGuncelle, btnAra, btnListele, btnFiyataGoreSirala;

    public MenuPanel(Menu menu) {
        this.menu = menu;
        setLayout(new BorderLayout());

        // Üst panel (Butonlar)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnEkle = new JButton("Ürün Ekle");
        btnSil = new JButton("Ürün Sil");
        btnFiyatGuncelle = new JButton("Fiyat Güncelle");
        btnAra = new JButton("Ürün Ara");
        btnListele = new JButton("Menü Listele");
        btnFiyataGoreSirala = new JButton("Fiyata Göre Sırala");

        topPanel.add(btnEkle);
        topPanel.add(btnSil);
        topPanel.add(btnFiyatGuncelle);
        topPanel.add(btnAra);
        topPanel.add(btnListele);
        topPanel.add(btnFiyataGoreSirala);

        add(topPanel, BorderLayout.NORTH);

        // Orta panel (Metin alanı)
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Butonların actionListener'ları
        btnEkle.addActionListener(e -> urunEkle());
        btnSil.addActionListener(e -> urunSil());
        btnFiyatGuncelle.addActionListener(e -> fiyatGuncelle());
        btnAra.addActionListener(e -> urunAra());
        btnListele.addActionListener(e -> listeleMenu());
        btnFiyataGoreSirala.addActionListener(e -> fiyataGoreSirala());
    }

    
    private boolean urunZatenVar(String urunAdi) {
        // Tüm kategorilerdeki ürünleri tek bir Map'te toplayarak kontrol edebiliriz
        Map<String, Double> tumUrunler = new HashMap<>();
        tumUrunler.putAll(menu.getAnaYemekler());
        tumUrunler.putAll(menu.getTatlilar());
        tumUrunler.putAll(menu.getIçecekler());

        for (String mevcutUrun : tumUrunler.keySet()) {
            if (mevcutUrun.equalsIgnoreCase(urunAdi)) {
                return true;
            }
        }
        return false;
    }

    private void urunEkle() {
        String[] kategoriler = {"anaYemek", "tatli", "icecek"};
        String kategori = (String) JOptionPane.showInputDialog(
                this,
                "Kategori seçiniz:",
                "Kategori",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kategoriler,
                kategoriler[0]
        );
        if (kategori == null) return;

        String urun = JOptionPane.showInputDialog("Ürün Adı:");
        if (urun == null || urun.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ürün adı boş olamaz!");
            return;
        }

        // Yeni ekleme: ürün zaten var mı kontrolü
        if (urunZatenVar(urun)) {
            JOptionPane.showMessageDialog(this, "Bu ürün menüde zaten mevcut!");
            return;
        }

        String fiyatStr = JOptionPane.showInputDialog("Fiyat (pozitif, double):");
        if (fiyatStr == null) return;

        double fiyat;
        try {
            fiyat = Double.parseDouble(fiyatStr);
            if (fiyat <= 0) {
                JOptionPane.showMessageDialog(this, "Fiyat pozitif bir değer olmalı!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz fiyat formatı!");
            return;
        }

        switch (kategori) {
            case "anaYemek":
                menu.anayemekEkle(urun, fiyat);
                JOptionPane.showMessageDialog(this, "Ana yemek eklendi.");
                break;
            case "tatli":
                menu.tatliEkle(urun, fiyat);
                JOptionPane.showMessageDialog(this, "Tatlı eklendi.");
                break;
            case "icecek":
                menu.icecekEkle(urun, fiyat);
                JOptionPane.showMessageDialog(this, "İçecek eklendi.");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
                return;
        }

        listeleMenu();
    }

    private void urunSil() {
        String[] kategoriler = {"anaYemek", "tatli", "icecek"};
        String kategori = (String) JOptionPane.showInputDialog(
                this,
                "Kategori seçiniz:",
                "Kategori",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kategoriler,
                kategoriler[0]
        );
        if (kategori == null) return;

        String[] urunler = null;
        switch (kategori) {
            case "anaYemek":
                urunler = menu.getAnaYemekler().keySet().toArray(new String[0]);
                break;
            case "tatli":
                urunler = menu.getTatlilar().keySet().toArray(new String[0]);
                break;
            case "icecek":
                urunler = menu.getIçecekler().keySet().toArray(new String[0]);
                break;
        }

        if (urunler == null || urunler.length == 0) {
            JOptionPane.showMessageDialog(this, "Seçilen kategoride silinecek ürün bulunamadı.");
            return;
        }

        String urunSecimi = (String) JOptionPane.showInputDialog(
                this,
                "Silinecek ürünü seçiniz:",
                "Ürün Seçimi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                urunler,
                urunler[0]
        );
        if (urunSecimi == null) return;

        switch (kategori) {
            case "anaYemek":
                menu.anayemekSil(urunSecimi);
                JOptionPane.showMessageDialog(this, "Ana yemek silindi.");
                break;
            case "tatli":
                menu.tatliSil(urunSecimi);
                JOptionPane.showMessageDialog(this, "Tatlı silindi.");
                break;
            case "icecek":
                menu.icecekSil(urunSecimi);
                JOptionPane.showMessageDialog(this, "İçecek silindi.");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
                return;
        }

        listeleMenu();
    }

    private void fiyatGuncelle() {
        String[] kategoriler = {"anaYemek", "tatli", "icecek"};
        String kategori = (String) JOptionPane.showInputDialog(
                this,
                "Kategori seçiniz:",
                "Kategori",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kategoriler,
                kategoriler[0]
        );
        if (kategori == null) return;

        String[] urunler;
        switch (kategori) {
            case "anaYemek":
                urunler = menu.getAnaYemekler().keySet().toArray(new String[0]);
                break;
            case "tatli":
                urunler = menu.getTatlilar().keySet().toArray(new String[0]);
                break;
            case "icecek":
                urunler = menu.getIçecekler().keySet().toArray(new String[0]);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
                return;
        }

        if (urunler.length == 0) {
            JOptionPane.showMessageDialog(this, "Seçilen kategoride güncellenecek ürün yok.");
            return;
        }

        String urunSecimi = (String) JOptionPane.showInputDialog(
                this,
                "Fiyatı güncellenecek ürünü seçiniz:",
                "Ürün Seçimi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                urunler,
                urunler[0]
        );
        if (urunSecimi == null) return;

        String yeniFiyatStr = JOptionPane.showInputDialog("Yeni fiyat (pozitif, double):");
        if (yeniFiyatStr == null) return;

        double yeniFiyat;
        try {
            yeniFiyat = Double.parseDouble(yeniFiyatStr);
            if (yeniFiyat <= 0) {
                JOptionPane.showMessageDialog(this, "Fiyat pozitif bir değer olmalı!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz fiyat formatı!");
            return;
        }

        switch (kategori) {
            case "anaYemek":
                menu.fiyatGuncelleAnayemek(urunSecimi, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (Ana Yemek).");
                break;
            case "tatli":
                menu.fiyatGuncelleTatli(urunSecimi, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (Tatlı).");
                break;
            case "icecek":
                menu.fiyatGuncelleIcecek(urunSecimi, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (İçecek).");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
        }

        listeleMenu();
    }

    private void urunAra() {
        String urunAdi = JOptionPane.showInputDialog("Aranacak Ürün Adı:");
        if (urunAdi == null || urunAdi.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aranacak ürün adı boş olamaz!");
            return;
        }

        // Büyük/Küçük harf duyarsız arama için tüm ürünleri kontrol ediyoruz
        double fiyat = -1;

        // Ana Yemekler içinde arama
        for (Map.Entry<String, Double> entry : menu.getAnaYemekler().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(urunAdi.trim())) {
                fiyat = entry.getValue();
                break;
            }
        }

        // Tatlılar içinde arama
        if (fiyat == -1) {
            for (Map.Entry<String, Double> entry : menu.getTatlilar().entrySet()) {
                if (entry.getKey().equalsIgnoreCase(urunAdi.trim())) {
                    fiyat = entry.getValue();
                    break;
                }
            }
        }

        // İçecekler içinde arama
        if (fiyat == -1) {
            for (Map.Entry<String, Double> entry : menu.getIçecekler().entrySet()) {
                if (entry.getKey().equalsIgnoreCase(urunAdi.trim())) {
                    fiyat = entry.getValue();
                    break;
                }
            }
        }

        if (fiyat != -1) {
            JOptionPane.showMessageDialog(this, urunAdi + " menüde mevcut. Fiyat: " + fiyat + " TL");
        } else {
            JOptionPane.showMessageDialog(this, urunAdi + " menüde bulunamadı.");
        }
    }

    private void listeleMenu() {
        textArea.setText("=== MENÜ ===\n");
        textArea.append(menu.getMenuString());
    }

    private void fiyataGoreSirala() {
        Object[] secenekler = {"Yüksekten Düşüğe", "Düşükten Yükseğe"};
        int secim = JOptionPane.showOptionDialog(
                this,
                "Fiyata göre nasıl sıralamak istersiniz?",
                "Sıralama Seçimi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                secenekler,
                secenekler[0]
        );
        if (secim == JOptionPane.CLOSED_OPTION) return;

        // Tüm ürünleri tek Map içerisinde toplama
        Map<String, Double> tumUrunler = new HashMap<>();
        tumUrunler.putAll(menu.getAnaYemekler());
        tumUrunler.putAll(menu.getTatlilar());
        tumUrunler.putAll(menu.getIçecekler());

        // Entry listesi oluşturma
        List<Entry<String, Double>> urunListesi = new ArrayList<>(tumUrunler.entrySet());

        // Sıralama
        if (secim == 0) {
            // Yüksekten Düşüğe
            urunListesi.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
        } else {
            // Düşükten Yükseğe
            urunListesi.sort(Comparator.comparingDouble(Entry::getValue));
        }

        // Sonuçları ekrana yazdırma
        textArea.setText("=== FİYATA GÖRE SIRALANMIŞ MENÜ ===\n");
        for (Entry<String, Double> entry : urunListesi) {
            textArea.append(entry.getKey() + " - " + entry.getValue() + " TL\n");
        }
    }
}
