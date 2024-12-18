package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GarsonPanel extends JPanel {
    private List<Garsonlar> garsonlar;
    private JTextArea textArea;
    private JButton btnGarsonEkle, btnListele;

    public GarsonPanel(List<Garsonlar> garsonlar) {
        this.garsonlar = garsonlar;
        setLayout(new BorderLayout());

        // Üst panelde butonlar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnGarsonEkle = new JButton("Garson Ekle");
        btnListele = new JButton("Garson Listele");
        topPanel.add(btnGarsonEkle);
        topPanel.add(btnListele);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buton işlevleri
        btnGarsonEkle.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Garson ID:");
            if (idStr == null) return;
            try {
                int garsonId = Integer.parseInt(idStr);
                String ad = JOptionPane.showInputDialog("Garson Adı:");
                if(ad == null) return;
                String soyad = JOptionPane.showInputDialog("Garson Soyadı:");
                if(soyad == null) return;
                Garsonlar g = new Garsonlar(garsonId, ad, soyad);
                garsonlar.add(g);
                JOptionPane.showMessageDialog(this, "Garson Eklendi: " + ad + " " + soyad);
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID.");
            }
        });

        btnListele.addActionListener(e -> {
            textArea.setText("=== Garson Listesi ===\n");
            if (garsonlar.isEmpty()) {
                textArea.append("Kayıtlı garson yok.\n");
            } else {
                for (Garsonlar g : garsonlar) {
                    textArea.append("ID: " + g.getGarsonId() + " | " + g.getAd() + " " + g.getSoyad() + "\n");
                }
            }
        });
    }
}
