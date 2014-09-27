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
            .withContentAnchor(A.BOTH)
            .add(new JButton("Button 0"))
            .addLineSeparator()
            .add(new JButton("Button 1")).addSeparator().add(new JButton("Button 2"))
            .addLineSeparator()
            .add(new JButton("Button 3")).addSeparator().add(new JButton("Button 4")).add(new JButton("Button 5"))
            .add(line().addBlank(spec().withPreferredHeight(40)))
            .add(vpanel().withSpec(spec().withGridWidthRemainder()).withContentAnchor(A.BOTH)
                    .add(new JCheckBox("CheckBox A")).addSeparator().add(new JCheckBox("CheckBox B")).add(new JCheckBox("CheckBox C"))
                    .addLineSeparator()
                    .add(new JCheckBox("CheckBox D")).addSeparator().add(new JCheckBox("CheckBox E"))
                    .addLineSeparator()
                    .add(new JCheckBox("CheckBox F"))
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