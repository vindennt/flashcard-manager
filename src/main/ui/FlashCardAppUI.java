// Reference: AlarmSystem
// Git: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// Author: Paul Carter
// Contribution: Adapted to work for FlashCardApp methods

package ui;

import model.Deck;
import model.DuplicateDeckException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FlashCardAppUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    //private static final String FILE_DESCRIPTOR = "...file";
    //private static final String SCREEN_DESCRIPTOR = "...screen";
    private JComboBox<String> deckSelectorCombo;         // contains display names to access decks
    private HashMap<String, Deck> deckSelectorReference; // name reference to access actual decks
    private JDesktopPane desktop;
    private JInternalFrame selectionPanel;

    private ArrayList<Deck> deckList;

    private JsonWriter jsonWriter;    // deck saver
    private JsonReader jsonReader;    // deck loader
    private String jsonFileLocation;  // tracks target file location

    /**
     * Constructor sets up button panel, key pad and visual alarm status window.
     */
    public FlashCardAppUI() throws FileNotFoundException {
        init();
        //fca.addAlarmObserver(new AlarmSiren());

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        selectionPanel = new JInternalFrame("Home Menu", false, false, false, false);
        selectionPanel.setLayout(new BorderLayout());
        selectionPanel.setResizable(true);

        deckSelectorReference = new HashMap<>();
        createDeckSelectionCombo();
        deckSelectorCombo.setEditable(true);
        deckSelectorCombo.addActionListener(this);

        setContentPane(desktop);
        setTitle("Flash Card Application");
        setSize(WIDTH, HEIGHT);

        addSelectionPanel();
        addMenu();
        //addKeyPad();
        //addAlarmDisplayPanel();

        //Remote r = new Remote(fca);
        //addRemote(r);

        selectionPanel.pack();
        selectionPanel.setVisible(true);
        desktop.add(selectionPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes variables and default save name and location
    private void init() {
        deckList = new ArrayList<>();
        //input = new Scanner(System.in);
        //input.useDelimiter("\n");
        jsonWriter = new JsonWriter("");
        jsonReader = new JsonReader("");
        updateTargetFileLocation("Default"); // sets a default target file location
    }

    // EFFECTS: sets where the saver/loader should look for a file
    private void updateTargetFileLocation(String filename) {
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
        selectionPanel.setLayout(new GridLayout(2, 2));

        selectionPanel.add(new JButton(new NewDeckAction()));
        selectionPanel.add(new JButton(new ImportDeckAction()));
        selectionPanel.add(selectorLabel);
        selectionPanel.add(createDeckSelectionCombo());

        this.selectionPanel.add(selectionPanel, BorderLayout.WEST);
    }

    private JComboBox<String> createDeckSelectionCombo() {
        deckSelectorCombo = new JComboBox<String>();
        return deckSelectorCombo;
    }

//
//    /**
//     * Helper to set up visual alarm status window
//     */
//    private void addAlarmDisplayPanel() {
//        AlarmUI alarmUI = new AlarmUI();
//        fca.addAlarmObserver(alarmUI);
//        controlPanel.add(alarmUI, BorderLayout.NORTH);
//    }

    /**
     * Adds menu bar.
     */
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu sensorMenu = new JMenu("File");
        sensorMenu.setMnemonic('F');
        addMenuItem(sensorMenu, new ImportDeckAction(),
                KeyStroke.getKeyStroke("control S"));
        //addMenuItem(sensorMenu, new SaveDeckAction(),
        //        KeyStroke.getKeyStroke("control S"));
        menuBar.add(sensorMenu);

        setJMenuBar(menuBar);
    }

    private void deckSelectorAdd(Deck d) {
        String displayName = d.getName() + ":" + d.getCourse();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deckSelectorCombo) {
            System.out.println(deckSelectorCombo.getSelectedItem());
        }
    }


    public void addDeck(Deck d) throws DuplicateDeckException {
        for (Deck deck : deckList) {
            if (deck.getName().equals(d.getName())) {
                throw new DuplicateDeckException("Cannot add duplicate deck");
            }
        }
        deckList.add(d);
        System.out.println("Added deck " + d.getName() + " for course " + d.getCourse() + " to deck list.");
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
                        JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

    }


//    /**
//     * Adds user interface for remote to the system.
//     * @param r  the remote control
//     */
//    private void addRemote(Remote r) {
//        RemoteUI rUI = new RemoteUI(r, this);
//        desktop.add(rUI);
//    }
//
//    /**
//     * Helper to create print options combo box
//     * @return  the combo box
//     */
//    private JComboBox<String> createPrintCombo() {
//        printCombo = new JComboBox<String>();
//        printCombo.addItem(FILE_DESCRIPTOR);
//        printCombo.addItem(SCREEN_DESCRIPTOR);
//        return printCombo;
//    }
//
//    /**
//     * Helper to add keypad to main application window
//     */
//    private void addKeyPad() {
//        kp = new KeyPad();
//        addKeyListener(kp);
//        controlPanel.add(kp, BorderLayout.CENTER);
//    }
//

    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }
//
//    /**
//     * Represents action to be taken when user wants to add a new code
//     * to the system.
//     */
//    private class AddCodeAction extends AbstractAction {
//
//        AddCodeAction() {
//            super("Add Code");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            AlarmCode alarmCode = new AlarmCode(kp.getCode());
//            kp.clearCode();
//            try {
//                fca.addCode(alarmCode);
//            } catch (NotValidCodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//

    /**
     * Represents the action to be taken when the user wants to add a new
     * sensor to the system.
     */
    private class ImportDeckAction extends AbstractAction {

        ImportDeckAction() {
            super("Load Deck");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String course = null;
            String name = JOptionPane.showInputDialog(null,
                    "Enter the name of the deck to import", "Import Deck",
                    JOptionPane.QUESTION_MESSAGE);
            if (name != null) {
                course = JOptionPane.showInputDialog(null,
                        "Enter the course of the deck to import", "Import Deck",
                        JOptionPane.QUESTION_MESSAGE);
            }
            String filename = name + course;
            updateTargetFileLocation(filename);
            try {
                Deck deckToLoad = jsonReader.read();
                addDeck(deckToLoad);
                deckSelectorAdd(deckToLoad);
                System.out.println("Loaded " + deckToLoad.getName() + " from " + jsonFileLocation);
            } catch (DuplicateDeckException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                System.out.println("Unable to read from file at: " + jsonFileLocation);
            }
        }
    }


//    /**
//     * Represents the action to be taken when the user wants to remove
//     * a code from the system.
//     */
//    private class RemoveCodeAction extends AbstractAction {
//
//        RemoveCodeAction() {
//            super("Remove Code");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            AlarmCode alarmCode = new AlarmCode(kp.getCode());
//            kp.clearCode();
//            try {
//                fca.removeCode(alarmCode);
//            } catch (NotValidCodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (CodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (LastCodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    /**
//     * Represents the action to be taken when the user wants to arm
//     * the system.
//     */
//    private class ArmAction extends AbstractAction {
//
//        ArmAction() {
//            super("Arm System");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            AlarmCode alarmCode = new AlarmCode(kp.getCode());
//            kp.clearCode();
//            try {
//                fca.arm(alarmCode);
//            } catch (SystemNotReadyException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (CodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    /**
//     * Represents the action to be taken when the user wants to
//     * disarm the system.
//     */
//    private class DisarmAction extends AbstractAction {
//
//        DisarmAction() {
//            super("Disarm System");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            AlarmCode alarmCode = new AlarmCode(kp.getCode());
//            kp.clearCode();
//
//            try {
//                fca.disarm(alarmCode);
//            } catch (CodeException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    /**
//     * Represents the action to be taken when the user wants to
//     * print the event log.
//     */
//    private class PrintLogAction extends AbstractAction {
//        PrintLogAction() {
//            super("Print log to...");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            String selected = (String) printCombo.getSelectedItem();
//            LogPrinter lp;
//            try {
//                if (selected.equals(FILE_DESCRIPTOR))
//                    lp = new FilePrinter();
//                else {
//                    lp = new ScreenPrinter(AlarmControllerUI.this);
//                    desktop.add((ScreenPrinter) lp);
//                }
//
//                lp.printLog(EventLog.getInstance());
//            } catch (LogException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    /**
//     * Represents the action to be taken when the user wants to
//     * clear the event log.
//     */
//    private class ClearLogAction extends AbstractAction {
//        ClearLogAction() {
//            super("Clear log");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            EventLog.getInstance().clear();
//        }
//    }
//

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

