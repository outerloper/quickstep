package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.support.*;

import static org.quickstep.GridBagToolKit.*;

public class GridContainerCommand<T extends GridContainerCommand<T>> extends AbstractComponentCommand<T>
{
   private JComponent container;

   private final CommandListSupport<T> commandListSupport = new CommandListSupport<T>(self());
   private final ContentAnchorSupport<T> contentAnchorSupport = new ContentAnchorSupport<T>(self());
   private final DecorationSupport<T> decorationSupport = new DecorationSupport<T>(self());
   private final GridSpecSupport<T> gridSpecSupport = new GridSpecSupport<T>(self());

   private ComponentFactory componentFactory;

   protected GridContainerCommand()
   {
   }

   public final T addLineBreak()
   {
      return commandListSupport.addLineBreak();
   }

   public final T addBlank()
   {
      return commandListSupport.addBlank();
   }

   public T addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public final T add(String text)
   {
      return commandListSupport.add(text);
   }

   public T add(String text, CellSpec spec)
   {
      return commandListSupport.add(text, spec);
   }

   public final T add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public T add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public final T addAll(Iterable<? extends JComponent> components)
   {
      return commandListSupport.addAll(components);
   }

   public final T addAll(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.addAll(components, spec);
   }

   public T addHeader(String title)
   {
      return commandListSupport.addHeader(title);
   }

   public T addSeparator()
   {
      return commandListSupport.addSeparator();
   }

   public T addLineSeparator()
   {
      return commandListSupport.addLineSeparator();
   }

   public T add(GridBagCommand command)
   {
      return commandListSupport.add(command);
   }

   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      ComponentFactory factory = componentFactory != null ? componentFactory : parentFactory;

      JComponent gridContainer = container != null ? container : factory.createPanel();
      gridContainer.setLayout(new GridBagLayout());

      GridSpec gridSpec = factory.getDefaultGridSpec().overrideWith(gridSpecSupport.getGridSpec());
      JComponent component = contentAnchorSupport.applyContentAnchor(gridContainer, gridSpec);
      GridBagBuilder builder = new GridBagBuilder(gridContainer, gridSpec, factory.getChildFactory());

      for (GridBagCommand command : commandListSupport)
      {
         command.apply(builder);
      }
      return decorationSupport.decorate(component, factory);
   }

   public T withGrid(GridSpec gridSpec)
   {
      return gridSpecSupport.withGrid(gridSpec);
   }

   public final T withDefault(CellSpec spec)
   {
      gridSpecSupport.withDefault(spec);
      return self();
   }

   public final T withColumn(int columnIndex, CellSpec spec)
   {
      gridSpecSupport.withColumn(columnIndex, spec);
      return self();
   }

   public final T withColumn(int columnIndex, LineSpec lineSpec)
   {
      gridSpecSupport.withColumn(columnIndex, lineSpec);
      return self();
   }

   public final T withRow(int rowIndex, CellSpec spec)
   {
      gridSpecSupport.withRow(rowIndex, spec);
      return self();
   }

   public final T withRow(int rowIndex, LineSpec lineSpec)
   {
      gridSpecSupport.withRow(rowIndex, lineSpec);
      return self();
   }

   public final T withCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpecSupport.withCell(columnIndex, rowIndex, spec);
      return self();
   }

   public final T on(JComponent container)
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
      gridSpecSupport.withOrientation(orientation);
      return self();
   }

   public final T withLineLength(Integer lineLength)
   {
      gridSpecSupport.withLineLength(lineLength);
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