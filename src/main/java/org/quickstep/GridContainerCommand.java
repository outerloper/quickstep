package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static org.quickstep.GridBagToolKit.*;

public class GridContainerCommand<T extends GridContainerCommand<T>> extends CellCommand<T>
{
   private JComponent container;

   private final CommandsBuilder<T> commandsBuilder = new CommandsBuilder<T>(self());
   private final ContentAnchorSupport<T> contentAnchorSupport = new ContentAnchorSupport<T>(self());
   private final DecorationSupport<T> decorationSupport = new DecorationSupport<T>(self());
   private final GridSpecBuilder<T> gridSpecBuilder = new GridSpecBuilder<T>(self());

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
      return commandsBuilder.addLineBreak();
   }

   public final T addBlank()
   {
      return commandsBuilder.addBlank();
   }

   public T addBlank(CellSpec spec)
   {
      return commandsBuilder.addBlank(spec);
   }

   public final T add(String text)
   {
      return commandsBuilder.add(text);
   }

   public T add(String text, CellSpec spec)
   {
      return commandsBuilder.add(text, spec);
   }

   public final T add(JComponent component)
   {
      return commandsBuilder.add(component);
   }

   public T add(JComponent component, CellSpec spec)
   {
      return commandsBuilder.add(component, spec);
   }

   public final T addAll(Iterable<? extends JComponent> components)
   {
      return commandsBuilder.addAll(components);
   }

   public final T addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandsBuilder.addAll(components, spec);
   }

   public T addHeader(String title)
   {
      return commandsBuilder.addHeader(title);
   }

   public T addSeparator()
   {
      return commandsBuilder.addSeparator();
   }

   public T addLineSeparator()
   {
      return commandsBuilder.addLineSeparator();
   }

   public T add(GridBagCommand command)
   {
      return commandsBuilder.add(command);
   }

   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      ComponentFactory factory = componentFactory != null ? componentFactory : parentFactory;

      JComponent gridContainer = container != null ? container : factory.createPanel();
      gridContainer.setLayout(new GridBagLayout());

      GridSpec gridSpec = factory.getDefaultGridSpec().overrideWith(gridSpecBuilder.getGridSpec());
      JComponent component = contentAnchorSupport.applyContentAnchor(gridContainer, gridSpec);
      GridBagBuilder builder = new GridBagBuilder(gridContainer, gridSpec, factory.getChildFactory());

      for (GridBagCommand command : commandsBuilder)
      {
         command.apply(builder);
      }
      return decorationSupport.decorate(component, factory);
   }

   public T specifyGrid(GridSpec gridSpec)
   {
      return gridSpecBuilder.overrideWith(gridSpec);
   }

   public final T specifyDefault(CellSpec spec)
   {
      gridSpecBuilder.specifyDefault(spec);
      return self();
   }

   public final T specifyColumn(int columnIndex, CellSpec spec)
   {
      gridSpecBuilder.specifyColumn(columnIndex, spec);
      return self();
   }

   public final T specifyColumn(int columnIndex, LineSpec lineSpec)
   {
      gridSpecBuilder.specifyColumn(columnIndex, lineSpec);
      return self();
   }

   public final T specifyRow
      (int rowIndex, CellSpec spec)
   {
      gridSpecBuilder.specifyRow(rowIndex, spec);
      return self();
   }

   public final T specifyRow(int rowIndex, LineSpec lineSpec)
   {
      gridSpecBuilder.specifyRow(rowIndex, lineSpec);
      return self();
   }

   public final T specifyCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpecBuilder.specifyCell(columnIndex, rowIndex, spec);
      return self();
   }

   public final T with(JComponent container)
   {
      this.container = container;
      return self();
   }

   public T withContentAnchor(AX anchorX, AY anchorY)
   {
      return contentAnchorSupport.withContentAnchor(anchorX, anchorY);
   }

   public T withContentAnchor(A anchorXAndY)
   {
      return contentAnchorSupport.withContentAnchor(anchorXAndY);
   }

   public T withContentAnchorX(AX anchorX)
   {
      return contentAnchorSupport.withContentAnchorX(anchorX);
   }

   public T withContentAnchorY(AY anchorY)
   {
      return contentAnchorSupport.withContentAnchorY(anchorY);
   }

   public final T withOrientation(Orientation orientation)
   {
      gridSpecBuilder.withOrientation(orientation);
      return self();
   }

   public final T withLineLength(Integer lineLength)
   {
      gridSpecBuilder.withLineLength(lineLength);
      return self();
   }

   public final T withBorder()
   {
      return decorationSupport.withBorder();
   }

   public final T withBorder(String title)
   {
      return decorationSupport.withBorder(title);
   }

   public final T withBorder(Border innerBorder, Border... outerBorders)
   {
      return decorationSupport.withBorder(innerBorder, outerBorders);
   }

   public final T withScroll()
   {
      return decorationSupport.withScroll();
   }

   public final T withScroll(JScrollPane scroll)
   {
      return decorationSupport.withScroll(scroll);
   }

   public final T withComponentFactory(ComponentFactory componentFactory)
   {
      this.componentFactory = componentFactory;
      return self();
   }
}