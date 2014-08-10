package org.quickstep;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import javax.swing.*;

import com.google.common.collect.*;
import org.quickstep.util.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public class GridBagBuilder
{
   private final JPanel panel;

   private int cursorY = 0;
   private int cursorX = 0;
   private boolean endOfLine = false;

   private int previousCursorY = 0;
   private int previousCursorX = 0;
   private boolean previousEndOfLine = false;

   private final Table<Integer, Integer, Boolean> usedCells = TreeBasedTable.create();
   private final Map<Integer, Integer> gridHeightRemainders = new TreeMap<Integer, Integer>();
   private final Map<Integer, Integer> gridWidthRemainders = new TreeMap<Integer, Integer>();

   private final GridSpec gridSpec;
   private boolean empty = true;

   protected GridBagBuilder(GridSpec gridSpec, JPanel panel)
   {
      this.panel = panel;
      this.gridSpec = gridSpec;
   }

   public GridSpec getGridSpec()
   {
      return gridSpec;
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

   public boolean isHorizontal() // refactor using method Orientation::isHorizontal()
   {
      return Orientation.HORIZONTAL.equals(gridSpec.getOrientation());
   }

   public boolean isEmpty()
   {
      return empty;
   }

   public void setEndOfLine(boolean value)
   {
      endOfLine = value;
   }

   public int getCurrentRowNumber()
   {
      return cursorY;
   }

   public int getCurrentColumnNumber()
   {
      return cursorX;
   }

   public int getCurrentLineNumber()
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
         throw new GridBagException("No free cell below line " + getCurrentLineNumber() + ".");
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

      DebugSupport.attachDebugInfo(component, panel, constraints);
      panel.add(getComponentToAdd(component, calculatedSpec), constraints);

      if (isHorizontal() && calculatedSpec.getGridWidth() == GridBagConstraints.REMAINDER ||
         !isHorizontal() && calculatedSpec.getGridHeight() == GridBagConstraints.REMAINDER) // TODO test this
      {
         endOfLine = true;
      }

      markAreaAsUsed(cursorX, cursorY, calculatedSpec);
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
         JPanel wrappingPanel = new ResizablePanel();
         wrappingPanel.add(component);
         return wrappingPanel;
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



   protected JComponent createDefaultHeader(String title, boolean first)
   {
      PanelCommand result = panel().add(title).add(createDefaultSeparator(), specWithFillX());
      if (!first)
      {
         result.specifyRow(0, spec().withInsetTop(5));
      }
      return result.getComponent();
   }

   protected JSeparator createDefaultSeparator()
   {
      return new JSeparator();
   }
}
