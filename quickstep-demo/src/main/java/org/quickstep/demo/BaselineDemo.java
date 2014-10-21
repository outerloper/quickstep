package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class BaselineDemo
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withContentAnchor(A.BOTH)
            .withDefault(spec().withBaseline())
            .add(line().add("Label respecting baseline", getBigFontTextField()))
            .add(line().add("&Label respecting baseline", vpanel()
                               .withContentAnchor(A.BOTH)
                               .withLineLength(4)
                               .add(getBigFontCheckBoxes(), spec().withIPadY(10).withInsetTop(10))
                 )
            )
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   private static Font font16()
   {
      return new Font("Arial", Font.PLAIN, 16);
   }

   private static JTextField getBigFontTextField()
   {
      JTextField textField = new JTextField();
      textField.setFont(font16());
      return textField;
   }

   private static List<JCheckBox> getBigFontCheckBoxes()
   {
      List<JCheckBox> checkBoxes = new LinkedList<JCheckBox>();
      for (int i = 0; i < 12; i++)
      {
         JCheckBox checkBox = new JCheckBox("Item " + i);
         checkBoxes.add(checkBox);
         checkBox.setFont(font16());
      }
      return checkBoxes;
   }
}
