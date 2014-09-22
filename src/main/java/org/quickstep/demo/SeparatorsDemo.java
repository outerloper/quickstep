package org.quickstep.demo;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorsDemo extends JFrame
{
   public SeparatorsDemo()
   {
      buildContent(
         this, panel()
            .withBorder()
            .withDefault(specWithFill().withGridWidthRemainder())
            .add(new JButton("Button"))
            .addLineSeparator()
            .add(new JButton("Button"))
            .addBlank()
            .add(new JButton("Button"))
            .add(panel().with(specWithFill()).withDefault(specWithFill())
                    .add(new JButton("Button")).addSeparator().add(new JButton("Button"))
            )
      );

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      new SeparatorsDemo();
   }
}