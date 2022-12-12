import javax.lang.model.util.ElementScanner14;
import javax.swing.table.*;

public class TableTestModell extends AbstractTableModel {
    // private DatenLieferant daten = null; // wo die Daten herkommen

    /*
     * public TableTestModell() {
     * daten = new DatenLieferant(); // initialisiere Datenquelle
     * }
     */
    String[][] inhalt = { { "Beatles", "Help" }, { "Beatwatt", "Is That All" }, { "ABBA", "Waterloo" } };

    @Override
    public int getColumnCount() { // Anzahl der Spalten
        return 2;
    }

    @Override
    public String getColumnName(int arg0) { // Die Spaltenueberschriften
        if (arg0 == 0)
            return "Interpret";
        if (arg0 == 1)
            return "Titel";
        return null;
    }

    @Override
    public int getRowCount() { // Anzahl der Zeilen, also Datenobjekte
        return 0;
    }

    @Override
    public Object getValueAt(int zeile, int spalte) { // Die eigentlichen Daten
        /*
         * if (zeile > 0 && spalte > 0) {
         * return zeile * spalte;
         * } else { // Fehlerbehandlung
         * return "";
         * }
         */

        return inhalt[zeile][spalte];
    }
}