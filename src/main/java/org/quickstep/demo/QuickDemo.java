package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;

public class QuickDemo extends JFrame
{
   JTextField userField = new JTextField();
   JTextField passwordField = new JTextField();
   JButton proceedButton = new JButton("OK");
   JButton cancelButton = new JButton("Cancel");

   @SuppressWarnings("all")
   public QuickDemo() throws HeadlessException
   {
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setVisible(true);
   }

   public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new QuickDemo();
   }
}
