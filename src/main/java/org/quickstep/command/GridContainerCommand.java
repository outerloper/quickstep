package org.quickstep.command;

/*
 * ---------------------------------------------------------------------
 * Quickstep
 * ------
 * Copyright (C) 2014 Konrad Sacala
 * ------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * ---------------------------------------------------------------------
 */


import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.ComponentFactory;
import org.quickstep.spec.*;
import org.quickstep.support.*;

import static org.quickstep.GridBagToolKit.*;

public abstract class GridContainerCommand<C extends JComponent, T extends GridContainerCommand<C, T>> extends AbstractComponentCommand<T>
{
   private final CommandListSupport<T> commandListSupport = new CommandListSupport<T>(self());
   private final ContentAnchorSupport<T> contentAnchorSupport = new ContentAnchorSupport<T>(self());
   private final DecorationSupport<T> decorationSupport = new DecorationSupport<T>(self());
   private final GridSpecSupport<T> gridSpecSupport = new GridSpecSupport<T>(self());

   private ComponentFactory componentFactory;
   private C container;

   protected GridContainerCommand()
   {
   }

   public T addLineBreak()
   {
      return commandListSupport.addLineBreak();
   }

   public T addBlank()
   {
      return commandListSupport.addBlank();
   }

   public T addBlank(CellSpec spec)
   {
      return commandListSupport.addBlank(spec);
   }

   public T add(String label)
   {
      return commandListSupport.add(label);
   }

   public T add(String label, CellSpec spec)
   {
      return commandListSupport.add(label, spec);
   }

   public T add(String label, JComponent component)
   {
      return commandListSupport.add(label, component);
   }

   public T add(String label, JComponent component, CellSpec spec)
   {
      return commandListSupport.add(label, component, spec);
   }

   public T add(String label, GridBagCommand command)
   {
      return commandListSupport.add(label, command);
   }

   public T add(JComponent component)
   {
      return commandListSupport.add(component);
   }

   public T add(JComponent component, CellSpec spec)
   {
      return commandListSupport.add(component, spec);
   }

   public T add(Iterable<? extends JComponent> components)
   {
      return commandListSupport.add(components);
   }

   public T add(Iterable<? extends JComponent> components, CellSpec spec)
   {
      return commandListSupport.add(components, spec);
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
   public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
   {
      ComponentFactory factory = componentFactory != null ? componentFactory : parentFactory;

      C gridContainer = container != null ? container : createDefaultContainer(factory);
      gridContainer.setLayout(new GridBagLayout());

      GridSpec gridSpec = factory.getGridSpec().overrideWith(gridSpecSupport.getGridSpec());
      JComponent component = createContentAnchorSupport(factory).applyContentAnchor(gridContainer, gridSpec);
      SizeGroupsSupport sizeGroupsSupport = new SizeGroupsSupport();
      GridBagBuilder builder = new GridBagBuilder(gridContainer, gridSpec, factory.createChildFactory(), sizeGroupsSupport);

      for (GridBagCommand command : commandListSupport)
      {
         command.apply(builder);
      }
      sizeGroupsSupport.process();
      return decorationSupport.decorate(component, factory);
   }

   private ContentAnchorSupport<ContentAnchorSupport> createContentAnchorSupport(ComponentFactory factory)
   {
      ContentAnchorSupport<ContentAnchorSupport> result = ContentAnchorSupport.createDefault();
      result.
         withContentAnchor(factory.getContentAnchorX(), factory.getContentAnchorY()).
         withContentAnchor(contentAnchorSupport.getContentAnchorX(), contentAnchorSupport.getContentAnchorY());
      return result;
   }

   protected abstract C createDefaultContainer(ComponentFactory factory);

   public T withGrid(GridSpec gridSpec)
   {
      return gridSpecSupport.withGrid(gridSpec);
   }

   public T withDefault(CellSpec spec)
   {
      return gridSpecSupport.withDefault(spec);
   }

   public T withColumn(int columnIndex, CellSpec spec)
   {
      return gridSpecSupport.withColumn(columnIndex, spec);
   }

   public T withColumn(int columnIndex, LineSpec lineSpec)
   {
      return gridSpecSupport.withColumn(columnIndex, lineSpec);
   }

   public T withRow(int rowIndex, CellSpec spec)
   {
      return gridSpecSupport.withRow(rowIndex, spec);
   }

   public T withRow(int rowIndex, LineSpec lineSpec)
   {
      return gridSpecSupport.withRow(rowIndex, lineSpec);
   }

   public T withCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      return gridSpecSupport.withCell(columnIndex, rowIndex, spec);
   }

   public T on(C container)
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

   public T withDirection(Direction direction)
   {
      return gridSpecSupport.withDirection(direction);
   }

   public T withLineLength(Integer lineLength)
   {
      return gridSpecSupport.withLineLength(lineLength);
   }

   public T withBorder()
   {
      return decorationSupport.withBorder();
   }

   public T withBorder(String title)
   {
      return decorationSupport.withBorder(title);
   }

   public T withBorder(Border innerBorder, Border... outerBorders)
   {
      return decorationSupport.withBorder(innerBorder, outerBorders);
   }

   public T withScroll()
   {
      return decorationSupport.withScroll();
   }

   public T withScroll(JScrollPane scroll)
   {
      return decorationSupport.withScroll(scroll);
   }

   public T withComponentFactory(ComponentFactory componentFactory)
   {
      this.componentFactory = componentFactory;
      return self();
   }
}