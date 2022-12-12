import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class SimpleTable extends JFrame {
    SimpleTable() { // Konstruktor
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[][] inhalt = { { "Beatles", "Help" }, { "Beatwatt", "Is That All" }, { "ABBA", "Waterloo" } };
        String[] titel = { "Interpret", "Titel" };
        JTable table = new JTable(inhalt, titel);
        add(new JScrollPane(table)); // ohne JScrollPane keine Titel!
        setSize(400, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleTable();
    }
}