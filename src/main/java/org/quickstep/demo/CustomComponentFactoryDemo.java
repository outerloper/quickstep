package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.*;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomComponentFactoryDemo
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withSpec(spec().withInset(20, 3))
            .withComponentFactory(createComponentFactory())
            .withDirection(Direction.TOP_TO_BOTTOM)
            .add(panel()
                    .withBorder("Custom Panel")
                    .add(line().add("User", new JTextField(), spec().withPreferredWidth(100)))
                    .add(line().add("Password", new JTextField(), spec().withPreferredWidth(100)))
            )
            .add(new JButton("Proceed"), spec().withGridWidthRemainder().withAnchor(A.CENTER).withInset(5))
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   private static ComponentFactory createComponentFactory()
   {
      return new ComponentFactory()
      {
         @Override
         public ComponentFactory createChildFactory()
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
               public GridSpec getGridSpec()
               {
                  return super.getGridSpec().withDefault(spec().withGap(10));
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
}
