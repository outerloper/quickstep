package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class GridContainerCommand<T extends GridContainerCommand<T>> extends CellCommand<T> implements GridSpecBuilder<T>
{
   private JComponent container;

   private final CommandsCollector<T> commandsCollector = new CommandsCollector<T>(self());

   private final GridSpec gridSpec = new GridSpec();
   private Decoration<Border> borderDecoration;
   private Decoration<JScrollPane> scrollDecoration;

   private AX anchorX;
   private AY anchorY;

   private ComponentFactory componentFactory;

   protected GridContainerCommand()
   {
      this(spec().withGap(5).withAnchorX(AX.LEFT));
   }

   protected GridContainerCommand(CellSpec defaultSpec)
   {
      specifyDefault(defaultSpec);
      specifyRow(0, spec().withInsetTop(0));
      specifyColumn(0, spec().withInsetLeft(0));
   }

   public final T addLineBreak()
   {
      return commandsCollector.addLineBreak();
   }

   public final T addBlank()
   {
      return commandsCollector.addBlank();
   }

   public T addBlank(CellSpec spec)
   {
      return commandsCollector.addBlank(spec);
   }

   public final T add(String text)
   {
      return commandsCollector.add(text);
   }

   public T add(String text, CellSpec spec)
   {
      return commandsCollector.add(text, spec);
   }

   public final T add(JComponent component)
   {
      return commandsCollector.add(component);
   }

   public T add(JComponent component, CellSpec spec)
   {
      return commandsCollector.add(component, spec);
   }

   public final T addAll(Iterable<? extends JComponent> components)
   {
      return commandsCollector.addAll(components);
   }

   public final T addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsCollector.addAll(components, spec);
   }

   public T addHeader(String title)
   {
      return commandsCollector.addHeader(title);
   }

   public T addSeparator()
   {
      return commandsCollector.addSeparator();
   }

   public T addLineSeparator()
   {
      return commandsCollector.addLineSeparator();
   }

   public T add(GridBagCommand command)
   {
      return commandsCollector.add(command);
   }

   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      ComponentFactory usedFactory = componentFactory != null ? componentFactory : parentFactory;

      JComponent gridContainer = container != null ? container : usedFactory.createPanel();
      gridContainer.setLayout(new GridBagLayout());

      JComponent component = applyContentAnchor(gridContainer);

      GridBagBuilder builder = usedFactory.createGridBagBuilder(gridContainer, gridSpec, usedFactory.getChildFactory());
      for (GridBagCommand command : commandsCollector)
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

   public T specifyGrid(GridSpec gridSpec)
   {
      this.gridSpec.overrideWith(gridSpec);
      return self();
   }

   @Override
   public final T specifyDefault(CellSpec spec)
   {
      gridSpec.specifyDefault(spec);
      return self();
   }

   @Override
   public final T specifyColumn(int columnIndex, CellSpec spec)
   {
      gridSpec.specifyColumn(columnIndex, spec);
      return self();
   }

   @Override
   public final T specifyColumn(int columnIndex, LineSpec lineSpec)
   {
      gridSpec.specifyColumn(columnIndex, lineSpec);
      return self();
   }

   @Override
   public final T specifyRow
      (int rowIndex, CellSpec spec)
   {
      gridSpec.specifyRow(rowIndex, spec);
      return self();
   }

   @Override
   public final T specifyRow(int rowIndex, LineSpec lineSpec)
   {
      gridSpec.specifyRow(rowIndex, lineSpec);
      return self();
   }

   @Override
   public final T specifyCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpec.specifyCell(columnIndex, rowIndex, spec);
      return self();
   }

   public final T with(JComponent container)
   {
      this.container = container;
      return self();
   }

   public T withContentAnchor(AX anchorX, AY anchorY)
   {
      return withContentAnchorX(anchorX).withContentAnchorY(anchorY);
   }

   public T withContentAnchor(A anchorXAndY)
   {
      if (A.CENTER.equals(anchorXAndY))
      {
         return withContentAnchor(AX.CENTER, AY.CENTER);
      }
      return withContentAnchor(AX.BOTH, AY.BOTH);
   }

   public T withContentAnchorX(AX anchorX)
   {
      this.anchorX = anchorX;
      return self();
   }

   public T withContentAnchorY(AY anchorY)
   {
      this.anchorY = anchorY;
      return self();
   }

   public final T withOrientation(Orientation orientation)
   {
      gridSpec.withOrientation(orientation);
      return self();
   }

   public final T withLineLength(Integer lineLength)
   {
      gridSpec.withLineLength(lineLength);
      return self();
   }

   public final T withBorder()
   {
      return withBorderDecoration(new DefaultBorderDecoration(null));
   }

   public final T withBorder(String title)
   {
      return withBorderDecoration(new DefaultBorderDecoration(title));
   }

   private T withBorderDecoration(DefaultBorderDecoration borderDecoration)
   {
      this.borderDecoration = borderDecoration;
      return self();
   }

   public final T withBorder(Border innerBorder, Border... outerBorders)
   {
      Border border = innerBorder;
      for (Border outerBorder : outerBorders)
      {
         border = createCompoundBorder(outerBorder, border);
      }
      borderDecoration = new CustomBorderDecoration(border);
      return self();
   }

   public final T withScroll()
   {
      return withScrollDecoration(new DefaultScrollDecoration());
   }

   public final T withScroll(JScrollPane scroll)
   {
      return withScrollDecoration(new CustomScrollDecoration(scroll));
   }

   private T withScrollDecoration(Decoration<JScrollPane> scrollDecoration)
   {
      this.scrollDecoration = scrollDecoration;
      return self();
   }

   public final T withComponentFactory(ComponentFactory componentFactory)
   {
      this.componentFactory = componentFactory;
      return self();
   }

   // TODO extract decorator as a separate component class, also consider rename back CommandCollector to CommandsCollectorComponent
   // TODO think about name other than component - trait, ability, _support_, helper...
   // TODO extract GridSpecBuilderComponent, let ComponentCommand not override
   // TODO think about doing the same with LineSpecBuilder
   private JComponent applyContentAnchor(JComponent panel)
   {
      JComponent result = panel;

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