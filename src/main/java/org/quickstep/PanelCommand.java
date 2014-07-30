package org.quickstep;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class PanelCommand implements CellCommand, GridBagCommandsCollector<PanelCommand>, GridBagBuilderSpec
{
   private JPanel panel;
   private Integer maxLineLength;
   private Orientation orientation = Orientation.HORIZONTAL;

   private final GridBagCommandsCollectorComponent<PanelCommand> commandsCollector = new GridBagCommandsCollectorComponent<PanelCommand>(this);

   private CellSpec spec = spec();
   private Border border;
   private JScrollPane scroll;

   private final CellSpec cellDefaultSpec;
   private final Map<Integer, CellSpec> columnSpecs = new TreeMap<Integer, CellSpec>();
   private final Map<Integer, CellSpec> rowSpecs = new TreeMap<Integer, CellSpec>();

   private final Table<Integer, Integer, CellSpec> cellSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, CellSpec> rowSpecsOverridingColumnSpecs = TreeBasedTable.create();

   protected PanelCommand(JPanel panel)
   {
      this(panel, spec().withGap(5));
   }

   protected PanelCommand(JPanel panel, CellSpec cellDefaultSpec)
   {
      this.panel = panel;
      this.cellDefaultSpec = cellDefaultSpec;

      withRowSpec(0, spec().withInsetTop(0));
      withColumnSpec(0, spec().withInsetLeft(0));
   }

   public final boolean isHorizontal()
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
   public PanelCommand addBlank(CellSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   @Override
   public final PanelCommand add(String text)
   {
      return commandsCollector.add(text);
   }

   @Override
   public PanelCommand add(String text, CellSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   @Override
   public final PanelCommand add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   @Override
   public PanelCommand add(JComponent component, CellSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   @Override
   public final PanelCommand addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   @Override
   public final PanelCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   @Override
   public PanelCommand addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   @Override
   public PanelCommand addVerticalSeparator()
   {
      return commandsCollector.addVerticalSeparator();
   }

   @Override
   public PanelCommand addHorizontalSeparator()
   {
      return commandsCollector.addHorizontalSeparator();
   }

   @Override
   public PanelCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   public CellSpec getSpecAt(int x, int y)
   {
      CellSpec[] lineSpecs = new CellSpec[]{columnSpecs.get(x), rowSpecs.get(y)};
      int i = rowSpecsOverridingColumnSpecs.get(x, y) != null ? 0 : 1;
      return completeSpec().
         overrideWith(cellDefaultSpec).
         overrideWith(lineSpecs[i]).
         overrideWith(lineSpecs[(i + 1) % 2]).
         overrideWith(cellSpecs.get(x, y));
   }

   public final PanelCommand withSpec(CellSpec spec)
   {
      this.spec.overrideWith(spec);
      return this;
   }

   @Override
   public JComponent getComponent()
   {
      JPanel content = panel;
      content.setLayout(new GridBagLayout());
      GridBagBuilder builder = createBuilder();
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

   protected GridBagBuilder createBuilder()
   {
      return new GridBagBuilder(this, panel);
   }

   @Override
   public final CellSpec getSpec()
   {
      return spec;
   }

   @Override
   public final Integer getMaxLineLength()
   {
      return maxLineLength;
   }


   public final PanelCommand withDefaultSpec(CellSpec spec)
   {
      cellDefaultSpec.overrideWith(spec);
      return this;
   }

   public final PanelCommand withColumnSpec(int x, CellSpec spec)
   {
      for (Integer y : rowSpecsOverridingColumnSpecs.column(x).keySet())
      {
         rowSpecsOverridingColumnSpecs.remove(x, y);
      }
      CellSpec columnSpec = columnSpecs.get(x);
      if (columnSpec == null)
      {
         columnSpec = spec();
      }
      columnSpecs.put(x, columnSpec.overrideWith(spec));
      return this;
   }

   public final PanelCommand withRowSpec(int y, CellSpec spec)
   {
      for (Integer x : columnSpecs.keySet())
      {
         rowSpecsOverridingColumnSpecs.put(x, y, spec);
      }
      CellSpec rowSpec = rowSpecs.get(y);
      if (rowSpec == null)
      {
         rowSpec = spec();
      }
      rowSpecs.put(y, rowSpec.overrideWith(spec));
      return this;
   }

   public final PanelCommand withCellSpec(int x, int y, CellSpec spec)
   {
      CellSpec cellSpec = cellSpecs.get(x, y);
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
      return createCompoundBorder(createTitledBorder(title), createEmptyBorder(5, 5, 5, 5));
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
