package restoran_siparis_yonetim_sistemi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MasaPanel extends JPanel {
    private List<Masa> masalar;
    private JTextArea textArea;
    private JButton btnMasaEkle, btnMasaAc, btnMasaKapat, btnRezerveEt, btnIptalRez;
    private JTextField txtMasaNo, txtKapasite;

    public MasaPanel() {
        masalar = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnMasaEkle = new JButton("Masa Ekle");
        btnMasaAc = new JButton("Masa Aç");
        btnMasaKapat = new JButton("Masa Kapat");
        btnRezerveEt = new JButton("Rezerve Et");
        btnIptalRez = new JButton("Rez. İptal");

        txtMasaNo = new JTextField(5);
        txtKapasite = new JTextField(5);
        topPanel.add(new JLabel("Masa No:"));
        topPanel.add(txtMasaNo);
        topPanel.add(new JLabel("Kapasite:"));
        topPanel.add(txtKapasite);

        topPanel.add(btnMasaEkle);
        topPanel.add(btnMasaAc);
        topPanel.add(btnMasaKapat);
        topPanel.add(btnRezerveEt);
        topPanel.add(btnIptalRez);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        btnMasaEkle.addActionListener(e -> masaEkle());
        btnMasaAc.addActionListener(e -> masaAc());
        btnMasaKapat.addActionListener(e -> masaKapat());
        btnRezerveEt.addActionListener(e -> rezerveEt());
        btnIptalRez.addActionListener(e -> rezervasyonIptal());
    }

    private Masa masaBul(int no) {
        for(Masa m : masalar) {
            if(m.getNumara() == no) return m;
        }
        return null;
    }

    private void masaEkle() {
        try {
            int numara = Integer.parseInt(txtMasaNo.getText());
            int kapasite = Integer.parseInt(txtKapasite.getText());
            Masa yeniMasa = new Masa(numara, kapasite);
            masalar.add(yeniMasa);
            textArea.append("Masa eklendi: No=" + numara + ", Kapasite=" + kapasite + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz giriş.");
        }
    }

    private void masaAc() {
        try {
            int no = Integer.parseInt(txtMasaNo.getText());
            Masa m = masaBul(no);
            if(m != null) {
                m.masaAc();
                textArea.append(no + " numaralı masa açıldı.\n");
            } else {
                JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz Masa No.");
        }
    }

    private void masaKapat() {
        try {
            int no = Integer.parseInt(txtMasaNo.getText());
            Masa m = masaBul(no);
            if(m != null) {
                m.masaKapat();
                textArea.append(no + " numaralı masa kapatıldı.\n");
            } else {
                JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz Masa No.");
        }
    }

    private void rezerveEt() {
        try {
            int no = Integer.parseInt(txtMasaNo.getText());
            Masa m = masaBul(no);
            if(m != null) {
                String musteriAdi = JOptionPane.showInputDialog("Müşteri Adı:");
                if(musteriAdi == null) return;
                String telNo = JOptionPane.showInputDialog("Telefon:");
                if(telNo == null) return;
                m.rezerveEt(musteriAdi, telNo);
                textArea.append(no + " numaralı masa rezerve edildi.\n");
            } else {
                JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz Masa No.");
        }
    }

    private void rezervasyonIptal() {
        try {
            int no = Integer.parseInt(txtMasaNo.getText());
            Masa m = masaBul(no);
            if(m != null) {
                m.rezervasyonIptal();
                textArea.append(no + " numaralı masanın rezervasyonu iptal.\n");
            } else {
                JOptionPane.showMessageDialog(this, "Masa bulunamadı.");
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz Masa No.");
        }
    }
}
