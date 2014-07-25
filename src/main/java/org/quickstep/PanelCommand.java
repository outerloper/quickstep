package org.quickstep;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class PanelCommand implements CellCommand, GridBagCommandsCollector<PanelCommand>, GridBagBuilderClient
{
   private JPanel panel;
   private Integer maxLineLength;
   private Orientation orientation = Orientation.HORIZONTAL;

   private final GridBagCommandsCollectorComponent<PanelCommand> commandsCollector = new GridBagCommandsCollectorComponent<PanelCommand>(this);

   private GridBagSpec spec = spec();
   private Border border;
   private JScrollPane scroll;

   private final GridBagSpec cellDefaultSpec;
   private final Map<Integer, GridBagSpec> columnSpecs = new TreeMap<Integer, GridBagSpec>();
   private final Map<Integer, GridBagSpec> rowSpecs = new TreeMap<Integer, GridBagSpec>();

   private final Table<Integer, Integer, GridBagSpec> cellSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, GridBagSpec> rowSpecsOverridingColumnSpecs = TreeBasedTable.create();

   protected PanelCommand(JPanel panel)
   {
      this(panel, spec().withGap(5));
   }

   protected PanelCommand(JPanel panel, GridBagSpec cellDefaultSpec)
   {
      this.panel = panel;
      this.cellDefaultSpec = cellDefaultSpec;

      specifyRow(0, spec().withInsetTop(0));
      specifyColumn(0, spec().withInsetLeft(0));
   }

   public PanelCommand derive()
   {
      return derive(new ResizablePanel());
   }

   public PanelCommand derive(JPanel panel)
   {
      PanelCommand result = new PanelCommand(panel, cellDefaultSpec.derive());
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

   public boolean isHorizontal()
   {
      return Orientation.HORIZONTAL.equals(orientation);
   }

   @Override
   public final PanelCommand nextLine()
   {
      return commandsCollector.nextLine();
   }

   @Override
   public final PanelCommand addBlank()
   {
      return commandsCollector.addBlank();
   }

   @Override
   public PanelCommand addBlank(GridBagSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public final PanelCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public PanelCommand add(String text, GridBagSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public final PanelCommand add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   @Override
   public PanelCommand add(JComponent component, GridBagSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public final PanelCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public final PanelCommand addAll(Iterable<? extends JComponent> components, GridBagSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public PanelCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public PanelCommand addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   @Override
   public PanelCommand addSeparatingLine()
   {
      return commandsCollector.addSeparatingLine();
   }

   @Override
   public PanelCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   public GridBagSpec getSpecAt(int x, int y)
   {
      GridBagSpec[] lineSpecs = new GridBagSpec[]{columnSpecs.get(x), rowSpecs.get(y)};
      int i = rowSpecsOverridingColumnSpecs.get(x, y) != null ? 0 : 1;
      return completeSpec().
         overrideWith(cellDefaultSpec).
         overrideWith(lineSpecs[i]).
         overrideWith(lineSpecs[(i + 1) % 2]).
         overrideWith(cellSpecs.get(x, y));
   }

   @Override
   public void addComponent(JComponent component, GridBagConstraints constraints)
   {
      panel.add(component, constraints);
      attachDebugInfo(component, panel, constraints);
   }

   public final PanelCommand withSpec(GridBagSpec spec)
   {
      this.spec.overrideWith(spec);
      return this;
   }

   @Override
   public JComponent getComponent()
   {
      JPanel content = panel;
      content.setLayout(new GridBagLayout());
      GridBagBuilder builder = new GridBagBuilder(this);
      for (GridBagCommand command : commandsCollector)
      {
         command.apply(builder);
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

   @Override
   public final GridBagSpec getSpec()
   {
      return spec;
   }

   @Override
   public final Integer getMaxLineLength()
   {
      return maxLineLength;
   }


   public final PanelCommand specifyCellDefaults(GridBagSpec spec)
   {
      cellDefaultSpec.overrideWith(spec);
      return this;
   }

   public final PanelCommand specifyColumn(int x, GridBagSpec spec)
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

   public final PanelCommand specifyRow(int y, GridBagSpec spec)
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

   public final PanelCommand specifyCell(int x, int y, GridBagSpec spec)
   {
      GridBagSpec cellSpec = cellSpecs.get(x, y);
      if (cellSpec == null)
      {
         cellSpec = spec();
      }
      cellSpecs.put(x, y, cellSpec.overrideWith(spec));
      return this;
   }

   public final PanelCommand withOrientation(Orientation value)
   {
      orientation = value;
      return this;
   }

   public final PanelCommand withMaxLineLength(int value)
   {
      maxLineLength = value;
      return this;
   }

   protected Border createDefaultBorder(String title)
   {
      return createCompoundBorder(createTitledBorder(createEtchedBorder(), title), createEmptyBorder(5, 5, 5, 5));
   }

   public final PanelCommand withBorder()
   {
      return withBorder(null);
   }

   public final PanelCommand withBorder(String title)
   {
      return withBorder(createDefaultBorder(title));
   }

   public final PanelCommand withBorder(Border innerBorder, Border... outerBorders)
   {
      border = innerBorder;
      for (Border outerBorder : outerBorders)
      {
         border = createCompoundBorder(outerBorder, border);
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
      result.setBorder(createEmptyBorder());
      return result;
   }

   public final PanelCommand withScroll()
   {
      return withScroll(createDefaultScrollPane());
   }

   public final PanelCommand withScroll(JScrollPane scrollPane)
   {
      scroll = scrollPane;
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.moveToFreeCell();
      builder.placeComponent(getComponent(), getSpec());
   }
}
