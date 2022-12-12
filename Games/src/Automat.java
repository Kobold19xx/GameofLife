import javax.swing.table.*;

//Verallgemeinerung der Präsentation des GOL 
// LP 09.12.22

public abstract class Automat extends AbstractTableModel {
    // eigenschaften

    // methoden
    abstract void initialisiere();

    abstract void berechneNeueGeneration();

    // nun also auch mehrere Generationen
    void berechneMehrereGenerationen(int anzahl, int verzoegerung) {
        Thread mehrfach = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < anzahl; i++) {
                    berechneNeueGeneration();
                    try {
                        Thread.sleep(verzoegerung);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        mehrfach.start();
    }

    // Zur Erinnerung: Überladen ... vgl. optionale Parameter
    void berechneMehrereGenerationen(int anzahl) {
        berechneMehrereGenerationen(anzahl, 0);
    }

    // damit sind alle Zellen veränderbar
    @Override
    public boolean isCellEditable(int row, int col) {
        // damit sind ALLE Zellen veränderbar
        return true;
    }

}