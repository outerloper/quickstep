package org.quickstep.demo;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class BaselineDemo
{
   public static final int N = 12;

   private static Font font16()
   {
      return new Font("Arial", Font.PLAIN, 16);
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();

      JTextField textField = new JTextField();
      textField.setFont(font16());

      JCheckBox[] checkBoxes = new JCheckBox[N];
      for (int i = 0; i < N; i++)
      {
         checkBoxes[i] = new JCheckBox("Item " + i);
         checkBoxes[i].setFont(font16());
      }

      buildContent(
         frame, panel()
            .withContentAnchor(A.BOTH)
            .withDefault(spec().withBaseline())
            .add(line().add("Label respecting baseline", textField))
            .add(line().add("&Label respecting baseline", vpanel()
                               .withContentAnchor(A.BOTH)
                               .withLineLength(4)
                               .add(Arrays.asList(checkBoxes), spec().withIPadY(10).withInsetTop(10))
                 )
            )
      );

      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
      frame.pack();
   }
}
