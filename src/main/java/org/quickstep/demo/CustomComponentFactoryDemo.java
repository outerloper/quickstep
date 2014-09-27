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
      createComponentFactory().buildContent(
         this, panel()
            .withDirection(Direction.TOP_TO_BOTTOM)
            .add(panel()
                    .withBorder("Custom Panel")
                    .add(line().add("User", new JTextField(), spec().withPreferredWidth(100)))
                    .add(line().add("Password", new JTextField(), spec().withPreferredWidth(100)))
            )
            .add(new JButton("Proceed"), spec().withGridWidthRemainder().withAnchor(A.CENTER).withInset(5))
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

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      new CustomComponentFactoryDemo();
   }
}
