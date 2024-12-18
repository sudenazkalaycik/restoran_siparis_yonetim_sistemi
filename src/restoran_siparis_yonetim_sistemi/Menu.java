package restoran_siparis_yonetim_sistemi;

import java.util.HashMap;
import java.util.Map;

public class Menu {
	// Ürün kategorileri (HashMap: Ürün adı -> Fiyat)
	private Map<String, Double> icecekler = new HashMap<>();
	private Map<String, Double> anaYemekler = new HashMap<>();
	private Map<String, Double> tatlilar = new HashMap<>();

	public void anayemekEkle(String urunAdi, double fiyat) {
		anaYemekler.put(urunAdi, fiyat);
	}

	public void anayemekSil(String urunAdi) {
		anaYemekler.remove(urunAdi);
	}

	public void tatliEkle(String urunAdi, double fiyat) {
		tatlilar.put(urunAdi, fiyat);
	}

	public void tatliSil(String urunAdi) {
		tatlilar.remove(urunAdi);
	}

	public void icecekEkle(String urunAdi, double fiyat) {
		icecekler.put(urunAdi, fiyat);
	}

	public void icecekSil(String urunAdi) {
		icecekler.remove(urunAdi);
	}

	public void fiyatGuncelleTatli(String urunAdi, double yeniFiyat) {
		if (tatlilar.containsKey(urunAdi)) {
			tatlilar.put(urunAdi, yeniFiyat);
		}
	}

	public void fiyatGuncelleIcecek(String urunAdi, double yeniFiyat) {
		if (icecekler.containsKey(urunAdi)) {
			icecekler.put(urunAdi, yeniFiyat);
		}
	}

	public void fiyatGuncelleAnayemek(String urunAdi, double yeniFiyat) {
		if (anaYemekler.containsKey(urunAdi)) {
			anaYemekler.put(urunAdi, yeniFiyat);
		}
	}

	public boolean urunAra(String urunAdi) {
		return icecekler.containsKey(urunAdi) || anaYemekler.containsKey(urunAdi) || tatlilar.containsKey(urunAdi);
	}

	public void menuGoruntule() {
		System.out.println("=== MENÜ ===");
		System.out.println("Ana Yemekler:");
		for (String key : anaYemekler.keySet()) {
			System.out.println(key + " - " + anaYemekler.get(key) + " TL");
		}
		System.out.println("\nİçecekler:");
		for (String key : icecekler.keySet()) {
			System.out.println(key + " - " + icecekler.get(key) + " TL");
		}
		System.out.println("\nTatlılar:");
		for (String key : tatlilar.keySet()) {
			System.out.println(key + " - " + tatlilar.get(key) + " TL");
		}
	}

	public String getMenuString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ana Yemekler:\n");
		for (String yemek : anaYemekler.keySet()) {
			sb.append("  ").append(yemek).append(" - ").append(anaYemekler.get(yemek)).append(" TL\n");
		}
		sb.append("\nİçecekler:\n");
		for (String icecek : icecekler.keySet()) {
			sb.append("  ").append(icecek).append(" - ").append(icecekler.get(icecek)).append(" TL\n");
		}
		sb.append("\nTatlılar:\n");
		for (String tatli : tatlilar.keySet()) {
			sb.append("  ").append(tatli).append(" - ").append(tatlilar.get(tatli)).append(" TL\n");
		}
		return sb.toString();
	}
	
	public Map<String, Double> getAnaYemekler() {
	    return anaYemekler;
	}
	public Map<String, Double> getIçecekler() {
	    return icecekler;
	}
	public Map<String, Double> getTatlilar() {
	    return tatlilar;
	}


}
