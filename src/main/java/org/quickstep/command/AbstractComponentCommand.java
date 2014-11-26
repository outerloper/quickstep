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


import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.ComponentFactory;
import org.quickstep.GridBagException;
import org.quickstep.spec.CellSpec;
import org.quickstep.support.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public abstract class AbstractComponentCommand<T extends AbstractComponentCommand<T>> implements GridBagCommand
{
   private final CellSpec spec = spec();

   public abstract JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory);

   public final JComponent getComponent()
   {
      return getComponent(Direction.LEFT_TO_RIGHT, getDefaultComponentFactory());
   }

   public final T withSpec(CellSpec spec)
   {
      this.spec.overrideWith(spec);
      return self();
   }

   protected CellSpec getDefaultSpec(Direction parentDirection)
   {
      return spec();
   }

   public final CellSpec getSpec(Direction parentDirection)
   {
      return getDefaultSpec(parentDirection).overrideWith(spec);
   }

   @Override
   public final void apply(GridBagBuilder builder)
   {
      Direction direction = builder.isHorizontal() ? Direction.LEFT_TO_RIGHT : Direction.TOP_TO_BOTTOM;
      JComponent component = getComponent(direction, builder.getComponentFactory());
      if (component != null)
      {
         try
         {
            builder.moveToFreeCell();
            builder.placeComponent(component, getSpec(direction));
         }
         catch (GridBagException e)
         {
            logger.log(Level.WARNING, "No place for " + DebugSupport.objectId(component) + ".", e);
         }
      }
   }

   @SuppressWarnings("unchecked")
   protected final T self()
   {
      return (T) this;
   }
}
