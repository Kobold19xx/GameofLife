import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class AutomatenView extends JFrame {
    // eigenschaften
    JTable tabelle;
    GameOfLife gol = new GameOfLife();

    // spezielle Methoden
    void speichern() {
        System.out.println("speichern");
    }

    void laden() {
        System.out.println("laden");
    }

    void neuerAutomat() {
        System.out.println("neu...");
    }

    // konstruktor -> Fenster basteln
    AutomatenView() {
        // das übliche für den Frame
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new menu bar
        JMenuBar jmb = new JMenuBar();

        // Create a new menu
        JMenu jmFile = new JMenu("Datei");

        // Create some menu items
        JMenuItem jmiOpen = new JMenuItem("Laden");
        JMenuItem jmiSave = new JMenuItem("Speichern");
        JMenuItem jmiNew = new JMenuItem("Neu");

        // Was soll beim anklicken passieren?
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
        jmiNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                neuerAutomat();
            }
        });

        // Add the menu items to the menu
        jmFile.add(jmiNew);
        jmFile.add(jmiOpen);
        // jmFile.addSeparator(); // Trennlinie
        jmFile.add(jmiSave);

        // Add the menu to the menu bar
        jmb.add(jmFile);

        // Add the menu bar to the frame
        setJMenuBar(jmb);

        // Create a table
        tabelle = new JTable(gol);
        tabelle.setRowSelectionAllowed(false);

        JPanel buttons = new JPanel();
        JButton next1 = new JButton("Nächste Generation");
        JButton next2 = new JButton("Mehrere Generationen");
        buttons.add(next1);
        buttons.add(next2);

        next1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("nächste generation");
                gol.berechneNeueGeneration();
            }
        });

        next2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("mehrere generation");
                gol.berechneMehrereGenerationen(10, 42);
            }
        });

        setLayout(new BorderLayout());
        add(tabelle, BorderLayout.NORTH);
        add(buttons, BorderLayout.SOUTH);
        // Display the frame
        setVisible(true);
    }
}