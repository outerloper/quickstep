package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.*;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomComponentFactoryDemo extends JFrame
{
   public CustomComponentFactoryDemo() throws HeadlessException
   {
      buildContent(this, panel().
         withComponentFactory(createComponentFactory()).
         withOrientation(Orientation.VERTICAL).
         add(panel().
            add(line().add("User").add(new JTextField(), spec().withPreferredWidth(100))).
            add(line().add("Password").add(new JTextField(), spec().withPreferredWidth(100))).
            withBorder("Custom Panel")
         ).
         add(new JButton("Proceed"), spec().withGridWidthRemainder().withAnchor(A.CENTER))
      );
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   private ComponentFactory createComponentFactory()
   {
      return new DefaultComponentFactory()
      {
         @Override
         public ComponentFactory getChildFactory()
         {
            return new DefaultComponentFactory()
            {
               @Override
               public Border createBorder(String title)
               {
                  return createCompoundBorder(createTitledBorder(createLineBorder(Color.ORANGE, 10), title), createEmptyBorder(9, 9, 9, 9));
               }

               @Override
               public JPanel createPanel()
               {
                  JPanel panel = super.createPanel();
                  panel.setBackground(Color.LIGHT_GRAY);
                  return panel;
               }
            };
         }

         @Override
         public JPanel createPanel()
         {
            JPanel panel = super.createPanel();
            panel.setBackground(Color.GRAY);
            return panel;
         }
      };
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new CustomComponentFactoryDemo();
   }
}
