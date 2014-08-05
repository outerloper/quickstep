package org.quickstep.demo;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorsDemo extends JFrame
{
   public SeparatorsDemo()
   {
      buildContent(this, panel().
         withLineLength(1).
         withBorder().
         specifyDefault(specWithFillX()).
//         specifyCellDefaults(growX().withGridWidthRemaining()). // TODO this hangs
   add(new JButton("Button")).
         addHorizontalSeparator().
         add(new JButton("Button")).
         addBlank().
         add(new JButton("Button")).
         add(panel().withSpec(specWithFillY()).specifyDefault(specWithFillX()).
            add(new JButton("Button")).addVerticalSeparator().add(new JButton("Button"))
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