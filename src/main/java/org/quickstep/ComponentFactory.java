package org.quickstep;

import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class ComponentFactory
{
   public JLabel createLabel(String text)
   {
      return new JLabel(text);
   }

   public JComponent createHeader(String title, boolean placedInFirstRow)
   {
      GridContainerCommand result = panel().add(title).add(createSeparator(Direction.LEFT_TO_RIGHT), spec().withAnchorX(AX.BOTH).withWeightX(1.0));
      if (!placedInFirstRow)
      {
         result.withRow(0, spec().withInsetTop(5));
      }
      return result.getComponent();
   }

   public JComponent createSeparator(Direction direction)
   {
      return new JSeparator(direction.isLeftToRight() ? JSeparator.HORIZONTAL : JSeparator.VERTICAL);
   }

   public JPanel createPanel()
   {
      return new ResizablePanel();
   }

   public Border createBorder(String title)
   {
      return createCompoundBorder(createTitledBorder(title), createEmptyBorder(5, 5, 5, 5));
   }

   public JScrollPane createScrollPane()
   {
      JScrollPane result = new JScrollPane();
      result.getHorizontalScrollBar().setUnitIncrement(10);
      result.getHorizontalScrollBar().setBlockIncrement(10);
      result.getVerticalScrollBar().setUnitIncrement(10);
      result.getVerticalScrollBar().setBlockIncrement(10);
      result.setBorder(createEmptyBorder());
      return result;
   }

   public GridSpec getGridSpec()
   {
      return new GridSpec()
         .withDefault(spec().withGap(5).withAnchorX(AX.LEFT))
         .withRow(0, spec().withInsetTop(0))
         .withColumn(0, spec().withInsetLeft(0));
   }

   public AX getContentAnchorX()
   {
      return AX.CENTER;
   }

   public AY getContentAnchorY()
   {
      return AY.CENTER;
   }

   public ComponentFactory createChildFactory()
   {
      return this;
   }

   public JComponent apply(JComponent component, CellSpec spec)
   {
      return component;
   }
}
