package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Bu panelde:
 * 1) Kategori seçimi (Ana Yemek / İçecek / Tatlı)
 * 2) Seçili kategorideki ürünleri ComboBox'ta listeleme
 * 3) Miktar girişi
 * 4) "Sipariş Ekle" butonuna basarak adisyona sipariş ekleme
 * 5) "Siparişleri Listele" butonuyla Adisyon içeriğini görebilme
 */
public class SiparisPanel extends JPanel {
    private Adisyon aktifAdisyon; // Hangi adisyona sipariş ekleyeceğiz
    private Menu menu;            // Kullanıcı, menüdeki ürünlerden sipariş seçecek

    private JComboBox<String> cbKategori;   // "Ana Yemek", "İçecek", "Tatlı" seçenekleri
    private JComboBox<String> cbUrunSec;    // Seçili kategoriye ait ürünlerin listesi
    private JTextField txtMiktar;
    private JTextArea textArea;

    private JButton btnUrunListele, btnSiparisEkle, btnSiparisleriGoster;

    public SiparisPanel(Adisyon aktifAdisyon, Menu menu) {
        this.aktifAdisyon = aktifAdisyon;
        this.menu = menu;
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        // Üst panel: kategori seçimi, ürün seçimi ve miktar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbKategori = new JComboBox<>(new String[]{"Ana Yemek", "İçecek", "Tatlı"});
        cbUrunSec = new JComboBox<>();
        txtMiktar = new JTextField(5);
        btnUrunListele = new JButton("Kategori Ürünlerini Göster");
        btnSiparisEkle = new JButton("Sipariş Ekle");
        btnSiparisleriGoster = new JButton("Adisyonu Listele");

        topPanel.add(new JLabel("Kategori:"));
        topPanel.add(cbKategori);
        topPanel.add(btnUrunListele);
        topPanel.add(new JLabel("Ürün:"));
        topPanel.add(cbUrunSec);
        topPanel.add(new JLabel("Miktar:"));
        topPanel.add(txtMiktar);
        topPanel.add(btnSiparisEkle);
        topPanel.add(btnSiparisleriGoster);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea(15, 50);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // ActionListener'lar
        btnUrunListele.addActionListener(e -> kategoriUrunleriniYukle());
        btnSiparisEkle.addActionListener(e -> siparisEkle());
        btnSiparisleriGoster.addActionListener(e -> siparisleriListele());
    }

    /**
     * Seçili kategoriye (Ana Yemek / İçecek / Tatlı) ait ürünleri ComboBox'a yükler.
     */
    private void kategoriUrunleriniYukle() {
        cbUrunSec.removeAllItems(); 
        String kategori = (String) cbKategori.getSelectedItem();
        if (kategori == null) return;

        switch(kategori) {
            case "Ana Yemek":
                for (Map.Entry<String, Double> entry : menu.getAnaYemekler().entrySet()) {
                    cbUrunSec.addItem(entry.getKey()); 
                }
                break;
            case "İçecek":
                for (Map.Entry<String, Double> entry : menu.getIçecekler().entrySet()) {
                    cbUrunSec.addItem(entry.getKey());
                }
                break;
            case "Tatlı":
                for (Map.Entry<String, Double> entry : menu.getTatlilar().entrySet()) {
                    cbUrunSec.addItem(entry.getKey());
                }
                break;
        }
    }

    /**
     * Seçilen ürünü ve miktarı alarak Adisyon’a Siparis nesnesi ekler.
     */
    private void siparisEkle() {
        if (aktifAdisyon == null) {
            JOptionPane.showMessageDialog(this, "Adisyon tanımlı değil!");
            return;
        }

        String urunAdi = (String) cbUrunSec.getSelectedItem();
        if (urunAdi == null) {
            JOptionPane.showMessageDialog(this, "Lütfen önce kategori ve ürün seçiniz.");
            return;
        }

        String miktarStr = txtMiktar.getText().trim();
        if (miktarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Miktar giriniz.");
            return;
        }

        int miktar;
        try {
            miktar = Integer.parseInt(miktarStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz miktar.");
            return;
        }

        // Menüdeki fiyatı bulalım
        double fiyat = 0.0;
        String kategori = (String) cbKategori.getSelectedItem();
        switch(kategori) {
            case "Ana Yemek":
                fiyat = menu.getAnaYemekler().getOrDefault(urunAdi, 0.0);
                break;
            case "İçecek":
                fiyat = menu.getIçecekler().getOrDefault(urunAdi, 0.0);
                break;
            case "Tatlı":
                fiyat = menu.getTatlilar().getOrDefault(urunAdi, 0.0);
                break;
        }

        if (fiyat <= 0) {
            JOptionPane.showMessageDialog(this, "Ürün fiyatı 0 veya ürün bulunamadı.");
            return;
        }

        // Sipariş nesnesi oluşturup Adisyon'a ekleyelim
        Siparis siparis = new Siparis(urunAdi, fiyat, miktar);
        aktifAdisyon.siparisEkle(siparis);
        textArea.append("Sipariş Eklendi: " + miktar + " x " + urunAdi + " (" + fiyat + " TL)\n");
    }

    /**
     * Adisyondaki siparişleri textArea'ya yazar.
     */
    private void siparisleriListele() {
        if (aktifAdisyon == null) {
            JOptionPane.showMessageDialog(this, "Adisyon tanımlı değil!");
            return;
        }
        textArea.append("\n--- Adisyon (" + aktifAdisyon.getAdisyonNo() + ") Sipariş Listesi ---\n");
        if (aktifAdisyon.getSiparisler().isEmpty()) {
            textArea.append("Henüz sipariş yok.\n");
        } else {
            for (Siparis s : aktifAdisyon.getSiparisler()) {
                double tutar = s.getFiyat() * s.getMiktar();
                textArea.append(s.getMiktar() + " x " + s.getUrunAdi() + " = " + tutar + " TL\n");
            }
            textArea.append("Toplam Tutar: " + aktifAdisyon.toplamTutarHesapla() + " TL\n\n");
        }
    }
}
