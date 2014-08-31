package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.*;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomComponentFactoryDemo extends JFrame
{
   public CustomComponentFactoryDemo()
   {
      createComponentFactory().buildContent(this, panel().
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
      return new ComponentFactory()
      {
         @Override
         public ComponentFactory getContentFactory()
         {
            return new ComponentFactory()
            {
               @Override
               public Border createBorder(String title)
               {
                  return createCompoundBorder(createTitledBorder(createLineBorder(Color.GRAY, 2), title), createEmptyBorder(12, 12, 12, 12));
               }

               @Override
               public JPanel createPanel()
               {
                  JPanel panel = super.createPanel();
                  panel.setBackground(Color.LIGHT_GRAY);
                  return panel;
               }

               @Override
               public GridSpec createDefaultGridSpec()
               {
                  return super.createDefaultGridSpec().withDefault(spec().withGap(10));
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

         @Override
         public AX getContentAnchorX()
         {
            return AX.BOTH;
         }

         @Override
         public AY getContentAnchorY()
         {
            return AY.BOTH;
         }
      };
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new CustomComponentFactoryDemo();
   }
}
