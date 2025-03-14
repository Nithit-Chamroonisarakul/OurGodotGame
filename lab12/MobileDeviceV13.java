package piyayotai.suppanat.lab12;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import piyayotai.suppanat.lab12.MobileDeviceV12;
import piyayotai.suppanat.lab7.SmartPhone;
import piyayotai.suppanat.lab7.Tablet;
import piyayotai.suppanat.lab7.MobileDevice;

public class MobileDeviceV13 extends MobileDeviceV12 implements ActionListener {
    protected JButton addButton, displayButton;
    protected ArrayList<MobileDevice> deviceList;

    public MobileDeviceV13(String title) {
        super(title);
        deviceList = new ArrayList<>();
    }

    @Override
    protected void addComponents() {
        super.addComponents();

        addButton = new JButton("Add");
        displayButton = new JButton("Display");

        addButton.addActionListener(this);
        displayButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
    }

    protected void handleAddButton() {
        String name = deviceNameField.getText().trim();
        String brand = brandField.getText().trim();
        String priceText = priceField.getText().trim();
        double price;

        if (name.isEmpty() || brand.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Price.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MobileDevice device;
        if (tabletButton.isSelected()) {
            device = new Tablet(name, brand, price);
            JOptionPane.showMessageDialog(this, "Tablet " + name + " is added", "Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            device = new SmartPhone(name, brand, price);
            JOptionPane.showMessageDialog(this, "SmartPhone " + name + " is added", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        deviceList.add(device);
    }

    protected void handleDisplayButton() {
        if (deviceList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No devices added.", "Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder message = new StringBuilder();
        for (MobileDevice device : deviceList) {
            message.append(device.getType()).append(": ")
                    .append(device.getName()).append(" (")
                    .append(device.getBrand()).append(") ")
                    .append(device.getPrice()).append(" Baht.\n");
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Device List", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == addButton) {
            handleAddButton();
        } else if (src == displayButton) {
            handleDisplayButton();
        } else {
            super.actionPerformed(e);
        }
    }

    public static void createAndShowGUI() {
        MobileDeviceV13 mdv13 = new MobileDeviceV13("Mobile Device V13");
        mdv13.addComponents();
        mdv13.setFrameFeatures();
        mdv13.addMenus();
        mdv13.addListeners();
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
