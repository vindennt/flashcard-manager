// References: AlarmSystem
// Git: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Author: Paul Carter
// Contribution: Adapted to work for FlashCardApp methods

package ui;

import model.Deck;
import exceptions.DuplicateDeckException;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// Run the flashcard application with interactive GUI
public class FlashCardAppUI extends JFrame implements ActionListener, MessageHandler {
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

    // MODIFIES: this
    // EFFECTS: handles flashcard application
    // throws FIleNotFoundException when a filename cannot be found in the data folder
    public FlashCardAppUI() throws FileNotFoundException {
        init();
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        selectionPanel = new JInternalFrame("Home Menu", false, false,
                false, false);
        selectionPanel.setLayout(new BorderLayout());
        selectionPanel.setResizable(true);

        deckSelectorCombo = new JComboBox<>();
        deckSelectorCombo.addActionListener(this);
        setContentPane(desktop);
        addSelectionPanel();
        addMenu();

        selectionPanel.pack();
        selectionPanel.setVisible(true);
        desktop.add(selectionPanel);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeHandler();
            }
        });
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: updates the selected deck when choosing it in the dropdown menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(deckSelectorCombo)) {
            System.out.println(deckSelectorCombo.getSelectedItem());
            String reference = deckSelectorCombo.getSelectedItem().toString();
            selectedDeck = deckSelectorReference.get(reference);
            addDeckPanel(selectedDeck);
        }
    }

    // MODIFIES: this
    // EFFECTS: warns user when they want to exist the program
    public void closeHandler() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Any unsaved work will be lost. Are you sure you want to exit?", "Warning",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            dispose();
        }
    }


    // MODIFIES: this
    // EFFECTS: adds UI panel for deck selection
    private void addDeckPanel(Deck d) {
        DeckUI deckUI = new DeckUI(d, this);
        desktop.add(deckUI);
    }

    // MODIFIES: this
    // EFFECTS: initializes variables and panel properties
    private void init() {
        setTitle("FlashCard Application");
        setSize(WIDTH, HEIGHT);
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

    // MODIFIES: this
    // EFFECTS: adds UI panels for interacting with the deck
    private void addSelectionPanel() {
        JPanel selectionPanel = new JPanel();
        JLabel selectorLabel = new JLabel("Selected Deck:");

        ImageIcon imageIcon =
                new ImageIcon(new ImageIcon("data/deckIcon.png").getImage().getScaledInstance(25,
                        25, Image.SCALE_DEFAULT));
        selectorLabel.setIcon(imageIcon);

        selectionPanel.setLayout(new GridLayout(3, 2));

        selectionPanel.add(selectorLabel);
        selectionPanel.add(deckSelectorCombo);
        selectionPanel.add(new JButton(new NewDeckAction()));
        selectionPanel.add(new JButton(new ImportDeckAction()));
        selectionPanel.add(new JButton(new PrintDeckAction()));
        selectionPanel.add(new JButton(new SaveDeckAction()));

        this.selectionPanel.add(selectionPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar at the top of the panel
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu selectionMenu = new JMenu("File");
        selectionMenu.setMnemonic('F');
        addSelectionMenuItem(selectionMenu, new ImportDeckAction(),
                KeyStroke.getKeyStroke("control I"));
        addSelectionMenuItem(selectionMenu, new SaveDeckAction(),
                KeyStroke.getKeyStroke("control S"));
        menuBar.add(selectionMenu);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds the deck to the deck selection menu
    private void deckSelectorAdd(Deck d) {
        String displayName = d.getName() + " : " + d.getCourse();
        deckSelectorReference.put(displayName, d);
        deckSelectorCombo.addItem(displayName);
    }

    // MODIFIES: this
    // EFFECTS: adds deck selection menu to the main panel
    private void addSelectionMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // MODIFIES: this
    // EFFECTS: adds given deck to the decklist
    public void addDeck(Deck d) throws DuplicateDeckException {
        for (Deck deck : deckList) {
            if (deck.getName().equals(d.getName())) {
                throw new DuplicateDeckException("Deck of that name already exists");
            }
        }
        deckList.add(d);
        System.out.println("Added deck " + d.getName() + " for course " + d.getCourse() + " to deck list.");
    }

    // MODIFIES: this
    // EFFECTS:  displays messsage box
    public void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: displays error message box
    public void showErrorDialog(Exception e, String title) {
        JOptionPane.showMessageDialog(null, e.getMessage(), title,
                JOptionPane.ERROR_MESSAGE);
    }


    // Represents an action to create a new deck
    private class NewDeckAction extends AbstractAction {

        NewDeckAction() {
            super("Create Deck");
        }

        // MODIFIES: this
        // EFFECTS: creates a new deck with inputted name and course
        @Override
        public void actionPerformed(ActionEvent evt) {
            String name;
            String course;

            name = JOptionPane.showInputDialog(null, "Deck name: ", "Deck creation",
                    JOptionPane.QUESTION_MESSAGE);

            if (name != null) {
                course = JOptionPane.showInputDialog(null, "Deck course: ", "Deck creation",
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


    // MODIFIES: this
    // EFFECTS: centers items on the frame
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // Represents an action to load a deck from a json file
    private class ImportDeckAction extends AbstractAction {

        ImportDeckAction() {
            super("Import Deck");
        }

        // MODIFIES: this
        // EFFECTS: action for when a user wants to import/load an existing deck file
        @Override
        public void actionPerformed(ActionEvent evt) {
            String filename = JOptionPane.showInputDialog(null,
                    "Enter the filename of the .json deck to import.", "Import Deck",
                    JOptionPane.QUESTION_MESSAGE);
            if (filename != null) {
                updateTargetFileLocation(filename);
                try {
                    Deck deckToLoad = jsonReader.read();
                    addDeck(deckToLoad);
                    deckSelectorAdd(deckToLoad);
                    showMessageDialog("Imported deck from" + jsonFileLocation,
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


    // Represents an action to save a deck to a json file
    private class SaveDeckAction extends AbstractAction {

        SaveDeckAction() {
            super("Save Deck");
        }

        // MODIFIES: this
        // EFFECTS: action for when a user wants to save a deck from the program
        @Override
        public void actionPerformed(ActionEvent evt) {
            String fileName = selectedDeck.getName() + selectedDeck.getCourse();
            updateTargetFileLocation(fileName);
            try {
                if (isOccupiedFilepath(jsonFileLocation)) {
                    int result = JOptionPane.showConfirmDialog(null, "File named " + fileName
                                    + ".json already exists. Overwrite?", "Overwrite file",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        saveDeck(selectedDeck);
                        showMessageDialog("Saved " + fileName + ".json", "Save successful");
                    } else if (result == JOptionPane.NO_OPTION) {
                        // do nothing
                    }
                } else {
                    saveDeck(selectedDeck);
                    showMessageDialog("Saved " + fileName + ".json", "Save successful");
                }
            } catch (FileNotFoundException e) {
                showErrorDialog(e, "Save failed");
            }
        }

        // EFFECTS: saves selected deck to data directory
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


    // Represents an action to print out all decks within the decklist
    private class PrintDeckAction extends AbstractAction {
        PrintDeckAction() {
            super("Print all decks");
        }

        // EFFECTS: action for when user wants to print out all their decks
        @Override
        public void actionPerformed(ActionEvent evt) {
            DeckPrinter dp;
            dp = new DeckPrinter(FlashCardAppUI.this);
            desktop.add((DeckPrinter) dp);
            dp.setLocation(50, getHeight() / 2);
            dp.printDeck(deckList);
        }
    }


    // Represents an action that requests the OS to focus on the window when clicked
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        // EFFECTS: requests focus when the window is clicked
        public void mouseClicked(MouseEvent e) {
            FlashCardAppUI.this.requestFocusInWindow();
        }

    }
}

//    // EFFECTS: runs the application
//    // throws FIleNotFoundException when trying to load a file that does not exist
//    public static void main(String[] args) {
//        try {
//            new FlashCardAppUI();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run: file not found");
//        }
//    }
//}

