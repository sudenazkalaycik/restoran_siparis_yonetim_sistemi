package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
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
        JButton btnGarsonGuncelle = new JButton("Garson Güncelle");
        JButton btnGarsonSil = new JButton("Garson Sil");
        topPanel.add(btnGarsonEkle);
        topPanel.add(btnListele);
        topPanel.add(btnGarsonGuncelle);
        topPanel.add(btnGarsonSil);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Garson Güncelle Butonu
        btnGarsonGuncelle.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Güncellenecek Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            try {
                int garsonId = Integer.parseInt(idStr);
                Garsonlar foundGarson = null;

                // ID’ye sahip garsonu bulma
                for (Garsonlar g : garsonlar) {
                    if (g.getGarsonId() == garsonId) {
                        foundGarson = g;
                        break;
                    }
                }

                if (foundGarson != null) {
                    // Mevcut değerleri varsayılan olarak göstermek
                    String newName = JOptionPane.showInputDialog("Yeni Ad:", foundGarson.getAd());
                    if (newName == null || newName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Yeni ad boş olamaz!");
                        return;
                    }
                    String newSurname = JOptionPane.showInputDialog("Yeni Soyad:", foundGarson.getSoyad());
                    if (newSurname == null || newSurname.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Yeni soyad boş olamaz!");
                        return;
                    }

                    foundGarson.setAd(newName);
                    foundGarson.setSoyad(newSurname);

                    JOptionPane.showMessageDialog(this, "Garson güncellendi.");
                } else {
                    JOptionPane.showMessageDialog(this, "Bu ID'ye ait garson bulunamadı.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID.");
            }
        });

        // Garson Ekle Butonu
        btnGarsonEkle.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            try {
                int garsonId = Integer.parseInt(idStr);

                // ID kontrolü
                if (garsonId <= 0) {
                    JOptionPane.showMessageDialog(this, "Bu ID kullanılamaz, lütfen 0'dan büyük bir ID giriniz.");
                    return;
                }

                for (Garsonlar existingGarson : garsonlar) {
                    if (existingGarson.getGarsonId() == garsonId) {
                        JOptionPane.showMessageDialog(this, "Bu ID kullanımda, lütfen başka bir ID giriniz.");
                        return;
                    }
                }

                String ad = JOptionPane.showInputDialog("Garson Adı:");
                if (ad == null || ad.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Garson adı boş olamaz!");
                    return;
                }

                String soyad = JOptionPane.showInputDialog("Garson Soyadı:");
                if (soyad == null || soyad.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Garson soyadı boş olamaz!");
                    return;
                }

                garsonlar.add(new Garsonlar(garsonId, ad, soyad));
                JOptionPane.showMessageDialog(this, "Garson eklendi.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID.");
            }
        });

        // Garson Sil Butonu
        btnGarsonSil.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Silinecek Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            try {
                int garsonId = Integer.parseInt(idStr);
                boolean removed = false;

                for (int i = 0; i < garsonlar.size(); i++) {
                    if (garsonlar.get(i).getGarsonId() == garsonId) {
                        garsonlar.remove(i);
                        removed = true;
                        JOptionPane.showMessageDialog(this, "Garson silindi (ID: " + garsonId + ")");
                        break;
                    }
                }

                if (!removed) {
                    JOptionPane.showMessageDialog(this, "Bu ID'ye ait garson bulunamadı.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID.");
            }
        });

        // Garson Listele Butonu
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
