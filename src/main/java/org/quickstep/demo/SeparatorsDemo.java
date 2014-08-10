package org.quickstep.demo;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorsDemo extends JFrame
{
   public SeparatorsDemo()
   {
      buildContent(this, panel().
         withBorder().
         specifyDefault(specWithFill().withGridWidthRemaining()).
         add(new JButton("Button")).
         addLineSeparator().
         add(new JButton("Button")).
         addBlank().
         add(new JButton("Button")).
         add(panel().withSpec(specWithFill()).specifyDefault(specWithFill()).
            add(new JButton("Button")).addSeparator().add(new JButton("Button"))
         )
      );
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
      new SeparatorsDemo();
   }
}