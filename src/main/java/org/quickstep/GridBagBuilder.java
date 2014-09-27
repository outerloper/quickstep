package org.quickstep;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import javax.swing.*;

import com.google.common.collect.*;
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
      return gridSpec.getDirection().isHorizontal();
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

      if (!isAreaFree(cursorX, cursorY, calculatedSpec.getGridWidth(), calculatedSpec.getGridHeight()))
      {
         logger.log(Level.WARNING, String.format("GridBagBuilder: no enough place for component: %s with constraints: %s\n",
                                                 DebugSupport.objectId(component), calculatedSpec));
      }

      GridBagConstraints constraints = calculatedSpec.toConstraints(cursorX, cursorY);

      DebugSupport.attachDebugInfo(component, gridContainer, constraints);
      JComponent componentToAdd = getComponentToAdd(component, calculatedSpec);
      gridContainer.add(componentToAdd, constraints);
      groupsSupport.add(componentToAdd, calculatedSpec);
      handleMnemonic(component); // TODO - move to factory? then factory stateful so new instance required in getChild
      // TODO also think about renaming ComponentFactory as it starts to do everything...

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

   private JComponent getComponentToAdd(JComponent component, CellSpec calculatedSpec)
   {
      Integer width = calculatedSpec.getPreferredWidth();
      if (width != null)
      {
         component.setPreferredSize(new Dimension(width, component.getPreferredSize().height));
      }
      Integer height = calculatedSpec.getPreferredHeight();
      if (height != null)
      {
         component.setPreferredSize(new Dimension(component.getPreferredSize().width, height));
      }
      if (width != null || height != null)
      {
         return ResizablePanel.wrap(component);
      }
      return component;
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
