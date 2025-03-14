package piyayotai.suppanat.lab12;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import piyayotai.suppanat.lab12.MobileDeviceV14;
import piyayotai.suppanat.lab7.MobileDevice;
import piyayotai.suppanat.lab7.SmartPhone;
import piyayotai.suppanat.lab7.Tablet;

public class MobileDeviceV15 extends MobileDeviceV14 implements ActionListener {
    private JRadioButtonMenuItem textFormatMI, binaryFormatMI;
    private boolean isBinaryFormat = false;

    public MobileDeviceV15(String title) {
        super(title);
    }

    @Override
    protected void addMenus() {
        super.addMenus();
        JMenu configMenu = menuBar.getMenu(1);
        JMenu formatMenu = new JMenu("Format");
        
        textFormatMI = new JRadioButtonMenuItem("Text", true);
        binaryFormatMI = new JRadioButtonMenuItem("Binary");
        
        ButtonGroup formatGroup = new ButtonGroup();
        formatGroup.add(textFormatMI);
        formatGroup.add(binaryFormatMI);
        
        textFormatMI.addActionListener(this);
        binaryFormatMI.addActionListener(this);
        
        formatMenu.add(textFormatMI);
        formatMenu.add(binaryFormatMI);
        
        configMenu.add(formatMenu);
    }

    @Override
    protected void handleMenuSave() {
        super.handleMenuSave();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                if (isBinaryFormat) {
                    saveBinaryFile(file);
                } else {
                    saveTextFile(file);
                }
                JOptionPane.showMessageDialog(this, "Devices saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void saveTextFile(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (MobileDevice device : deviceList) {
                writer.println(device.getType() + ": " + device.getName() + " (" + device.getBrand() + ") " + device.getPrice() + " Baht.");
            }
        }
    }

    protected void saveBinaryFile(File file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(deviceList);
        }
    }

    @Override
    protected void handleMenuOpen() {
        super.handleMenuOpen();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Opening " + file.getAbsolutePath(), "Info", JOptionPane.INFORMATION_MESSAGE);
            
            deviceList.clear();
            try {
                if (isBinaryFormat) {
                    loadBinaryFile(file);
                } else {
                    loadTextFile(file);
                }
                handleDisplayButton();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadTextFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length < 2) continue;
                
                String type = parts[0].trim();
                String details = parts[1].trim();
                
                int brandStartIndex = details.indexOf("(");
                int brandEndIndex = details.indexOf(")");
                
                if (brandStartIndex == -1 || brandEndIndex == -1) continue;
                
                String name = details.substring(0, brandStartIndex).trim();
                String brand = details.substring(brandStartIndex + 1, brandEndIndex).trim();
                double price = Double.parseDouble(details.substring(brandEndIndex + 2, details.indexOf(" Baht.")).trim());
                
                MobileDevice device;
                if (type.equals("Tablet")) {
                    device = new Tablet(name, brand, price);
                } else {
                    device = new SmartPhone(name, brand, price);
                }
                deviceList.add(device);
            }
        }
    }

    private void loadBinaryFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            deviceList = (ArrayList<MobileDevice>) in.readObject();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object srcObj = event.getSource();
        if (srcObj == menu2) {
            handleMenuOpen();
        } else if (srcObj == menu3) {
            handleMenuSave();
        } else if (srcObj == textFormatMI) {
            isBinaryFormat = false;
        } else if (srcObj == binaryFormatMI) {
            isBinaryFormat = true;
        } else {
            super.actionPerformed(event);
        }
    }

    public static void createAndShowGUI() {
        MobileDeviceV15 mdv15 = new MobileDeviceV15("Mobile Device V15");
        mdv15.addComponents();
        mdv15.setFrameFeatures();
        mdv15.addMenus();
        mdv15.addListeners();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}