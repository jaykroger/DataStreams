import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.io.*;
import java.util.Scanner;

public class StringFilterGUI extends JFrame {
    JPanel mainPanel;

    // Header panel assets
    JPanel headerPanel;
    JLabel header;
    Font headerFont = new Font("Comfortaa", Font.BOLD, 49);
    Color headerLabelColor = new Color(247, 246, 245, 255);
    Color interfaceButtonColor = new Color(186, 185, 180);
    Color headerBackgroundColor = new Color(240, 130, 90, 255);


    // Main interface assets, which contains field to select file, as well as textArea to show extracted tags
    JPanel centerPanel;
    JPanel interfacePanel;
    JLabel fileLabel;
    JTextField fileTextField;
    JButton selectFileButton;
    JButton scanButton;
    JFileChooser fileChooser;


    // Display Panel
    JPanel displayPanel;
    JLabel originalFileLabel;
    JTextArea originalFileTextArea;
    JScrollPane originalFileScrollPane;
    JLabel filterStringLabel;
    JTextField filterStringField;
    JTextArea filterTextArea;
    JScrollPane filterScrollPane;
    Font interfaceLabelFont = new Font("Arial", Font.PLAIN, 12);
    Color interfaceLabelColor = new Color(44, 43, 41);
    Color interfaceBackgroundColor = new Color(255, 252, 246, 255);


    // Button Panel
    JPanel buttonPanel;
    JButton exportTagsButton;
    JButton clearButton;
    JButton quitButton;


    // Program logic variables
    File selectedFile;
    Path file;
    File workingDirectory = new File(System.getProperty("user.dir"));


    public StringFilterGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setBackground(headerBackgroundColor);
        headerPanel.setLayout(new GridBagLayout());
        Dimension headerSize = new Dimension(1080, 100);
        headerPanel.setPreferredSize(headerSize);

        header = new JLabel("String Filter");
        header.setFont(headerFont);
        header.setForeground(headerLabelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(header, gbc);
    }

    private void createCenterPanel()
    {
        centerPanel = new JPanel();
        centerPanel.setBackground(interfaceBackgroundColor);
        centerPanel.setLayout(new BorderLayout());

        createInterfacePanel();
        centerPanel.add(interfacePanel, BorderLayout.PAGE_START);

        createDisplayPanel();
        centerPanel.add(displayPanel, BorderLayout.PAGE_END);
    }

    private void createInterfacePanel() {
        interfacePanel = new JPanel();
        interfacePanel.setBackground(interfaceBackgroundColor);
        interfacePanel.setLayout(new GridBagLayout());

        fileLabel = new JLabel("File:");
        fileLabel.setFont(interfaceLabelFont);
        fileLabel.setForeground(interfaceLabelColor);

        fileTextField = new JTextField();
        fileTextField.setColumns(50);

        selectFileButton = new JButton("Select File");
        selectFileButton.setFont(interfaceLabelFont);
        selectFileButton.setForeground(interfaceLabelColor);
        selectFileButton.setBackground(interfaceButtonColor);
        selectFileButton.addActionListener((ActionEvent ae) -> getSelectedFile());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        interfacePanel.add(fileLabel, gbc);
        gbc.gridx = 1;
        interfacePanel.add(fileTextField, gbc);
        gbc.gridx = 2;
        interfacePanel.add(selectFileButton, gbc);
    }

    private void createDisplayPanel()
    {
        displayPanel = new JPanel();
        displayPanel.setBackground(interfaceBackgroundColor);
        displayPanel.setLayout(new GridBagLayout());

        originalFileTextArea = new JTextArea();
        originalFileTextArea.setRows(10);
        originalFileTextArea.setColumns(30);
        originalFileTextArea.setLineWrap(true);
        originalFileTextArea.setWrapStyleWord(true);
        originalFileScrollPane = new JScrollPane(originalFileTextArea);

        filterStringLabel = new JLabel("Filter String:");
        filterStringLabel.setFont(interfaceLabelFont);
        filterStringLabel.setForeground(interfaceLabelColor);

        filterStringField = new JTextField();
        filterStringField.setColumns(50);

        scanButton = new JButton("Scan File");
        scanButton.setFont(interfaceLabelFont);
        scanButton.setForeground(interfaceLabelColor);
        scanButton.setBackground(interfaceButtonColor);
        scanButton.addActionListener((ActionEvent ae) -> scanFile());

        filterTextArea = new JTextArea();
        filterTextArea.setRows(10);
        filterTextArea.setColumns(30);
        filterTextArea.setLineWrap(true);
        filterTextArea.setWrapStyleWord(true);
        filterScrollPane = new JScrollPane(filterTextArea);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(75, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        interfacePanel.add(filterStringLabel, gbc);
        gbc.gridx = 1;
        interfacePanel.add(filterStringField, gbc);
        gbc.gridx = 2;
        interfacePanel.add(scanButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        interfacePanel.add(originalFileScrollPane, gbc);
        gbc.gridx = 2;
        interfacePanel.add(filterScrollPane, gbc);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(interfaceBackgroundColor);
        buttonPanel.setLayout(new GridBagLayout());

        clearButton = new JButton("Clear");
        clearButton.setFont(interfaceLabelFont);
        clearButton.setForeground(interfaceLabelColor);
        clearButton.setBackground(interfaceButtonColor);
        clearButton.addActionListener((ActionEvent ae) ->
        {
            fileTextField.selectAll();
            fileTextField.setText("");

            originalFileTextArea.selectAll();
            originalFileTextArea.setText("");

            filterStringField.selectAll();
            filterStringField.setText("");

            filterTextArea.selectAll();
            filterTextArea.setText("");
        });

        quitButton = new JButton("Quit");
        quitButton.setFont(interfaceLabelFont);
        quitButton.setForeground(interfaceLabelColor);
        quitButton.setBackground(interfaceButtonColor);
        quitButton.addActionListener((ActionEvent ae) -> getQuitConfirm());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 25, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(clearButton, gbc);
        gbc.gridx = 1;
        buttonPanel.add(quitButton, gbc);
    }

    private void getSelectedFile() {
        fileChooser = new JFileChooser();

        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            file = selectedFile.toPath();
            fileTextField.setText(String.valueOf(file));
        }
    }

    private void scanFile()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile()))))
        {
            if (fileTextField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please input a file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                originalFileTextArea.selectAll();
                originalFileTextArea.setText("");

                filterTextArea.selectAll();
                filterTextArea.setText("");

                String filterString = filterStringField.getText().trim().toLowerCase();
                String line;

                while ((line = reader.readLine()) != null)
                {
                    originalFileTextArea.append(line + "\n");

                    if (line.toLowerCase().contains(filterString)) {
                        filterTextArea.append(line + "\n");
                    }
                }
            }
        }
        catch (EOFException ignored)
        {
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error Reading File.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getQuitConfirm() {
        int quitConfirm;
        quitConfirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit", JOptionPane.YES_NO_OPTION);

        if (quitConfirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}