// Reference: AlarmSystem
// Git: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Author: Paul Carter
// Contribution: Adapted to work for FlashCardApp methods

package ui;

import model.Deck;
import exceptions.DuplicateDeckException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FlashCardAppUI extends JFrame implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private JComboBox<String> deckSelectorCombo;         // contains display names to access decks
    private HashMap<String, Deck> deckSelectorReference; // name reference to access actual decks

    private JDesktopPane desktop;
    private JInternalFrame selectionPanel;


    private ArrayList<Deck> deckList;

    private JsonWriter jsonWriter;    // deck saver
    private JsonReader jsonReader;    // deck loader
    private String jsonFileLocation;  // tracks target file location
    private Deck selectedDeck;

    /**
     * Constructor sets up button panel, key pad and visual alarm status window.
     */
    public FlashCardAppUI() throws FileNotFoundException {
        init();
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        selectionPanel = new JInternalFrame("Home Menu", false, false, false, false);
        selectionPanel.setLayout(new BorderLayout());
        selectionPanel.setResizable(true);

        //createDeckSelectionCombo();
        deckSelectorCombo = new JComboBox<>();
        deckSelectorCombo.setEditable(true);
        deckSelectorCombo.addActionListener(this);

        setContentPane(desktop);
        setTitle("FlashCard Application");
        setSize(WIDTH, HEIGHT);

        addSelectionPanel();
        addMenu();

        selectionPanel.pack();
        selectionPanel.setVisible(true);
        desktop.add(selectionPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(deckSelectorCombo)) {
            System.out.println(deckSelectorCombo.getSelectedItem());
            String reference = deckSelectorCombo.getSelectedItem().toString();
            selectedDeck = deckSelectorReference.get(reference);
            addDeckPanel(selectedDeck);
        }
    }


    /**
     * Adds user interface for remote to the system.
     */
    private void addDeckPanel(Deck d) {
        DeckUI deckUI = new DeckUI(d, this);
        desktop.add(deckUI);
    }

    // MODIFIES: this
    // EFFECTS: initializes variables and default save name and location
    private void init() {
        deckList = new ArrayList<>();
        jsonWriter = new JsonWriter("");
        jsonReader = new JsonReader("");
        updateTargetFileLocation("Default"); // sets a default target file location
        deckSelectorReference = new HashMap<>();
    }


    // EFFECTS: sets where the saver/loader should look for a file
    public void updateTargetFileLocation(String filename) {
        jsonFileLocation = "./data/" + filename + ".json";
        jsonWriter.setDestination(jsonFileLocation);
        jsonReader.setSource(jsonFileLocation);
    }

    /**
     * Helper to add control buttons.
     */
    private void addSelectionPanel() {
        JPanel selectionPanel = new JPanel();
        JLabel selectorLabel = new JLabel("Selected Deck:");

        ImageIcon imageIcon =
                new ImageIcon(new ImageIcon("data/deckIcon.png").getImage().getScaledInstance(25,
                        25, Image.SCALE_DEFAULT));
        selectorLabel.setIcon(imageIcon);

        selectionPanel.setLayout(new GridLayout(4, 2));

        selectionPanel.add(selectorLabel);
        selectionPanel.add(deckSelectorCombo);
        selectionPanel.add(new JButton(new NewDeckAction()));
        selectionPanel.add(new JButton(new ImportDeckAction()));
        selectionPanel.add(new JButton(new PrintDeckAction()));
        selectionPanel.add(new JButton(new SaveDeckAction()));
        selectionPanel.add(new JButton(new PrintFilteredDeckAction()));

        this.selectionPanel.add(selectionPanel, BorderLayout.WEST);
    }

    /**
     * Adds menu bar.
     */
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu sensorMenu = new JMenu("File");
        sensorMenu.setMnemonic('F');
        addMenuItem(sensorMenu, new ImportDeckAction(),
                KeyStroke.getKeyStroke("control I"));
        addMenuItem(sensorMenu, new SaveDeckAction(),
                KeyStroke.getKeyStroke("control S"));
        menuBar.add(sensorMenu);

        setJMenuBar(menuBar);
    }

    private void deckSelectorAdd(Deck d) {
        String displayName = d.getName() + " : " + d.getCourse();
        deckSelectorReference.put(displayName, d);
        deckSelectorCombo.addItem(displayName);
    }

    /**
     * Adds an item with given handler to the given menu
     *
     * @param theMenu     menu to which new item is added
     * @param action      handler for new menu item
     * @param accelerator keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }


    public void addDeck(Deck d) throws DuplicateDeckException {
        for (Deck deck : deckList) {
            if (deck.getName().equals(d.getName())) {
                throw new DuplicateDeckException("Deck of that name already exists");
            }
        }
        deckList.add(d);
        System.out.println("Added deck " + d.getName() + " for course " + d.getCourse() + " to deck list.");
    }


    private void displayMessageBox(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class NewDeckAction extends AbstractAction {

        NewDeckAction() {
            super("Create Deck");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name;
            String course;

            name = JOptionPane.showInputDialog(null,
                    "Deck name: ",
                    "Deck creation",
                    JOptionPane.QUESTION_MESSAGE);

            if (name != null) {
                course = JOptionPane.showInputDialog(null,
                        "Deck course: ",
                        "Deck creation",
                        JOptionPane.QUESTION_MESSAGE);

                if (course != null) {
                    Deck deckToAdd = new Deck(name, course);
                    try {
                        addDeck(deckToAdd);
                        deckSelectorAdd(deckToAdd);
                    } catch (DuplicateDeckException e) {
                        showErrorDialog(e, "Creation failed");
                    }
                }
            }
        }

    }


    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Represents the action to be taken when the user wants to add a new
     * sensor to the system.
     */
    private class ImportDeckAction extends AbstractAction {

        ImportDeckAction() {
            super("Import Deck");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String filename = JOptionPane.showInputDialog(null,
                    "Enter the filename of the deck to import", "Import Deck",
                    JOptionPane.QUESTION_MESSAGE);
            if (filename != null) {
                updateTargetFileLocation(filename);
                try {
                    Deck deckToLoad = jsonReader.read();
                    addDeck(deckToLoad);
                    deckSelectorAdd(deckToLoad);
                    displayMessageBox("Imported deck from" + jsonFileLocation,
                            "Import successful");
                } catch (DuplicateDeckException e) {
                    showErrorDialog(e, "Import failed");
                } catch (IOException e) {
                    System.out.println("Unable to read from file at: " + jsonFileLocation);
                    showErrorDialog(e, "File does not exist");
                }
            }
        }
    }

    private static void showErrorDialog(Exception e, String title) {
        JOptionPane.showMessageDialog(null, e.getMessage(), title,
                JOptionPane.ERROR_MESSAGE);
    }


    private class SaveDeckAction extends AbstractAction {

        SaveDeckAction() {
            super("Save Deck");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String fileName = selectedDeck.getName() + selectedDeck.getCourse();
            updateTargetFileLocation(fileName);
            try {
                if (isOccupiedFilepath(jsonFileLocation)) {
                    int result = JOptionPane.showConfirmDialog(null, "File named " + fileName
                                    + ".json already exists. Overwrite?",
                            "Overwrite file",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        saveDeck(selectedDeck);
                        displayMessageBox("Saved " + fileName + ".json", "Save successful");
                    } else if (result == JOptionPane.NO_OPTION) {
                        // do nothing
                    }
                } else {
                    saveDeck(selectedDeck);
                    displayMessageBox("Saved " + fileName + ".json", "Save successful");
                }
            } catch (FileNotFoundException e) {
                showErrorDialog(e, "Save failed");
            }
        }

        // EFFECTS: saves deck to a file
        private void saveDeck(Deck deck) throws FileNotFoundException {
            jsonWriter.open();
            jsonWriter.write(deck);
            jsonWriter.close();
            System.out.println("Saved deck " + deck.getName() + deck.getCourse() + " to " + jsonFileLocation);
        }

        // REQUIRES: filePath is not empty
        // EFFECTS: returns true if file with name filePath exists, else false.
        private boolean isOccupiedFilepath(String filePath) {
            File file = new File(filePath);
            return (file.exists() && !file.isDirectory());
        }
    }


    /**
     * Represents the action to be taken when the user wants to
     * print the event log.
     */
    private class PrintDeckAction extends AbstractAction {
        PrintDeckAction() {
            super("Print all decks");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            DeckPrinter dp;
            dp = new DeckPrinter(FlashCardAppUI.this);
            desktop.add((DeckPrinter) dp);
            dp.setLocation(50, getHeight()  / 2);
            dp.printDeck(deckList);
        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * print the event log.
     */
    private class PrintFilteredDeckAction extends AbstractAction {
        PrintFilteredDeckAction() {
            super("Print decks from course");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            DeckPrinter dp;
            dp = new DeckPrinter(FlashCardAppUI.this);
            desktop.add((DeckPrinter) dp);

            String courseName = JOptionPane.showInputDialog(null,
                    "Enter the course to filter by",
                    JOptionPane.QUESTION_MESSAGE);
            if (courseName != null) {
                ArrayList<Deck> filteredDeckList = new ArrayList<>();
                for (Deck d : deckList) {
                    if (d.getCourse().equals(courseName)) {
                        filteredDeckList.add(d);
                    }
                }
                dp.printDeck(deckList);
            }
        }
    }


    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            FlashCardAppUI.this.requestFocusInWindow();
        }

    }


    // EFFECTS: runs the application
    // throws FIleNotFoundException when trying to load a file that does not exist
    public static void main(String[] args) {
        try {
            new FlashCardAppUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run: file not found");
        }
    }
}

