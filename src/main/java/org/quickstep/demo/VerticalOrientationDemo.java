package org.quickstep.demo;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import static org.quickstep.GridBagToolKit.*;

public class VerticalOrientationDemo extends JFrame
{
   public VerticalOrientationDemo() throws HeadlessException
   {
      buildContent(this, panel().
         withMaxLineLength(10).
         withOrientation(Orientation.VERTICAL).
         withScroll().
         withDefaultSpec(grow()).
         addAll(generateOptions())
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

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
      new VerticalOrientationDemo();
   }
}
