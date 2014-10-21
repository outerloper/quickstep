package org.quickstep.demo;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SpannedCellsDemo extends JFrame
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withLineLength(4)
            .withContentAnchor(A.BOTH)
            .withCell(1, 2, spec().withGridSize(2, 3))
            .withCell(0, 5, spec().withGridSize(2, 2))
            .withCell(2, 7, spec().withGridSize(1, 2))
            .withCell(3, 3, spec().withGridHeightRemainder())
            .add(createButtons())
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   private static List<JButton> createButtons()
   {
      List<JButton> buttons = new LinkedList<JButton>();
      for (int i = 0; i < 30; i++)
      {
         buttons.add(new JButton("JButton " + i));
      }
      return buttons;
   }
}
