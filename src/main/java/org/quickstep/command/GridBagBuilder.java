package org.quickstep.command;

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


import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import javax.swing.*;

import com.google.common.collect.*;
import org.quickstep.ComponentFactory;
import org.quickstep.GridBagException;
import org.quickstep.spec.CellSpec;
import org.quickstep.spec.GridSpec;
import org.quickstep.support.*;

import static org.quickstep.GridBagToolKit.*;

public class GridBagBuilder
{
   private final JComponent gridContainer;

   private int cursorY = 0;
   private int cursorX = 0;
   private boolean endOfLine = false;

   private int previousCursorY = 0;
   private int previousCursorX = 0;
   private boolean previousEndOfLine = false;

   private JLabel labelWithMnemonic = null;

   private final Table<Integer, Integer, Boolean> usedCells = TreeBasedTable.create();
   private final Map<Integer, Integer> gridHeightRemainders = new TreeMap<Integer, Integer>();
   private final Map<Integer, Integer> gridWidthRemainders = new TreeMap<Integer, Integer>();

   private final GridSpec gridSpec;
   private boolean empty = true;

   private final ComponentFactory componentFactory;
   private SizeGroupsSupport groupsSupport;

   protected GridBagBuilder(JComponent gridContainer, GridSpec gridSpec, ComponentFactory componentFactory, SizeGroupsSupport groupsSupport)
   {
      this.gridContainer = gridContainer;
      this.gridSpec = gridSpec;
      this.componentFactory = componentFactory;
      this.groupsSupport = groupsSupport;
   }

   public GridSpec getGridSpec()
   {
      return gridSpec;
   }

   public ComponentFactory getComponentFactory()
   {
      return componentFactory;
   }

   private void moveToNextCell() throws GridBagException
   {
      previousCursorX = cursorX;
      previousCursorY = cursorY;
      previousEndOfLine = endOfLine;

      if (isHorizontal())
      {
         cursorX++;
      }
      else
      {
         cursorY++;
      }
      //noinspection SimplifiableIfStatement
      if (gridSpec.getLineLength() != null && (isHorizontal() ? cursorX : cursorY) >= gridSpec.getLineLength() || endOfLine)
      {
         newLine();
      }
   }

   public boolean isHorizontal()
   {
      return gridSpec.getDirection().isLeftToRight();
   }

   public boolean isEmpty()
   {
      return empty;
   }

   public void setEndOfLine(boolean value)
   {
      endOfLine = value;
   }

   public int getCurrentRowIndex()
   {
      return cursorY;
   }

   public int getCurrentColumnIndex()
   {
      return cursorX;
   }

   public int getCurrentLineIndex()
   {
      return isHorizontal() ? cursorY : cursorX;
   }

   public void moveToPreviousCell()
   {
      cursorX = previousCursorX;
      cursorY = previousCursorY;
      endOfLine = previousEndOfLine;
   }

   public void moveToFreeCell() throws GridBagException
   {
      if (!isCellFree(cursorX, cursorY))
      {
         moveToNextFreeCell();
      }
   }

   public void moveToNextLine() throws GridBagException
   {
      endOfLine = true;
      moveToNextFreeCell();
   }

   public void moveToNextFreeCell() throws GridBagException
   {
      do
      {
         moveToNextCell();
      }
      while (!isCellFree(cursorX, cursorY));
   }

   private void newLine() throws GridBagException
   {
      if (!freeCellInNextLinesExists())
      {
         throw new GridBagException("No free cell below line " + getCurrentLineIndex() + ".");
      }
      if (isHorizontal())
      {
         cursorY++;
         cursorX = 0;
      }
      else
      {
         cursorX++;
         cursorY = 0;
      }
      endOfLine = false;
   }

   private boolean freeCellInNextLinesExists()
   {
      Integer lineLength = gridSpec.getLineLength();
      if (lineLength == null)
      {
         return true;
      }
      Map<Integer, Integer> remainders = isHorizontal() ? gridHeightRemainders : gridWidthRemainders;
      for (int i = 0; i < lineLength; i++)
      {
         if (remainders.get(i) == null)
         {
            return true;
         }
      }
      return false;
   }

   public void placeComponent(JComponent component, CellSpec givenSpec)
   {
      CellSpec calculatedSpec = completeSpec().overrideWith(gridSpec.getSpecAt(cursorX, cursorY)).overrideWith(givenSpec);
      component = componentFactory.apply(component, calculatedSpec);

      if (!isAreaFree(cursorX, cursorY, calculatedSpec.getGridWidth(), calculatedSpec.getGridHeight()))
      {
         logger.log(Level.WARNING, String.format("GridBagBuilder: no enough place for component: %s with constraints: %s\n",
                                                 DebugSupport.objectId(component), calculatedSpec));
      }

      GridBagConstraints constraints = calculatedSpec.toConstraints(cursorX, cursorY);

      DebugSupport.attachDebugInfo(component, gridContainer, constraints);
      JComponent componentToAdd = SizeGroupsSupport.applyPreferredSize(component, calculatedSpec);
      gridContainer.add(componentToAdd, constraints);
      groupsSupport.add(componentToAdd, calculatedSpec);
      handleMnemonic(component);

      if (isHorizontal() && calculatedSpec.getGridWidth() == GridBagConstraints.REMAINDER ||
         !isHorizontal() && calculatedSpec.getGridHeight() == GridBagConstraints.REMAINDER)
      {
         endOfLine = true;
      }

      markAreaAsUsed(cursorX, cursorY, calculatedSpec);
   }

   private void handleMnemonic(JComponent component)
   {
      if (component instanceof JLabel)
      {
         JLabel label = (JLabel) component;
         Component labelFor = label.getLabelFor();
         boolean hasMnemonic = MnemonicSupport.setUp(label, labelFor);
         labelWithMnemonic = labelFor == null && hasMnemonic ? label : null;
      }
      else if (component instanceof JButton)
      {
         MnemonicSupport.setUp((JButton) component);
         labelWithMnemonic = null;
      }
      else if (labelWithMnemonic != null)
      {
         labelWithMnemonic.setLabelFor(component);
         labelWithMnemonic = null;
      }
   }

   private void markAreaAsUsed(int x, int y, CellSpec spec)
   {
      Integer width = spec.getGridWidth();
      Integer height = spec.getGridHeight();

      boolean widthRemainder = width == GridBagConstraints.REMAINDER;
      boolean heightRemainder = height == GridBagConstraints.REMAINDER;

      int x1 = x + (widthRemainder ? 1 : width);
      int y1 = y + (heightRemainder ? 1 : height);

      if (widthRemainder)
      {
         for (int j = y; j < y1; j++)
         {
            gridWidthRemainders.put(j, x);
         }
      }
      if (heightRemainder)
      {
         for (int i = x; i < x1; i++)
         {
            gridHeightRemainders.put(i, y);
         }
      }
      for (int i = x; i < x1; i++)
      {
         for (int j = y; j < y1; j++)
         {
            usedCells.put(i, j, true);
         }
      }
      empty = false;
   }

   private boolean isAreaFree(int x, int y, int width, int height)
   {
      int x1 = x + width;
      int y1 = y + height;
      for (int i = x; i < x1; i++)
      {
         for (int j = y; j < y1; j++)
         {
            if (!isCellFree(i, j))
            {
               return false;
            }
         }
      }
      return true;
   }

   private boolean isCellFree(int x, int y)
   {
      Integer rowSpanFrom = gridHeightRemainders.get(x);
      if (rowSpanFrom != null && y >= rowSpanFrom)
      {
         return false;
      }
      Integer columnSpanFrom = gridWidthRemainders.get(y);
      if (columnSpanFrom != null && x >= columnSpanFrom)
      {
         return false;
      }
      Integer lineLength = gridSpec.getLineLength();
      if (lineLength != null && (isHorizontal() ? x : y) >= lineLength)
      {
         return false;
      }
      //noinspection SimplifiableIfStatement
      return usedCells.get(x, y) == null;
   }
}
