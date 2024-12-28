
package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
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
        super("Restoran Sipariş Yönetimi (GUI)");

        garsonlar = DataManager.loadGarsonlar(); 
        menu = DataManager.loadMenu(); // Menü verilerini dosyadan yüklüyoruz

        // Paneller
        garsonPanel = new GarsonPanel(garsonlar);
        menuPanel = new MenuPanel(menu);
        adisyonPanel = new AdisyonPanel(garsonlar, menu); // Menü nesnesini geç
        masaPanel = new MasaPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Garson Yönetimi", garsonPanel);
        tabbedPane.addTab("Menü Yönetimi", menuPanel);
        tabbedPane.addTab("Adisyon Yönetimi", adisyonPanel);
        tabbedPane.addTab("Masa Yönetimi", masaPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Kaydet & Çıkış butonları
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnKaydet = new JButton("Kaydet (Dosyaya Yaz)");
        JButton btnCikis = new JButton("Çıkış");
        bottomPanel.add(btnKaydet);
        bottomPanel.add(btnCikis);
        add(bottomPanel, BorderLayout.SOUTH);

        btnKaydet.addActionListener(e -> {
            DataManager.saveGarsonlar(garsonlar); 
            DataManager.saveMenu(menu); // Menü verilerini kaydediyoruz
            // Masa verilerini de kaydetmek isterseniz:
            // DataManager.saveMasalar(masalar);
            JOptionPane.showMessageDialog(this, "Veriler kaydedildi.");
        });

        btnCikis.addActionListener(e -> System.exit(0));

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapanmayı sağlar
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
