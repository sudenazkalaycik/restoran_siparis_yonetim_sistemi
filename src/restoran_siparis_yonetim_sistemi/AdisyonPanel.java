package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Önemli: ActionListener import'u
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdisyonPanel extends JPanel {
    // Adisyonları sakladığımız liste
    private List<Adisyon> adisyonlar = new ArrayList<>();

    // adisyon.oot dosyası
    private static final String DOSYA_ADI = "adisyon.oot";

    private JComboBox<Garsonlar> cbGarsonSec;
    private JTextArea textArea;
    private JButton btnOlustur, btnSiparisListele, btnAdisyonKapat;
    private List<Garsonlar> garsonlar;
    private Adisyon aktifAdisyon = null;
    private Menu menu; // Menü nesnesi

    // Components for adding Siparis
    private JComboBox<String> cbKategori;
    private JComboBox<String> cbUrunSec;
    private JLabel lblFiyat;
    private JTextField txtMiktar;
    private JButton btnEkleSiparis;

    public AdisyonPanel(List<Garsonlar> garsonlar, Menu menu) {
        this.garsonlar = garsonlar;
        this.menu = menu;
        setLayout(new BorderLayout());

        // Program başlarken dosyadan adisyonları yükle
        loadAdisyonlarFromFile();

        // Top panel: Garson seçimi ve adisyon oluşturma
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbGarsonSec = new JComboBox<>();
        for (Garsonlar g : garsonlar) {
            cbGarsonSec.addItem(g);
        }

        btnOlustur = new JButton("Yeni Adisyon");
        btnAdisyonKapat = new JButton("Adisyonu Kapat");

        topPanel.add(new JLabel("Garson Seç:"));
        topPanel.add(cbGarsonSec);
        topPanel.add(btnOlustur);
        topPanel.add(btnAdisyonKapat);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: Siparis ekleme form
        JPanel siparisPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kategori seçimi
        gbc.gridx = 0;
        gbc.gridy = 0;
        siparisPanel.add(new JLabel("Kategori:"), gbc);

        cbKategori = new JComboBox<>(new String[]{"Ana Yemek", "İçecek", "Tatlı"});
        gbc.gridx = 1;
        gbc.gridy = 0;
        siparisPanel.add(cbKategori, gbc);

        // Ürün seçimi
        gbc.gridx = 0;
        gbc.gridy = 1;
        siparisPanel.add(new JLabel("Ürün:"), gbc);

        cbUrunSec = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        siparisPanel.add(cbUrunSec, gbc);

        // Fiyat gösterimi
        gbc.gridx = 0;
        gbc.gridy = 2;
        siparisPanel.add(new JLabel("Fiyat:"), gbc);

        lblFiyat = new JLabel("0.0 TL");
        gbc.gridx = 1;
        gbc.gridy = 2;
        siparisPanel.add(lblFiyat, gbc);

        // Miktar girişi
        gbc.gridx = 0;
        gbc.gridy = 3;
        siparisPanel.add(new JLabel("Miktar:"), gbc);

        txtMiktar = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 3;
        siparisPanel.add(txtMiktar, gbc);

        // Sipariş ekle butonu
        btnEkleSiparis = new JButton("Sipariş Ekle");
        gbc.gridx = 1;
        gbc.gridy = 4;
        siparisPanel.add(btnEkleSiparis, gbc);

        add(siparisPanel, BorderLayout.WEST);

        // Bottom panel: Sipariş listeleme
        JPanel bottomPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        bottomPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.CENTER);

        // Ek buton: Siparişleri Listele
        btnSiparisListele = new JButton("Siparişleri Listele");
        bottomPanel.add(btnSiparisListele, BorderLayout.SOUTH);

        // ====================== ActionListeners (Java 7 uyumlu) ======================

        // "Yeni Adisyon" butonu
        btnOlustur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yeniAdisyon();
            }
        });

        // "Adisyonu Kapat" butonu
        btnAdisyonKapat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adisyonKapat();
            }
        });

        // Kategori combobox değişince ürünleri yükle
        cbKategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kategoriUrunleriniYukle();
            }
        });

        // Ürün combobox değişince fiyatı göster
        cbUrunSec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                urunFiyatiniGoster();
            }
        });

        // "Sipariş Ekle" butonu
        btnEkleSiparis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siparisEkle();
            }
        });

        // "Siparişleri Listele" butonu
        btnSiparisListele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siparisListele();
            }
        });

        // Başlangıçta kategoriyi yükle
        kategoriUrunleriniYukle();
    }

    // ===================== Dosya İşlemleri =====================

    /**
     * Uygulama başlarken adisyon.oot dosyasından kayıtlı adisyonları okur.
     */
    private void loadAdisyonlarFromFile() {
        File file = new File(DOSYA_ADI);
        if (!file.exists()) {
            return; // Dosya yoksa çık
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Adisyon adisyon = Adisyon.fromDataString(line);
                if (adisyon != null) {
                    adisyonlar.add(adisyon);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adisyonlar listesi değiştikçe adisyon.oot dosyasını günceller.
     */
    private void saveAdisyonlarToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA_ADI, false))) {
            for (Adisyon adisyon : adisyonlar) {
                bw.write(adisyon.toDataString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===================== Adisyon İşlemleri =====================

    private void yeniAdisyon() {
        Garsonlar secilenGarson = (Garsonlar) cbGarsonSec.getSelectedItem();
        if(secilenGarson == null) {
            JOptionPane.showMessageDialog(this, "Garson bulunamadı!");
            return;
        }

        String adisyonNoStr = JOptionPane.showInputDialog("Adisyon Numarası:");
        String masaNoStr = JOptionPane.showInputDialog("Masa Numarası:");
        if(adisyonNoStr == null || masaNoStr == null) return;

        try {
            int adisyonNo = Integer.parseInt(adisyonNoStr);
            int masaNo = Integer.parseInt(masaNoStr);

            // Aynı adisyonNo var mı kontrol edelim (opsiyonel)
            Adisyon existing = adisyonBul(adisyonNo);
            if (existing != null) {
                JOptionPane.showMessageDialog(this, "Bu adisyon numarası zaten var!");
                return;
            }

            // Yeni adisyon oluştur
            aktifAdisyon = new Adisyon(adisyonNo, masaNo);
            adisyonlar.add(aktifAdisyon); // Listemize ekleyelim
            secilenGarson.adisyonEkle(aktifAdisyon);

            textArea.setText("Yeni Adisyon oluşturuldu (Adisyon No:" + adisyonNo + ")\n");

            // Dosyayı güncelle
            saveAdisyonlarToFile();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz numara girişi.");
        }
    }

    private void adisyonKapat() {
        if(aktifAdisyon == null) {
            JOptionPane.showMessageDialog(this, "Henüz bir adisyon yok.");
            return;
        }
        aktifAdisyon.adisyonKapat();
        textArea.append("Adisyon kapatıldı. Toplam: " + aktifAdisyon.toplamTutarHesapla() + " TL\n");
        // Dosyayı güncelle
        saveAdisyonlarToFile();
        // Yeni adisyon açmak için null set
        aktifAdisyon = null;
    }

    private void siparisEkle() {
        if (aktifAdisyon == null) {
            JOptionPane.showMessageDialog(this, "Lütfen önce bir adisyon oluşturun.");
            return;
        }

        String kategori = (String) cbKategori.getSelectedItem();
        String urunAdi = (String) cbUrunSec.getSelectedItem();
        String miktarStr = txtMiktar.getText().trim();

        if (kategori == null || urunAdi == null || miktarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen kategori, ürün ve miktar giriniz.");
            return;
        }

        int miktar;
        try {
            miktar = Integer.parseInt(miktarStr);
            if (miktar <= 0) throw new NumberFormatException();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz miktar.");
            return;
        }

        double fiyat = 0.0;
        if ("Ana Yemek".equals(kategori)) {
            fiyat = menu.getAnaYemekler().getOrDefault(urunAdi, 0.0);
        } else if ("İçecek".equals(kategori)) {
            fiyat = menu.getIçecekler().getOrDefault(urunAdi, 0.0);
        } else if ("Tatlı".equals(kategori)) {
            fiyat = menu.getTatlilar().getOrDefault(urunAdi, 0.0);
        }

        if (fiyat <= 0.0) {
            JOptionPane.showMessageDialog(this, "Ürün fiyatı bulunamadı.");
            return;
        }

        Siparis siparis = new Siparis(urunAdi, fiyat, miktar);
        aktifAdisyon.siparisEkle(siparis);
        textArea.append("Sipariş Eklendi: " + miktar + " x " + urunAdi + " (" + fiyat + " TL)\n");

        // Her sipariş eklendiğinde de güncelle (toplam tutar değişir)
        saveAdisyonlarToFile();
    }

    private void siparisListele() {
        if (aktifAdisyon == null) {
            JOptionPane.showMessageDialog(this, "Henüz bir adisyon yok.");
            return;
        }
        textArea.append("\n--- Adisyon Siparişleri (No:" + aktifAdisyon.getAdisyonNo() + ") ---\n");
        if (aktifAdisyon.getSiparisler().isEmpty()) {
            textArea.append("Sipariş yok.\n");
        } else {
            for (Siparis s : aktifAdisyon.getSiparisler()) {
                double tutar = s.getFiyat() * s.getMiktar();
                textArea.append(s.getMiktar() + " x " + s.getUrunAdi() + " = " + tutar + " TL\n");
            }
            textArea.append("Toplam Tutar: " + aktifAdisyon.toplamTutarHesapla() + " TL\n");
        }
    }

    // ===================== Yardımcı Metotlar =====================

    /**
     * Kategori seçildiğinde ürünleri ComboBox'a ekler.
     */
    private void kategoriUrunleriniYukle() {
        cbUrunSec.removeAllItems();
        String kategori = (String) cbKategori.getSelectedItem();
        if (kategori == null) return;

        if ("Ana Yemek".equals(kategori)) {
            for (String urun : menu.getAnaYemekler().keySet()) {
                cbUrunSec.addItem(urun);
            }
        } else if ("İçecek".equals(kategori)) {
            for (String urun : menu.getIçecekler().keySet()) {
                cbUrunSec.addItem(urun);
            }
        } else if ("Tatlı".equals(kategori)) {
            for (String urun : menu.getTatlilar().keySet()) {
                cbUrunSec.addItem(urun);
            }
        }

        // Seçili ürüne göre fiyatı göster
        urunFiyatiniGoster();
    }

    /**
     * Seçilen ürüne göre fiyatı label'da gösterir.
     */
    private void urunFiyatiniGoster() {
        String kategori = (String) cbKategori.getSelectedItem();
        String urun = (String) cbUrunSec.getSelectedItem();
        if (kategori == null || urun == null) {
            lblFiyat.setText("0.0 TL");
            return;
        }

        double fiyat = 0.0;
        if ("Ana Yemek".equals(kategori)) {
            fiyat = menu.getAnaYemekler().getOrDefault(urun, 0.0);
        } else if ("İçecek".equals(kategori)) {
            fiyat = menu.getIçecekler().getOrDefault(urun, 0.0);
        } else if ("Tatlı".equals(kategori)) {
            fiyat = menu.getTatlilar().getOrDefault(urun, 0.0);
        }
        lblFiyat.setText(String.format("%.2f TL", fiyat));
    }

    /**
     * Adisyonlar içinden adisyonNo ile eşleşen adisyonu bulur.
     */
    private Adisyon adisyonBul(int adisyonNo) {
        for (Adisyon a : adisyonlar) {
            if (a.getAdisyonNo() == adisyonNo) {
                return a;
            }
        }
        return null;
    }
}
