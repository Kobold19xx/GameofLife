import javax.swing.table.AbstractTableModel;

//Game of Life Conway
//LP 07.12.22

public class GameOfLife extends AbstractTableModel {

    // eigenschaften

    int höhe = 10;
    int breite = 10;

    // 1. Dimension = Zeile 2. Dimension = Spalte
    // beachte Spielfeldrand (1 zeile davor, 1 Zeile danach, 1 spalte davor, 1
    // spalte danach)
    private boolean[][] feld = new boolean[höhe + 2][breite + 2];
    private boolean[][] feldNeu = new boolean[höhe + 2][breite + 2];

    private String leben = "x";
    private String tot = " ";

    // methoden
    // zeile -1, spalte -1 = kästchen links oben von ausgangspunkt
    int anzahlNachbarn(int zeile, int spalte) {
        return wertZelle(zeile - 1, spalte - 1) + wertZelle(zeile - 1, spalte) + wertZelle(zeile - 1, spalte + 1)
                + wertZelle(zeile, spalte - 1) + wertZelle(zeile, spalte + 1) + wertZelle(zeile + 1, spalte - 1)
                + wertZelle(zeile + 1, spalte) + wertZelle(zeile + 1, spalte + 1);
    }

    int wertZelle(int zeile, int spalte) {
        if (feld[zeile][spalte]) {
            return 1;
        } else {
            return 0;
        }
    }

    // Anwendung der Spielregeln berechnen
    boolean neueZelle(int zeile, int spalte) {
        // 3 möglichkeiten: sterben, neues Leben, bleibt so!
        int nachbarn = anzahlNachbarn(zeile, spalte);

        if (nachbarn < 2 || nachbarn > 3) {
            return false;
        } else if (nachbarn == 3) {
            return true;
        } else {
            return feld[zeile][spalte];
        }
    }

    // mehrer Generationen
    void berechneMehrereGenerationen(int anzahl, int verzögerung) {
        for (int i = 0; i < anzahl; i++) {
            berechneNeueGeneration();
            try {
                Thread.sleep(verzögerung);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // zur Erinnerung: Überladen ... Vergleiche optionale Parameter
    void berechneMehrereGenerationen(int anzahl) {
        berechneMehrereGenerationen(anzahl, 0);
    }

    // beachte: Spielfeld geht von Zeile 1 bis höhe, Spalte 1 bis breite
    void berechneNeueGeneration() {
        for (int zeile = 1; zeile <= höhe; zeile++) {
            for (int spalte = 1; spalte <= breite; spalte++) {
                feldNeu[zeile][spalte] = neueZelle(zeile, spalte);

            }
        }
        aktualisiereGeneration();
    }

    // Schreibe das 2. Array auf das 1. Array
    void aktualisiereGeneration() {
        // vorsicht ... so nicht ... so wird nur die Referenz kopiert und nicht der
        // inhalt
        // feld = feldNeu;
        for (int zeile = 1; zeile <= höhe; zeile++) {
            for (int spalte = 1; spalte <= breite; spalte++) {
                feld[zeile][spalte] = feldNeu[zeile][spalte];
            }
        }
        fireTableDataChanged();
    }

    void initialisiere() { // Test 1
        // beispiel Pendel Uhr
        feld[1][3] = true;
        feld[2][1] = true;
        feld[3][3] = true;
        feld[2][2] = true;
        feld[3][4] = true;
        feld[4][2] = true;
    }

    /*
     * void initialisiere() { // Test 2
     * // beispiel Pendel Uhr
     * feld[1][2] = true;
     * feld[2][3] = true;
     * feld[3][3] = true;
     * feld[3][2] = true;
     * feld[3][1] = true;
     * 
     * }
     */
    void ausgabe() {
        System.out.println("-----------------");
        for (int zeile = 1; zeile <= höhe; zeile++) {
            System.out.println();
            for (int spalte = 1; spalte <= breite; spalte++) {
                System.out.print(wertZelle(zeile, spalte));
            }

        }
        System.out.println("");
        System.out.println("");
        System.out.println("-----------------");
    }

    // konstruktoren
    public GameOfLife() {
        initialisiere();
    }

    @Override
    public int getRowCount() {

        return höhe; // ähm ... mit/ohne rand
    }

    @Override
    public int getColumnCount() {

        return breite; // ähm ... mit/ohne rand
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true; // damit sind alle Zellen editierbar
    }

    @Override
    // row, col ... wo hat sich was geändert
    // obj ... der "neue" wert der eingegeben wurde
    public void setValueAt(Object obj, int row, int col) {
        if (obj.equals(leben)) {
            feld[row][col] = true;
        } else {
            feld[row][col] = false;
        }
        // aktualisiere auch in der View
        fireTableCellUpdated(row, col);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (feld[rowIndex][columnIndex]) {
            return leben;
        } else {
            return tot;
        }
    }
}