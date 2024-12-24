package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    private GarsonPanel garsonPanel;
    private MenuPanel menuPanel;
    private AdisyonPanel adisyonPanel;
    private MasaPanel masaPanel;

    private List<Garsonlar> garsonlar; 
    private Menu menu;                

    public MainFrame() {
        super("Restoran Sipariş Yönetimi");

        // Nimbus Look and Feel'i Ayarla
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus Look and Feel yüklenemedi, varsayılan görünüm kullanılacak.");
        }

        garsonlar = DataManager.loadGarsonlar(); 
        menu = DataManager.loadMenu(); // Menü verilerini dosyadan yüklüyoruz

        // Paneller
        garsonPanel = new GarsonPanel(garsonlar);
        menuPanel = new MenuPanel(menu);
        adisyonPanel = new AdisyonPanel(garsonlar, menu); // Menü nesnesini geç
        masaPanel = new MasaPanel();

        // Sekmeli Bölme
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tabbedPane.addTab("Garson Yönetimi", garsonPanel);
        tabbedPane.addTab("Menü Yönetimi", menuPanel);
        tabbedPane.addTab("Adisyon Yönetimi", adisyonPanel);
        tabbedPane.addTab("Masa Yönetimi", masaPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Kaydet & Çıkış Butonları
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Kenar boşlukları ekle

        JButton btnKaydet = new JButton("Kaydet (Dosyaya Yaz)");
        JButton btnCikis = new JButton("Çıkış");

        // Butonları Stilize Et
        btnKaydet.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 14));

        bottomPanel.add(btnKaydet);
        bottomPanel.add(btnCikis);
        add(bottomPanel, BorderLayout.SOUTH);

        // Buton Aksiyonları
        btnKaydet.addActionListener(e -> {
            DataManager.saveGarsonlar(garsonlar); 
            DataManager.saveMenu(menu); // Menü verilerini kaydediyoruz
            // Masa verilerini de kaydetmek isterseniz:
            // DataManager.saveMasalar(masalar);
            JOptionPane.showMessageDialog(this, "Veriler kaydedildi.");
        });

        btnCikis.addActionListener(e -> System.exit(0));

        // Pencere Ayarları
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapanmayı sağlar
        setLocationRelativeTo(null);

        // Arayüzün genel görünümünü iyileştirmek için bazı ek ayarlamalar
        applyGlobalStyles();
    }

    /**
     * Arayüzün genel görünümünü iyileştirmek için global stiller uygular.
     */
    private void applyGlobalStyles() {
        // Tabloların görünümünü iyileştir
        UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 14));
        UIManager.put("TableHeader.font", new Font("Tahoma", Font.BOLD, 14));
        UIManager.put("TableHeader.background", new Color(220, 220, 220));
        UIManager.put("Table.alternateRowColor", new Color(245, 245, 245));

        // Butonların kenar boşluklarını artır
        UIManager.put("Button.margin", new Insets(10, 20, 10, 20));

        // Metin alanlarının fontunu ayarla
        UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Tahoma", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 14));

        // Update existing components
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        // Swing bileşenlerinin güvenli bir şekilde oluşturulmasını sağlar
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
