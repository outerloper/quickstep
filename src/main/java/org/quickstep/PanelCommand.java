package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class PanelCommand extends CellCommand<PanelCommand> implements GridBagCommandsCollector<PanelCommand>, GridSpecBuilder<PanelCommand>
{
   private JPanel panel;

   private final GridBagCommandsCollectorComponent<PanelCommand> commandsCollector = new GridBagCommandsCollectorComponent<PanelCommand>(this);

   protected GridSpec gridSpec = new GridSpec();
   private Border border;
   private JScrollPane scroll;

   private AX anchorX;
   private AY anchorY;

   protected PanelCommand()
   {
      this(spec().withGap(5).withAnchorX(AX.LEFT));
   }

   protected PanelCommand(CellSpec defaultSpec)
   {
      specifyDefault(defaultSpec);
      specifyRow(0, spec().withInsetTop(0));
      specifyColumn(0, spec().withInsetLeft(0));
   }

   @Override
   public final PanelCommand addLineBreak()
   {
      return commandsCollector.addLineBreak();
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
   public PanelCommand addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   @Override
   public PanelCommand addLineSeparator()
   {
      return commandsCollector.addLineSeparator();
   }

   @Override
   public PanelCommand add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   @Override
   public JComponent getComponent(Orientation orientation)
   {
      JPanel gridContainer = panel != null ? panel : new ResizablePanel();
      gridContainer.setLayout(new GridBagLayout());

      JPanel component = applyContentAnchor(gridContainer);

      GridBagBuilder builder = createBuilder(gridContainer);
      for (GridBagCommand command : commandsCollector)
      {
         command.apply(builder);
      }

      if (scroll != null)
      {
         scroll.setViewportView(component);
         component = new ResizablePanel();
         component.add(scroll);
      }
      if (border != null)
      {
         component.setBorder(border);
      }
      return component;
   }

   protected GridBagBuilder createBuilder(JPanel panel)
   {
      return new GridBagBuilder(gridSpec, panel);
   }

   public PanelCommand specifyGrid(GridSpec gridSpec)
   {
      this.gridSpec.overrideWith(gridSpec);
      return this;
   }

   @Override
   public final PanelCommand specifyDefault(CellSpec spec)
   {
      gridSpec.specifyDefault(spec);
      return this;
   }

   @Override
   public final PanelCommand specifyColumn(int columnIndex, CellSpec spec)
   {
      gridSpec.specifyColumn(columnIndex, spec);
      return this;
   }

   @Override
   public final PanelCommand specifyColumn(int columnIndex, LineSpec lineSpec)
   {
      gridSpec.specifyColumn(columnIndex, lineSpec);
      return this;
   }

   @Override
   public final PanelCommand specifyRow
      (int rowIndex, CellSpec spec)
   {
      gridSpec.specifyRow(rowIndex, spec);
      return this;
   }

   @Override
   public final PanelCommand specifyRow(int rowIndex, LineSpec lineSpec)
   {
      gridSpec.specifyRow(rowIndex, lineSpec);
      return this;
   }

   @Override
   public final PanelCommand specifyCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpec.specifyCell(columnIndex, rowIndex, spec);
      return this;
   }

   public final PanelCommand with(JPanel panel)
   {
      this.panel = panel;
      return this;
   }

   public PanelCommand withContentAnchor(AX anchorX, AY anchorY)
   {
      return withContentAnchorX(anchorX).withContentAnchorY(anchorY);
   }

   public PanelCommand withContentAnchor(A anchorXAndY)
   {
      if (A.CENTER.equals(anchorXAndY))
      {
         return withContentAnchor(AX.CENTER, AY.CENTER);
      }
      return withContentAnchor(AX.BOTH, AY.BOTH);
   }

   public PanelCommand withContentAnchorX(AX anchorX)
   {
      this.anchorX = anchorX;
      return this;
   }

   public PanelCommand withContentAnchorY(AY anchorY)
   {
      this.anchorY = anchorY;
      return this;
   }

   public final PanelCommand withOrientation(Orientation orientation)
   {
      gridSpec.withOrientation(orientation);
      return this;
   }

   public final PanelCommand withLineLength(Integer lineLength)
   {
      gridSpec.withLineLength(lineLength);
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

   private JPanel applyContentAnchor(JPanel panel)
   {
      JPanel result = panel;

      Integer contentX = null;
      if (anchorX == null)
      {
         anchorX = AX.CENTER;
      }
      switch (anchorX)
      {
         case LEFT:
            contentX = 0;
            break;
         case RIGHT:
            contentX = 1;
            break;
         case BOTH:
            gridSpec.specifyDefault(spec().withWeightX(1.0).withAnchorX(AX.BOTH));
            break;
      }
      Integer contentY = null;
      if (anchorY == null)
      {
         anchorY = AY.CENTER;
      }
      switch (anchorY)
      {
         case TOP:
            contentY = 0;
            break;
         case BOTTOM:
            contentY = 1;
            break;
         case BOTH:
            gridSpec.specifyDefault(spec().withWeightY(1.0).withAnchorY(AY.BOTH));
            break;
      }
      if (contentX != null || contentY != null)
      {
         int fillX = 0;
         CellSpec fillSpec = spec();
         if (contentX != null)
         {
            fillSpec.withWeightX(1.0);
            fillX = 1 - contentX;
         }
         else
         {
            contentX = 0;
         }
         int fillY = 0;
         if (contentY != null)
         {
            fillSpec.withWeightY(1.0);
            fillY = 1 - contentY;
         }
         else
         {
            contentY = 0;
         }
         result = new JPanel(new GridBagLayout());
         CellSpec contentSpec = spec();
         if (anchorX == AX.BOTH)
         {
            contentSpec.withWeightX(1.0).withAnchorX(AX.BOTH);
         }
         if (anchorY == AY.BOTH)
         {
            contentSpec.withWeightY(1.0).withAnchorY(AY.BOTH);
         }
         result.add(new JLabel(), fillSpec.toConstraints(fillX, fillY));
         result.add(panel, contentSpec.toConstraints(contentX, contentY));
      }
      return result;
   }
}
