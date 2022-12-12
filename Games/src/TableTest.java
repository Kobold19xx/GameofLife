import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.*;

public class TableTest extends JFrame {
    TableTest() { // Konstruktor
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = new JTable(new GameOfLife());
        add(table); // ohne JScrollPane keine Titel!
        setSize(400, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TableTest();
        // Table erzeugen
    }
}