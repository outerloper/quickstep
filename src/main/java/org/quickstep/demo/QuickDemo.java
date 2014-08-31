package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class QuickDemo extends JFrame
{
   public QuickDemo() throws HeadlessException
   {
      JButton ff = new JButton("ff");

      buildContent(this, panel().
         add(ff, spec().withIPadX(30))
      );

      System.out.println(ff.getPreferredSize());

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new QuickDemo();
   }
}
