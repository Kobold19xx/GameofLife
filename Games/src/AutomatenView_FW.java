import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

// Einheitliche Oberfläche für mehrere Automaten
// Nebenbei: Menüleiste mit Menüpunkten
// Nebenbei: Thread.sleep() und Thread.interrupt()
// Nebenbei: Speichern und Laden von Objekten
// mfg.fw, 08-12-2022

public class AutomatenView_FW extends JFrame {
	// -----------------------------------------------------------------
	// Eigenschaften ---------------------------------------------------
	private JTable tabelle;
	private Automat automat; // umbau: GOL sondern allgemeiner: Automat

	// insbesondere für das Berechnen mehrerer Generationen
	private JTextField txtAnzahl = new JTextField("" + 5);
	private Thread endlos; // für die Endlosschleife - dort muss ich irgendwo endlos.start() und
							// endlos.interrupt()
	private int verzoegerung = 500;

	// Spezielle Methoden, hier "normales" File-Management -------------
	// Überlegenswert: in separater Klasse "auslagern"
	void speichern() {
		System.out.println("Speichern ...");
		try {
			// 1. Einen FileOutputStream erzeugen
			// (wenn die Datei speicherTest.ser nicht existiert, wird sie angelegt)
			FileOutputStream dateiStrom = new FileOutputStream("speicherTest.ser");

			// 2. eine ObjectOutputStream erzeugen
			ObjectOutputStream objektOut = new ObjectOutputStream(dateiStrom);

			// 3. die Objekte schreiben
			objektOut.writeObject(automat);

			// 4. ObjectOutputStream schließen
			objektOut.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	void laden() {
		System.out.println("Laden ...");
		try {
			// 1. Einen FileInputStream erzeugen
			// (wenn die Datei speicherTest.ser nicht existiert, wird sie angelegt)
			FileInputStream dateiStrom = new FileInputStream("speicherTest.ser");

			// 2. eine ObjectInputStream erzeugen
			ObjectInputStream objektIn = new ObjectInputStream(dateiStrom);

			// 3. die Objekte lesen
			Object a = objektIn.readObject();

			// 4. Objekte casten, denn readObject() wird immer Objekte vom Typ Object
			// liefern
			automat = (Automat) a;
			tabelle.setModel(automat);
			// 5. ObjectOutputStream schließen
			objektIn.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------
	// über den Konstruktor wird das Fenster gebastelt -----------------
	AutomatenView_FW() {

		setSize(600, 500); // das Übliche für den Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a new menu bar ---------------------------------------
		JMenuBar jmb = new JMenuBar();
		JMenu jmFile = new JMenu("Datei");
		JMenuItem jmiOpen = new JMenuItem("Laden");
		JMenuItem jmiSave = new JMenuItem("Speichern");
		JMenu jmNew = new JMenu("Neu");
		JMenuItem jmiGOL = new JMenuItem("GameOfLive");
		JMenuItem jmiAddi = new JMenuItem("Addition");
		JMenuItem jmiMulti = new JMenuItem("Multiplikation");

		jmiAddi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Neues Spiel ...Addi");
				automat = new Addierer(); // erst hier wird ein Objekt aus der Klasse gezogen
				tabelle.setModel(automat);
			}
		});

		jmiMulti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Neues Spiel ...Multi");
				automat = new Multiplikator(); // erst hier wird ein Objekt aus der Klasse gezogen
				tabelle.setModel(automat);
			}
		});

		jmiOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				laden();
			}
		});
		jmiSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speichern();
			}
		});
		jmiGOL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Neues Spiel ...GOL");
				automat = new GameOfLife_FW(); // erst hier wird ein Objekt aus der Klasse gezogen
				tabelle.setModel(automat);
			}
		});

		// Add the menu items to the menu
		jmNew.add(jmiGOL);
		jmNew.add(jmiAddi);
		jmNew.add(jmiMulti);
		jmFile.add(jmNew);
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmb.add(jmFile);

		// Add the menu bar to the frame
		setJMenuBar(jmb);

		// die unteren Buttons für die Einzelberechnung ----------------
		JPanel buttonsUnten = new JPanel();
		JButton next1 = new JButton("Nächste Generation");
		JButton next2 = new JButton("Mehrere Generationen:");
		txtAnzahl.setColumns(3);
		buttonsUnten.add(next1);
		buttonsUnten.add(next2);
		buttonsUnten.add(txtAnzahl);

		next1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Nächste Generation");
				automat.berechneNeueGeneration();
			}
		});
		next2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mehrere Generationen");
				int anzahl = Integer.parseInt(txtAnzahl.getText());
				automat.berechneMehrereGenerationen(anzahl, verzoegerung);
			}
		});

		// die oberen Buttons für die Entschlosschleife ----------------
		JPanel buttonsOben = new JPanel();
		JButton endlosStart = new JButton("Starte");
		JButton endlosEnde = new JButton("Stopp");
		buttonsOben.add(endlosStart);
		buttonsOben.add(endlosEnde);

		endlosStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endlos = new Thread(new Runnable() { // hier: annonyme Klasse
					@Override
					public void run() {
						try {
							endlosStart.setEnabled(false); // schaltfläche deaktivieren
							while (true) { // Thread in einer Endlosschreife
								automat.berechneNeueGeneration();
								Thread.sleep(verzoegerung);
							}
						} catch (InterruptedException e) {
							// mache nix :-)
						}
					}
				});
				endlos.start(); // Threads müssen gestartet werden
			}
		});
		endlosEnde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endlosStart.setEnabled(true);
				endlos.interrupt(); // beendet den Thread
			}
		});

		// die Tabelle -------------------------------------------------
		tabelle = new JTable(automat);
		tabelle.setRowSelectionAllowed(false);

		// Zusammenbasteln des Frames ----------------------------------
		setLayout(new BorderLayout());
		add(buttonsOben, BorderLayout.NORTH);
		add(tabelle, BorderLayout.CENTER);
		add(buttonsUnten, BorderLayout.SOUTH);

		setVisible(true);
	}

	// -----------------------------------------------------------------
	// Zum Testen (ohne separate Starterklasse) ------------------------
	public static void main(String[] args) {
		new AutomatenView_FW();
	}
}
