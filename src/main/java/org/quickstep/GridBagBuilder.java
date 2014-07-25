package org.quickstep;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import javax.swing.*;

import com.google.common.collect.*;

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
   private final Map<Integer, Integer> gridHeightsRemaining = new HashMap<Integer, Integer>();
   private final Map<Integer, Integer> gridWidthsRemaining = new HashMap<Integer, Integer>();

   private final GridBagBuilderSpec client;

   protected GridBagBuilder(GridBagBuilderSpec builderSpec, JPanel panel)
   {
      this.panel = panel;
      this.client = builderSpec;
   }

   private void moveToNextCell()
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
      if (client.getMaxLineLength() != null && (isHorizontal() ? cursorX : cursorY) >= client.getMaxLineLength() || endOfLine)
      {
         newLine();
      }
   }

   boolean isHorizontal()
   {
      return client.isHorizontal();
   }

   boolean isEmpty()
   {
      return cursorX == 0 && cursorY == 0;
   }

   void setEndOfLine(boolean value)
   {
      endOfLine = value;
   }

   int getCurrentLineNumber()
   {
      return isHorizontal() ? cursorY : cursorX;
   }

   int getCurrentPositionInLine()
   {
      return isHorizontal() ? cursorX : cursorY;
   }

   void moveToPreviousCell()
   {
      cursorX = previousCursorX;
      cursorY = previousCursorY;
      endOfLine = previousEndOfLine;
   }

   void moveToFreeCell()
   {
      if (!isCellFree(cursorX, cursorY))
      {
         moveToNextFreeCell();
      }
   }

   void moveToNextLine()
   {
      endOfLine = true;
      moveToNextFreeCell();
   }

   void moveToNextFreeCell()
   {
      do
      {
         moveToNextCell();
      }
      while (!isCellFree(cursorX, cursorY));
   }

   private void newLine()
   {
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

   void placeComponent(JComponent component, GridBagSpec givenSpec)
   {
      GridBagSpec calculatedSpec = client.getSpecAt(cursorX, cursorY).overrideWith(givenSpec);

      if (!isAreaFree(cursorX, cursorY, calculatedSpec.getGridWidth(), calculatedSpec.getGridHeight()))
      {
         logger.log(Level.WARNING, String.format("GridBagBuilder: no enough place for component: %s with constraints: %s\n",
                                                 objectId(component), calculatedSpec));
      }

      GridBagConstraints constraints = calculatedSpec.toConstraints(cursorX, cursorY);

      panel.add(getComponentToAdd(component, calculatedSpec), constraints);
      attachDebugInfo(component, panel, constraints);

      if (calculatedSpec.getGridWidth() == GridBagConstraints.REMAINDER)
      {
         endOfLine = true;
      }

      markAreaAsUsed(cursorX, cursorY, calculatedSpec);
   }

   private JComponent getComponentToAdd(JComponent component, GridBagSpec calculatedSpec)
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

   private void markAreaAsUsed(int x, int y, GridBagSpec spec)
   {
      Integer width = spec.getGridWidth();
      Integer height = spec.getGridHeight();
      int x1 = x + width;
      int y1 = y + height;
      if (width == GridBagConstraints.REMAINDER)
      {
         for (int j = y; j < y1; j++)
         {
            gridWidthsRemaining.put(j, x);
         }
      }
      if (height == GridBagConstraints.REMAINDER)
      {
         for (int i = x; i < x1; i++)
         {
            gridHeightsRemaining.put(i, y);
         }
      }
      for (int i = x; i < x1; i++)
      {
         for (int j = y; j < y1; j++)
         {
            usedCells.put(i, j, true);
         }
      }
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
      Integer rowSpanFrom = gridHeightsRemaining.get(x);
      if (rowSpanFrom != null && y >= rowSpanFrom)
      {
         return false;
      }
      Integer columnSpanFrom = gridWidthsRemaining.get(y);
      if (columnSpanFrom != null && x >= columnSpanFrom)
      {
         return false;
      }
      //noinspection SimplifiableIfStatement
      return usedCells.get(x, y) == null;
   }


   protected JComponent createDefaultLabel(String text)
   {
      return new JLabel(text);
   }

   protected JComponent createDefaultHeader(String title)
   {
      return panel().add(title).add(createDefaultSeparator(), growX()).getComponent();
   }

   protected JSeparator createDefaultSeparator()
   {
      return new JSeparator();
   }
}
