
public class Multiplikator extends Automat {

    // Eigenschaften

    private int offset;

    // Methoden
    @Override
    public int getRowCount() {

        return 10;
    }

    @Override
    public int getColumnCount() {

        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return rowIndex * columnIndex * offset;
    }

    @Override
    void initialisiere() {
        offset = 1;
        fireTableDataChanged();
    }

    @Override
    void berechneNeueGeneration() {
        offset++;
        fireTableDataChanged();
    }

    // Konstruktor

    Multiplikator() {
        initialisiere();
    }

}