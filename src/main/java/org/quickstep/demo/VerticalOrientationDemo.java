package org.quickstep.demo;

import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class VerticalOrientationDemo extends JFrame
{
   public VerticalOrientationDemo()
   {
      buildContent(
         this, panel()
            .withOrientation(Orientation.VERTICAL)
            .withLineLength(10)
            .withScroll()
            .withDefault(specWithFill())
            .addAll(generateOptions())
      );

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   private List<AbstractButton> generateOptions()
   {
      List<AbstractButton> buttons = new LinkedList<AbstractButton>();
      for (int i = 1; i <= 60; i++)
      {
         buttons.add(new JCheckBox("Option " + i));
      }
      return buttons;
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      new VerticalOrientationDemo();
   }
}
