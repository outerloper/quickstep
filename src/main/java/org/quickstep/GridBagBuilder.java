package org.quickstep;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.border.Border;

import com.google.common.collect.*;

import static org.quickstep.GridBagToolKit.*;

public class GridBagBuilder implements ComponentBuilder
{
   private final JPanel panel;
   private Integer maxLineLength;
   private Orientation orientation = Orientation.HORIZONTAL;

   private int cursorY = 0;
   private int cursorX = 0;
   private boolean endOfLine = false;

   private int previousCursorY = 0;
   private int previousCursorX = 0;
   private boolean previousEndOfLine = false;

   private final List<GridBagCommand> commands = new LinkedList<GridBagCommand>();

   private GridBagSpec spec = spec();
   private Border border;
   private JScrollPane scroll;

   private final GridBagSpec cellDefaultSpec;
   private final Map<Integer, GridBagSpec> columnSpecs = new TreeMap<Integer, GridBagSpec>();
   private final Map<Integer, GridBagSpec> rowSpecs = new TreeMap<Integer, GridBagSpec>();

   private final Table<Integer, Integer, GridBagSpec> cellSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, GridBagSpec> rowSpecsOverridingColumnSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, Boolean> usedCells = TreeBasedTable.create();
   private final Map<Integer, Integer> gridHeightsRemaining = new HashMap<Integer, Integer>();
   private final Map<Integer, Integer> gridWidthsRemaining = new HashMap<Integer, Integer>();

   protected GridBagBuilder(JPanel panel)
   {
      this(panel, spec().withGap(5));
   }

   protected GridBagBuilder(JPanel panel, GridBagSpec cellDefaultSpec)
   {
      this.panel = panel;
      this.cellDefaultSpec = cellDefaultSpec;

      specifyRow(0, spec().withInsetTop(0));
      specifyColumn(0, spec().withInsetLeft(0));
   }

   public GridBagBuilder derive()
   {
      return derive(new ResizablePanel());
   }

   public GridBagBuilder derive(JPanel panel)
   {
      GridBagBuilder result = new GridBagBuilder(panel, cellDefaultSpec.derive());
      result.spec = spec.derive();
      result.border = border;
      result.maxLineLength = maxLineLength;
      result.scroll = scroll;
      for (Map.Entry<Integer, GridBagSpec> entry : rowSpecs.entrySet())
      {
         result.rowSpecs.put(entry.getKey(), entry.getValue().derive());
      }
      for (Map.Entry<Integer, GridBagSpec> entry : columnSpecs.entrySet())
      {
         result.columnSpecs.put(entry.getKey(), entry.getValue().derive());
      }
      for (Map.Entry<Integer, Integer> entry : gridHeightsRemaining.entrySet())
      {
         result.gridHeightsRemaining.put(entry.getKey(), entry.getValue());
      }
      for (Map.Entry<Integer, Integer> entry : gridWidthsRemaining.entrySet())
      {
         result.gridWidthsRemaining.put(entry.getKey(), entry.getValue());
      }
      for (Table.Cell<Integer, Integer, GridBagSpec> cell : cellSpecs.cellSet())
      {
         result.cellSpecs.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue().derive());
      }
      for (Table.Cell<Integer, Integer, GridBagSpec> cell : rowSpecsOverridingColumnSpecs.cellSet())
      {
         result.rowSpecsOverridingColumnSpecs.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue().derive());
      }
      return result;
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
      if (maxLineLength != null && (isHorizontal() ? cursorX : cursorY) >= maxLineLength || endOfLine)
      {
         newLine();
      }
   }

   boolean isHorizontal()
   {
      return Orientation.HORIZONTAL.equals(orientation);
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

   public final GridBagBuilder add()
   {
      return add(spec());
   }

   public GridBagBuilder add(GridBagSpec spec)
   {
      return add(createDefaultLabel(null), spec);
   }

   public final GridBagBuilder add(String text)
   {
      return add(text, spec());
   }

   public GridBagBuilder add(String text, GridBagSpec spec)
   {
      return add(createDefaultLabel(text), spec);
   }

   public final GridBagBuilder add(JComponent component)
   {
      return add(component, spec());
   }

   public GridBagBuilder add(JComponent component, GridBagSpec spec)
   {
      return add(new ComponentBuilderAdapter(component), spec.derive());
   }

   public final GridBagBuilder add(ComponentBuilder componentBuilder)
   {
      return add(componentBuilder, spec());
   }

   public GridBagBuilder add(ComponentBuilder componentBuilder, GridBagSpec spec)
   {
      return add(new AddComponentCommand(componentBuilder, spec.derive()));
   }

   public final GridBagBuilder add(Iterable<? extends JComponent> components)
   {
      return add(components, spec());
   }

   public final GridBagBuilder add(Iterable<? extends JComponent> components, GridBagSpec spec)
   {
      for (JComponent component : components)
      {
         add(component, spec);
      }
      return this;
   }

   public final GridBagBuilder add(GridBagLine line)
   {
      return add(new AddLineCommand(line));
   }

   private GridBagBuilder add(GridBagCommand item)
   {
      if (item == null)
      {
         throw new NullPointerException();
      }
      commands.add(item);
      return this;
   }

   public final GridBagBuilder nextLine()
   {
      add(new NextLineCommand());
      return this;
   }

   void placeComponent(JComponent component, GridBagSpec givenSpec)
   {
      GridBagSpec calculatedSpec = calculateSpec(cursorX, cursorY, givenSpec);

      if (!isAreaFree(cursorX, cursorY, calculatedSpec.getGridWidth(), calculatedSpec.getGridHeight()))
      {
         logger.log(Level.WARNING, String.format("GridBagBuilder: no enough place for component: %s with constraints: %s\n",
                                                 objectId(component), calculatedSpec));
      }

      GridBagConstraints constraints = calculatedSpec.toConstraints(cursorX, cursorY);

      attachDebugInfo(component, panel, constraints);

      panel.add(getComponentToAdd(component, calculatedSpec), constraints);
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

   private GridBagSpec calculateSpec(int x, int y, GridBagSpec spec)
   {
      GridBagSpec[] lineSpecs = new GridBagSpec[]{columnSpecs.get(x), rowSpecs.get(y)};
      int i = rowSpecsOverridingColumnSpecs.get(x, y) != null ? 0 : 1;
      return completeSpec().
         overrideWith(cellDefaultSpec).
         overrideWith(lineSpecs[i]).
         overrideWith(lineSpecs[(i + 1) % 2]).
         overrideWith(cellSpecs.get(x, y)).
         overrideWith(spec);
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

   @Override
   public final JPanel build()
   {
      JPanel content = panel;
      content.setLayout(new GridBagLayout());
      for (GridBagCommand command : commands)
      {
         command.apply(this);
      }

      if (scroll != null)
      {
         scroll.setViewportView(content);
         content = new ResizablePanel();
         content.add(scroll);
      }
      if (border != null)
      {
         content.setBorder(border);
      }
      return content;
   }


   public final GridBagBuilder withSpec(GridBagSpec spec)
   {
      this.spec.overrideWith(spec);
      return this;
   }

   @Override
   public final GridBagSpec getSpec()
   {
      return spec;
   }


   public final GridBagBuilder specifyCellDefaults(GridBagSpec spec)
   {
      cellDefaultSpec.overrideWith(spec);
      return this;
   }

   public final GridBagBuilder specifyColumn(int x, GridBagSpec spec)
   {
      for (Integer y : rowSpecsOverridingColumnSpecs.column(x).keySet())
      {
         rowSpecsOverridingColumnSpecs.remove(x, y);
      }
      GridBagSpec columnSpec = columnSpecs.get(x);
      if (columnSpec == null)
      {
         columnSpec = spec();
      }
      columnSpecs.put(x, columnSpec.overrideWith(spec));
      return this;
   }

   public final GridBagBuilder specifyRow(int y, GridBagSpec spec)
   {
      for (Integer x : columnSpecs.keySet())
      {
         rowSpecsOverridingColumnSpecs.put(x, y, spec);
      }
      GridBagSpec rowSpec = rowSpecs.get(y);
      if (rowSpec == null)
      {
         rowSpec = spec();
      }
      rowSpecs.put(y, rowSpec.overrideWith(spec));
      return this;
   }

   public final GridBagBuilder specifyCell(int x, int y, GridBagSpec spec)
   {
      GridBagSpec cellSpec = cellSpecs.get(x, y);
      if (cellSpec == null)
      {
         cellSpec = spec();
      }
      cellSpecs.put(x, y, cellSpec.overrideWith(spec));
      return this;
   }

   public final GridBagBuilder withOrientation(Orientation value)
   {
      orientation = value;
      return this;
   }

   public final GridBagBuilder withMaxLineLength(int value)
   {
      maxLineLength = value;
      return this;
   }

   private JComponent createDefaultLabel(String text)
   {
      return new JLabel(text);
   }

   protected Border createDefaultBorder(String title)
   {
      return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title), BorderFactory.createEmptyBorder(5, 5, 5, 5));
   }

   public final GridBagBuilder withBorder()
   {
      return withBorder(null);
   }

   public final GridBagBuilder withBorder(String title)
   {
      return withBorder(createDefaultBorder(title));
   }

   public final GridBagBuilder withBorder(Border innerBorder, Border... outerBorders)
   {
      border = innerBorder;
      for (Border outerBorder : outerBorders)
      {
         border = BorderFactory.createCompoundBorder(outerBorder, border);
      }
      return this;
   }

   protected JScrollPane createDefaultScrollPane()
   {
      JScrollPane result = new JScrollPane();
      result.getHorizontalScrollBar().setUnitIncrement(10);
      result.getHorizontalScrollBar().setBlockIncrement(10);
      result.getVerticalScrollBar().setUnitIncrement(10);
      result.getVerticalScrollBar().setBlockIncrement(10);
      result.setBorder(BorderFactory.createEmptyBorder());
      return result;
   }

   public final GridBagBuilder withScroll()
   {
      return withScroll(createDefaultScrollPane());
   }

   public final GridBagBuilder withScroll(JScrollPane scrollPane)
   {
      scroll = scrollPane;
      return this;
   }
}
