package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MasaPanel extends JPanel {
    private static final String DOSYA_ADI = "masalar.oot"; // .oot uzantılı dosya

    private List<Masa> masalar;
    private JTextArea textArea;
    private JButton btnMasaEkle, btnMasaAc, btnMasaKapat, btnListele; 
    private JTextField txtMasaNo, txtKapasite;

    public MasaPanel() {
        masalar = new ArrayList<>();
        setLayout(new BorderLayout());

        // Dosyadan var olan masaları yükle
        loadMasalarFromFile();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnMasaEkle = new JButton("Masa Ekle");
        btnMasaAc = new JButton("Masa Aç");
        btnMasaKapat = new JButton("Masa Kapat");
        btnListele = new JButton("Masaları Listele");

        txtMasaNo = new JTextField(5);
        txtKapasite = new JTextField(5);

        topPanel.add(new JLabel("Masa No:"));
        topPanel.add(txtMasaNo);
        topPanel.add(new JLabel("Kapasite:"));
        topPanel.add(txtKapasite);

        topPanel.add(btnMasaEkle);
        topPanel.add(btnMasaAc);
        topPanel.add(btnMasaKapat);
        topPanel.add(btnListele);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Buton dinleyicileri
        btnMasaEkle.addActionListener(e -> masaEkle());
        btnMasaAc.addActionListener(e -> masaAc());
        btnMasaKapat.addActionListener(e -> masaKapat());
        btnListele.addActionListener(e -> masaListele());
    }

    /**
     * String ifadenin pozitif tamsayı içerip içermediğini kontrol eder.
     */
    private boolean isPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void masaEkle() {
        // "Masa No" ve "Kapasite" sadece pozitif int olmalı
        if (!isPositiveInteger(txtMasaNo.getText()) || !isPositiveInteger(txtKapasite.getText())) {
            JOptionPane.showMessageDialog(this, "Lütfen pozitif tamsayı değerleri giriniz.");
            return;
        }

        int numara = Integer.parseInt(txtMasaNo.getText());
        int kapasite = Integer.parseInt(txtKapasite.getText());

        // Aynı numaraya sahip masa var mı kontrolü (opsiyonel)
        if (masaBul(numara) != null) {
            JOptionPane.showMessageDialog(this, "Bu numaraya sahip bir masa zaten var!");
            return;
        }

        Masa yeniMasa = new Masa(numara, kapasite);
        masalar.add(yeniMasa);
        textArea.append("Masa eklendi: No=" + numara + ", Kapasite=" + kapasite + "\n");

        // Dosyayı güncelle
        saveMasalarToFile();
    }

    private void masaAc() {
        // "Masa No" sadece pozitif int olmalı
        if (!isPositiveInteger(txtMasaNo.getText())) {
            JOptionPane.showMessageDialog(this, "Lütfen pozitif tamsayı bir 'Masa No' giriniz.");
            return;
        }

        int no = Integer.parseInt(txtMasaNo.getText());
        Masa m = masaBul(no);
        if (m != null) {
            m.masaAc();
            textArea.append(no + " numaralı masa açıldı.\n");
            saveMasalarToFile();
        } else {
            JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
        }
    }

    private void masaKapat() {
        // "Masa No" sadece pozitif int olmalı
        if (!isPositiveInteger(txtMasaNo.getText())) {
            JOptionPane.showMessageDialog(this, "Lütfen pozitif tamsayı bir 'Masa No' giriniz.");
            return;
        }

        int no = Integer.parseInt(txtMasaNo.getText());
        Masa m = masaBul(no);
        if (m != null) {
            m.masaKapat();
            textArea.append(no + " numaralı masa kapatıldı.\n");
            saveMasalarToFile();
        } else {
            JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
        }
    }

    private void masaListele() {
        textArea.setText("=== MASA LİSTESİ ===\n");
        if (masalar.isEmpty()) {
            textArea.append("Kayıtlı masa bulunmuyor.\n");
            return;
        }
        for (Masa m : masalar) {
            String durum = m.isAcikMi() ? "Açık" : "Kapalı";
            textArea.append("No=" + m.getNumara()
                    + ", Kapasite=" + m.getKapasite()
                    + ", Durum=" + durum + "\n");
        }
    }

    private Masa masaBul(int no) {
        for (Masa m : masalar) {
            if (m.getNumara() == no) {
                return m;
            }
        }
        return null;
    }

    private void loadMasalarFromFile() {
        File file = new File(DOSYA_ADI);
        if (!file.exists()) {
            return; // Dosya yoksa herhangi bir şey yapmıyoruz
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Masa m = Masa.fromDataString(line);
                if (m != null) {
                    masalar.add(m);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMasalarToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA_ADI, false))) {
            for (Masa m : masalar) {
                bw.write(m.toDataString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
