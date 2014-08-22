package org.quickstep;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import static org.quickstep.GridBagToolKit.*;

public class GridContainerCommand<T extends GridContainerCommand<T>> extends CellCommand<T> implements GridSpecBuilder<T>
{
   private JComponent container;

   private final CommandsCollector<T> commandsCollector = new CommandsCollector<T>(self());
   private final ContentAnchorSupport<T> contentAnchorSupport = new ContentAnchorSupport<T>(self());
   private final DecorationSupport<T> decorationSupport = new DecorationSupport<T>(self());

   private final GridSpec gridSpec = new GridSpec();

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

      JComponent component = contentAnchorSupport.applyContentAnchor(gridContainer, gridSpec);

      GridBagBuilder builder = usedFactory.createGridBagBuilder(gridContainer, gridSpec, usedFactory.getChildFactory());
      for (GridBagCommand command : commandsCollector)
      {
         command.apply(builder);
      }

      return decorationSupport.decorate(component, usedFactory);
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