
// Realisierung Game Of Life
// Nebenbei: Thread.sleep()
// Nebenbei: Datenmodell für eine JTable
// mfg.fw, 07-12-2022

public class GameOfLife_FW extends Automat {

	// -----------------------------------------------------------------
	// Eigenschaften ---------------------------------------------------

	private int breite = 30;
	private int hoehe = 20;

	// 1. Dimension ... zeile, 2. Dimension ... spalte
	// beachte den Rand: 1 Zeile davor und danach,
	// 1 Spalte davor und danach
	private boolean[][] spielfeld;
	private boolean[][] spielfeldNeu;
	// für die ausgabe
	private String leben = "x";
	private String tod = "";

	// Methoden
	// Addiere die Werte der benachbarten Zellen
	int anzahlNachbarn(int zeile, int spalte) {
		return wertZelle(zeile - 1, spalte - 1) + wertZelle(zeile - 1, spalte) + wertZelle(zeile - 1, spalte + 1)
				+ wertZelle(zeile, spalte - 1) + wertZelle(zeile, spalte + 1)
				+ wertZelle(zeile + 1, spalte - 1) + wertZelle(zeile + 1, spalte) + wertZelle(zeile + 1, spalte + 1);
	}

	// Notwendig zum Addieren der Nachbarn, da Array aus booleans
	int wertZelle(int zeile, int spalte) {
		if (spielfeld[zeile][spalte])
			return 1;
		else
			return 0;
	}

	// Spielregeln berechnen
	boolean neueZelle(int zeile, int spalte) {
		int nachbarn = anzahlNachbarn(zeile, spalte);

		// 3 Möglichkeiten: Tod, neues Leben oder es bleibt wie es ist
		if (nachbarn < 2 || nachbarn > 3) {
			return false;
		} else if (nachbarn == 3) {
			return true;
		} else {
			return spielfeld[zeile][spalte];
		}
	}

	// beachte: Spielfeld geht von zeile = 1 bis zur Höhe
	// spalte = 1 bis zur Breite
	void berechneNeueGeneration() {
		for (int zeile = 1; zeile <= hoehe; zeile++) {
			for (int spalte = 1; spalte <= breite; spalte++) {
				spielfeldNeu[zeile][spalte] = neueZelle(zeile, spalte);
			}
		}
		aktualisiereGeneration();
	}

	// Schreibe das 2. Array zurück auf das 1. Array
	void aktualisiereGeneration() {
		for (int zeile = 1; zeile <= hoehe; zeile++) {
			for (int spalte = 1; spalte <= breite; spalte++) {
				spielfeld[zeile][spalte] = spielfeldNeu[zeile][spalte];
			}
		}
		fireTableDataChanged(); // Info an View, dass Tabelle geändert
	}

	void initialisiere() {
		// automatisch mit false belegt
		spielfeld = new boolean[hoehe + 2][breite + 2];
		spielfeldNeu = new boolean[hoehe + 2][breite + 2];

		// das Beispiel "Pendel-Uhr"
		spielfeld[1][3] = true;
		spielfeld[2][1] = true;
		spielfeld[2][2] = true;
		spielfeld[3][3] = true;
		spielfeld[3][4] = true;
		spielfeld[4][2] = true;
		fireTableDataChanged();
	}

	void ausgabe() { // nur zum Test: Ausgabe in der Konsole
		for (int zeile = 1; zeile <= hoehe; zeile++) {
			System.out.println();
			for (int spalte = 1; spalte <= breite; spalte++) {
				System.out.print(wertZelle(zeile, spalte));
			}
		}
		System.out.println("------------------");
	}

	// Konstruktor -----------------------------------------------------
	GameOfLife_FW() {
		this.initialisiere();
	}

	// -----------------------------------------------------------------
	// Methoden für das Zusammenspiel mit der View ---------------------
	@Override
	public int getRowCount() {
		return hoehe; // ähm ... mit/ohne rand?!
	}

	@Override
	public int getColumnCount() {
		return breite; // ähm ... mit/ohne rand?!
	}

	// row, col ... wo hat sich was geändert
	// obj ... der "neue" Wert, der eingegeben wurde
	@Override
	public void setValueAt(Object obj, int row, int col) {
		if (obj.equals(leben)) {
			spielfeld[row][col] = true;
		} else {
			spielfeld[row][col] = false;
		}
		fireTableCellUpdated(row, col); // // Info an View, dass Zelle geändert
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (spielfeld[rowIndex][columnIndex])
			return leben;
		else
			return tod;
	}
}
