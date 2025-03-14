package piyayotai.suppanat.lab12;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import piyayotai.suppanat.lab7.MobileDevice;
import piyayotai.suppanat.lab7.SmartPhone;
import piyayotai.suppanat.lab7.Tablet;
import piyayotai.suppanat.lab12.MobileDeviceV13;

public class MobileDeviceV14 extends MobileDeviceV13 implements ActionListener {

    public MobileDeviceV14(String title) {
        super(title);
    }

    protected void handleMenuSave() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (MobileDevice device : deviceList) {
                    writer.println(device.getType() + ": " + device.getName() + " (" + device.getBrand() + ") " + device.getPrice() + " Baht.");
                }
                JOptionPane.showMessageDialog(this, "Devices saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void handleMenuOpen() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Opening " + file.getAbsolutePath(), "Info", JOptionPane.INFORMATION_MESSAGE);
            
            deviceList.clear();
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
                handleDisplayButton();
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object srcObj = event.getSource();
        if (srcObj == menu2) {
            handleMenuOpen();
        } else if (srcObj == menu3) {
            handleMenuSave();
        } else {
            super.actionPerformed(event);
        }
    }

    public static void createAndShowGUI() {
        MobileDeviceV14 mdv14 = new MobileDeviceV14("Mobile Device V14");
        mdv14.addComponents();
        mdv14.setFrameFeatures();
        mdv14.addMenus();
        mdv14.addListeners();
    }

    public static void main(String[] args) {
        // This runs on the main thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // This runs on the EDT
                createAndShowGUI();
            }
        });
    }
    
}
