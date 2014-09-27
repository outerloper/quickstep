package org.quickstep.demo;

import java.util.*;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SpannedCellsDemo extends JFrame
{
   List<JButton> buttons = new LinkedList<JButton>();

   public SpannedCellsDemo()
   {
      for (int i = 0; i < 30; i++)
      {
         buttons.add(new JButton("JButton " + i));
      }

      buildContent(
         this, panel()
            .withLineLength(4)
            .withContentAnchor(A.BOTH)
            .withCell(1, 2, spec().withGridSize(2, 3))
            .withCell(0, 5, spec().withGridSize(2, 2))
            .withCell(2, 7, spec().withGridSize(1, 2))
            .withCell(3, 3, spec().withGridHeightRemainder())
            .add(buttons)
      );

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setVisible(true);
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      new SpannedCellsDemo();
   }
}
