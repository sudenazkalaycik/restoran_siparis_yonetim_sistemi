package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
    private Menu menu;
    private JTextArea textArea;
    private JButton btnEkle, btnSil, btnFiyatGuncelle, btnAra, btnListele;

    public MenuPanel(Menu menu) {
        this.menu = menu;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnEkle = new JButton("Ürün Ekle");
        btnSil = new JButton("Ürün Sil");
        btnFiyatGuncelle = new JButton("Fiyat Güncelle");
        btnAra = new JButton("Ürün Ara");
        btnListele = new JButton("Menü Listele");
        topPanel.add(btnEkle);
        topPanel.add(btnSil);
        topPanel.add(btnFiyatGuncelle);
        topPanel.add(btnAra);
        topPanel.add(btnListele);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        btnEkle.addActionListener(e -> urunEkle());
        btnSil.addActionListener(e -> urunSil());
        btnFiyatGuncelle.addActionListener(e -> fiyatGuncelle());
        btnAra.addActionListener(e -> urunAra());
        btnListele.addActionListener(e -> listeleMenu());
    }

    private void urunEkle() {
        String kategori = JOptionPane.showInputDialog("Kategori: (anaYemek / tatli / icecek)");
        if(kategori == null) return;

        String urun = JOptionPane.showInputDialog("Ürün Adı:");
        if(urun == null) return;

        String fiyatStr = JOptionPane.showInputDialog("Fiyat:");
        if(fiyatStr == null) return;

        double fiyat;
        try {
            fiyat = Double.parseDouble(fiyatStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz fiyat.");
            return;
        }

        switch(kategori.toLowerCase()) {
            case "anayemek":
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

        listeleMenu(); // Eklendikten sonra menüyü güncelle
    }

    private void urunSil() {
        String kategori = JOptionPane.showInputDialog("Kategori: (anaYemek / tatli / icecek)");
        if(kategori == null) return;

        String urun = JOptionPane.showInputDialog("Silinecek Ürün Adı:");
        if(urun == null) return;

        switch(kategori.toLowerCase()) {
            case "anayemek":
                menu.anayemekSil(urun);
                JOptionPane.showMessageDialog(this, "Ana yemek silindi.");
                break;
            case "tatli":
                menu.tatliSil(urun);
                JOptionPane.showMessageDialog(this, "Tatlı silindi.");
                break;
            case "icecek":
                menu.icecekSil(urun);
                JOptionPane.showMessageDialog(this, "İçecek silindi.");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
                return;
        }

        listeleMenu(); // Silindikten sonra menüyü güncelle
    }

    private void fiyatGuncelle() {
        String kategori = JOptionPane.showInputDialog("Kategori: (anaYemek / tatli / icecek)");
        if(kategori == null) return;

        String urun = JOptionPane.showInputDialog("Fiyatı güncellenecek ürün adı:");
        if(urun == null) return;

        String yeniFiyatStr = JOptionPane.showInputDialog("Yeni fiyat:");
        if(yeniFiyatStr == null) return;

        double yeniFiyat;
        try {
            yeniFiyat = Double.parseDouble(yeniFiyatStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz fiyat.");
            return;
        }

        switch(kategori.toLowerCase()) {
            case "anayemek":
                menu.fiyatGuncelleAnayemek(urun, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (Ana Yemek).");
                break;
            case "tatli":
                menu.fiyatGuncelleTatli(urun, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (Tatlı).");
                break;
            case "icecek":
                menu.fiyatGuncelleIcecek(urun, yeniFiyat);
                JOptionPane.showMessageDialog(this, "Fiyat güncellendi (İçecek).");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Geçersiz kategori.");
        }

        listeleMenu(); // Güncellendikten sonra menüyü güncelle
    }

    private void urunAra() {
        String urunAdi = JOptionPane.showInputDialog("Aranacak Ürün Adı:");
        if(urunAdi == null) return;
        if(menu.urunAra(urunAdi)) {
            JOptionPane.showMessageDialog(this, urunAdi + " menüde mevcut.");
        } else {
            JOptionPane.showMessageDialog(this, urunAdi + " menüde bulunamadı.");
        }
    }

    private void listeleMenu() {
        textArea.setText("=== MENÜ ===\n");
        textArea.append(menu.getMenuString());
    }
}
