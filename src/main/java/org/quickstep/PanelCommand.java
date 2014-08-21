package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class PanelCommand extends CellCommand<PanelCommand> implements GridSpecBuilder<PanelCommand>
{
   private JPanel panel;

   private final SeqCommand commands = seq();

   private final GridSpec gridSpec = new GridSpec();
   private Decoration<Border> borderDecoration;
   private Decoration<JScrollPane> scrollDecoration;

   private AX anchorX;
   private AY anchorY;

   private ComponentFactory componentFactory;

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

   public final PanelCommand addLineBreak()
   {
      commands.addLineBreak();
      return this;
   }

   public final PanelCommand addBlank()
   {
      commands.addBlank();
      return this;
   }

   public PanelCommand addBlank(CellSpec spec)
   {
      commands.addBlank(spec);
      return this;
   }

   public final PanelCommand add(String text)
   {
      commands.add(text);
      return this;
   }

   public PanelCommand add(String text, CellSpec spec)
   {
      commands.add(text, spec);
      return this;
   }

   public final PanelCommand add(JComponent component)
   {
      commands.add(component);
      return this;
   }

   public PanelCommand add(JComponent component, CellSpec spec)
   {
      commands.add(component, spec);
      return this;
   }

   public final PanelCommand addAll(Iterable<? extends JComponent> components)
   {
      commands.addAll(components);
      return this;
   }

   public final PanelCommand addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      commands.addAll(components, spec);
      return this;
   }

   public PanelCommand addHeader(String title)
   {
      commands.addHeader(title);
      return this;
   }

   public PanelCommand addSeparator()
   {
      commands.addSeparator();
      return this;
   }

   public PanelCommand addLineSeparator()
   {
      commands.addLineSeparator();
      return this;
   }

   public PanelCommand add(GridBagCommand command)
   {
      commands.add(command);
      return this;
   }

   @Override
   public JComponent getComponent(Orientation orientation, ComponentFactory factory)
   {
      ComponentFactory usedFactory = componentFactory != null ? componentFactory : factory;

      JPanel gridContainer = panel != null ? panel : usedFactory.createPanel();
      gridContainer.setLayout(new GridBagLayout());

      JPanel component = applyContentAnchor(gridContainer);

      GridBagBuilder builder = usedFactory.createGridBagBuilder(gridContainer, gridSpec, usedFactory.getChildFactory());
      for (GridBagCommand command : commands)
      {
         command.apply(builder);
      }

      if (scrollDecoration != null)
      {
         JScrollPane scroll = scrollDecoration.getDecoration(usedFactory);
         scroll.setViewportView(component);
         component = new ResizablePanel();
         component.add(scroll);
      }
      if (borderDecoration != null)
      {
         component.setBorder(borderDecoration.getDecoration(usedFactory));
      }
      return component;
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

   public final PanelCommand withBorder()
   {
      return withBorderDecoration(new DefaultBorderDecoration(null));
   }

   public final PanelCommand withBorder(String title)
   {
      return withBorderDecoration(new DefaultBorderDecoration(title));
   }

   private PanelCommand withBorderDecoration(DefaultBorderDecoration borderDecoration)
   {
      this.borderDecoration = borderDecoration;
      return this;
   }

   public final PanelCommand withBorder(Border innerBorder, Border... outerBorders)
   {
      Border border = innerBorder;
      for (Border outerBorder : outerBorders)
      {
         border = createCompoundBorder(outerBorder, border);
      }
      borderDecoration = new CustomBorderDecoration(border);
      return this;
   }

   public final PanelCommand withScroll()
   {
      return withScrollDecoration(new DefaultScrollDecoration());
   }

   public final PanelCommand withScroll(JScrollPane scroll)
   {
      return withScrollDecoration(new CustomScrollDecoration(scroll));
   }

   private PanelCommand withScrollDecoration(Decoration<JScrollPane> scrollDecoration)
   {
      this.scrollDecoration = scrollDecoration;
      return this;
   }

   public final PanelCommand withComponentFactory(ComponentFactory componentFactory)
   {
      this.componentFactory = componentFactory;
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

   static interface Decoration<D>
   {
      D getDecoration(ComponentFactory componentFactory);
   }

   static class DefaultBorderDecoration implements Decoration<Border>
   {
      private String title;

      DefaultBorderDecoration(String title)
      {
         this.title = title;
      }

      @Override
      public Border getDecoration(ComponentFactory componentFactory)
      {
         return componentFactory.createBorder(title);
      }
   }

   static class CustomBorderDecoration implements Decoration<Border>
   {
      private Border border;

      CustomBorderDecoration(Border border)
      {
         this.border = border;
      }

      @Override
      public Border getDecoration(ComponentFactory componentFactory)
      {
         return border;
      }
   }

   static class DefaultScrollDecoration implements Decoration<JScrollPane>
   {
      @Override
      public JScrollPane getDecoration(ComponentFactory componentFactory)
      {
         return componentFactory.createScrollPane();
      }
   }

   static class CustomScrollDecoration implements Decoration<JScrollPane>
   {
      private JScrollPane scrollPane;

      CustomScrollDecoration(JScrollPane scrollPane)
      {
         this.scrollPane = scrollPane;
      }

      @Override
      public JScrollPane getDecoration(ComponentFactory componentFactory)
      {
         return scrollPane;
      }
   }
}