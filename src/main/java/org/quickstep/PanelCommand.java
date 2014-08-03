package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class PanelCommand implements CellCommand, GridBagCommandsCollector<PanelCommand>, GridSpecBuilder<PanelCommand>
{
   private JPanel panel;
   private CellSpec spec = spec();

   private final GridBagCommandsCollectorComponent<PanelCommand> commandsCollector = new GridBagCommandsCollectorComponent<PanelCommand>(this);

   private GridSpec gridSpec = new GridSpec();
   private Border border;
   private JScrollPane scroll;

   protected PanelCommand(JPanel panel)
   {
      this(panel, spec().withGap(5).withAnchorX(AX.LEFT));
   }

   protected PanelCommand(JPanel panel, CellSpec defaultSpec)
   {
      this.panel = panel;

      specifyDefault(defaultSpec);
      specifyRow(0, spec().withInsetTop(0));
      specifyColumn(0, spec().withInsetLeft(0));
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
      return completeSpec().overrideWith(gridSpec.getSpecAt(x, y));
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
      return new GridBagBuilder(gridSpec, panel);
   }

   @Override
   public final CellSpec getSpec()
   {
      return spec;
   }

   @Override
   public final PanelCommand specifyDefault(CellSpec spec)
   {
      gridSpec.specifyDefault(spec);
      return this;
   }

   @Override
   public final PanelCommand specifyColumn(int x, CellSpec spec)
   {
      gridSpec.specifyColumn(x, spec);
      return this;
   }

   @Override
   public final PanelCommand specifyRow
      (int y, CellSpec spec)
   {
      gridSpec.specifyRow(y, spec);
      return this;
   }

   @Override
   public final PanelCommand specifyCell(int x, int y, CellSpec spec)
   {
      gridSpec.specifyCell(x, y, spec);
      return this;
   }

   public final PanelCommand withOrientation(Orientation value)
   {
      gridSpec.withOrientation(value);
      return this;
   }

   public final PanelCommand withMaxLineLength(Integer value)
   {
      gridSpec.withMaxLineLength(value);
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
