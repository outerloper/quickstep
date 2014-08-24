package org.quickstep;

import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class ComponentFactory
{
   public JComponent createLabel(String text)
   {
      return new JLabel(text);
   }

   public JComponent createHeader(String title, boolean placedInFirstRow)
   {
      GridContainerCommand result = panel().add(title).add(createSeparator(Orientation.HORIZONTAL), specWithFillX());
      if (!placedInFirstRow)
      {
         result.withRow(0, spec().withInsetTop(5));
      }
      return result.getComponent();
   }

   public JComponent createSeparator(Orientation orientation)
   {
      return new JSeparator(orientation.isHorizontal() ? JSeparator.HORIZONTAL : JSeparator.VERTICAL);
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

   public GridSpec getDefaultGridSpec()
   {
      return new GridSpec().
         withDefault(spec().withGap(5).withAnchorX(AX.LEFT)).
         withRow(0, spec().withInsetTop(0)).
         withColumn(0, spec().withInsetLeft(0));
   }

   public ComponentFactory getChildFactory()
   {
      return this;
   }
}
