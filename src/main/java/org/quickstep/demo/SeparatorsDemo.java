package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorsDemo extends JFrame
{
   public SeparatorsDemo()
   {
      buildContent(this, panel().
         withMaxLineLength(1).
         specifyCellDefaults(growX()).
//         specifyCellDefaults(growX().withGridWidthRemaining()). // TODO this hangs
   add(new JButton("Button")).
         addSeparatingLine().
         add(new JButton("Button")).
         addBlank().
         add(new JButton("Button")).
         add(panel().withSpec(growY()).specifyCellDefaults(growX()).
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