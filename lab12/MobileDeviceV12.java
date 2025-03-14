package piyayotai.suppanat.lab12;

import com.sun.source.doctree.ReturnTree;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import piyayotai.suppanat.lab11.MobileDeviceV11;

public class MobileDeviceV12 extends MobileDeviceV11 implements ActionListener {
    public MobileDeviceV12(String title){
    super(title);
}

protected void addListeners(){
    super.addListeners();
    deviceNameField.addActionListener(this);
    brandField.addActionListener(this);
    priceField.addActionListener(this);

}

@Override
protected void addComponents(){
    super.addComponents();
    deviceNameField.setName("Device Name");
    brandField.setName("Brand");
    priceField.setName("Price");
}


protected void handleNormalTextField(JTextField tf, JComponent nextComponent){
    if (tf.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Please enter some data in " + tf.getName(), "Message", JOptionPane.INFORMATION_MESSAGE);
        tf.requestFocusInWindow();
        nextComponent.setEnabled(false);
    } else {
        JOptionPane.showMessageDialog(this, tf.getName() + " is Change to " + tf.getText(), "Message", JOptionPane.INFORMATION_MESSAGE);
        nextComponent.setEnabled(true);
    } 
}

protected void handlePosNumTextField(JTextField tf, JComponent nextComponent){
     if (tf.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Please enter some data in " + tf.getName(), "Message", JOptionPane.INFORMATION_MESSAGE);
        tf.requestFocusInWindow();
        nextComponent.setEnabled(false);
        return;
     }
    try {
        Double value = Double.parseDouble(tf.getText());
        if (value <= 0) {
            JOptionPane.showMessageDialog(this, tf.getName() + " Price must be a positive number ", "Error", JOptionPane.ERROR_MESSAGE);
            tf.requestFocusInWindow();
            nextComponent.setEnabled(false); 
        }
        else if (!tf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, tf.getName() + " is Change to " + value, "Message", JOptionPane.INFORMATION_MESSAGE);
            nextComponent.setEnabled(true);
    } 
}
     catch (NumberFormatException e){

        JOptionPane.showMessageDialog(this, " Please enter a valid number in Price ", "Message", JOptionPane.INFORMATION_MESSAGE);
        nextComponent.setEnabled(false); }
    }
     
@Override
public void actionPerformed(ActionEvent e) {
    super.actionPerformed(e);
    Object srcObject = e.getSource();
    if (srcObject == deviceNameField) {
        handleNormalTextField(deviceNameField, brandField);
    } else if (srcObject == brandField) {
        handleNormalTextField(brandField, priceField);
    } else if (srcObject == priceField) {
        handlePosNumTextField(priceField, osComboBox);
    }
}

    public static void createAndShowGUI() {
        MobileDeviceV12 mdv12 = new MobileDeviceV12("Mobile Device V12");
        mdv12.addComponents();
        mdv12.setFrameFeatures();
        mdv12.addMenus();
        mdv12.addListeners();
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