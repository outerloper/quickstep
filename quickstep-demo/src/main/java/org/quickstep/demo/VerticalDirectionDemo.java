package org.quickstep.demo;

import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class VerticalDirectionDemo
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withDirection(Direction.TOP_TO_BOTTOM)
            .withLineLength(10)
            .withContentAnchor(A.BOTH)
            .add(createCheckBoxes())
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   private static List<AbstractButton> createCheckBoxes()
   {
      List<AbstractButton> buttons = new LinkedList<AbstractButton>();
      for (int i = 1; i <= 60; i++)
      {
         buttons.add(new JCheckBox("Option " + i));
      }
      return buttons;
   }
}
