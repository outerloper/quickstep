package org.quickstep;

/*
 * ---------------------------------------------------------------------
 * Quickstep
 * ------
 * Copyright (C) 2014 Konrad Sacala
 * ------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * ---------------------------------------------------------------------
 */


import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.command.GridContainerCommand;
import org.quickstep.spec.CellSpec;
import org.quickstep.spec.GridSpec;

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
      GridContainerCommand result = panel()
         .withRow(0, spec().withInsetBottom(3))
         .add(title)
         .add(createSeparator(Direction.LEFT_TO_RIGHT), spec().withAnchorX(AX.BOTH).withWeightX(1.0));
      if (!placedInFirstRow)
      {
         result.withRow(0, spec().withInsetTop(10));
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
