package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class GarsonPanel extends JPanel {
    private List<Garsonlar> garsonlar;
    private JTextArea textArea;
    private JButton btnGarsonEkle, btnListele;

    // Regex pattern: sadece harfler ve boşluklara izin verir
    private static final Pattern AD_SOYAD_PATTERN = Pattern.compile("^[A-Za-zÇĞİÖŞÜçğıöşü ]+$");

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

        // Orta panelde metin alanı
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Garson Ekle Butonu ActionListener
        btnGarsonEkle.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            int garsonId;
            try {
                garsonId = Integer.parseInt(idStr.trim());
                if (garsonId <= 0) {
                    JOptionPane.showMessageDialog(this, "Bu ID kullanılamaz, lütfen 0'dan büyük bir ID giriniz.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID formatı! Lütfen sadece sayısal değer giriniz.");
                return;
            }

            // ID kontrolü
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
            ad = ad.trim();
            if (!AD_SOYAD_PATTERN.matcher(ad).matches()) {
                JOptionPane.showMessageDialog(this, "Garson adı sadece harf ve boşluk içermelidir!");
                return;
            }

            String soyad = JOptionPane.showInputDialog("Garson Soyadı:");
            if (soyad == null || soyad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson soyadı boş olamaz!");
                return;
            }
            soyad = soyad.trim();
            if (!AD_SOYAD_PATTERN.matcher(soyad).matches()) {
                JOptionPane.showMessageDialog(this, "Garson soyadı sadece harf ve boşluk içermelidir!");
                return;
            }

            garsonlar.add(new Garsonlar(garsonId, ad, soyad));
            JOptionPane.showMessageDialog(this, "Garson eklendi.");
            listeleGarsonlar();
        });

        // Garson Sil Butonu ActionListener
        btnGarsonSil.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Silinecek Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            int garsonId;
            try {
                garsonId = Integer.parseInt(idStr.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID formatı! Lütfen sadece sayısal değer giriniz.");
                return;
            }

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
            } else {
                listeleGarsonlar();
            }
        });

        // Garson Güncelle Butonu ActionListener
        btnGarsonGuncelle.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Güncellenecek Garson ID:");
            if (idStr == null || idStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson ID boş olamaz!");
                return;
            }

            int garsonId;
            try {
                garsonId = Integer.parseInt(idStr.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz ID formatı! Lütfen sadece sayısal değer giriniz.");
                return;
            }

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
                newName = newName.trim();
                if (!AD_SOYAD_PATTERN.matcher(newName).matches()) {
                    JOptionPane.showMessageDialog(this, "Yeni ad sadece harf ve boşluk içermelidir!");
                    return;
                }

                String newSurname = JOptionPane.showInputDialog("Yeni Soyad:", foundGarson.getSoyad());
                if (newSurname == null || newSurname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Yeni soyad boş olamaz!");
                    return;
                }
                newSurname = newSurname.trim();
                if (!AD_SOYAD_PATTERN.matcher(newSurname).matches()) {
                    JOptionPane.showMessageDialog(this, "Yeni soyad sadece harf ve boşluk içermelidir!");
                    return;
                }

                foundGarson.setAd(newName);
                foundGarson.setSoyad(newSurname);

                JOptionPane.showMessageDialog(this, "Garson güncellendi.");
                listeleGarsonlar();
            } else {
                JOptionPane.showMessageDialog(this, "Bu ID'ye ait garson bulunamadı.");
            }
        });

        // Garson Listele Butonu ActionListener
        btnListele.addActionListener(e -> {
            listeleGarsonlar();
        });
    }

    /**
     * Garsonları listeleyen metod.
     */
    private void listeleGarsonlar() {
        textArea.setText("=== Garson Listesi ===\n");
        if (garsonlar.isEmpty()) {
            textArea.append("Kayıtlı garson yok.\n");
        } else {
            for (Garsonlar g : garsonlar) {
                textArea.append("ID: " + g.getGarsonId() + " | " + g.getAd() + " " + g.getSoyad() + "\n");
            }
        }
    }
}
