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

import org.quickstep.GridBagException;
import org.quickstep.spec.CellSpec;

import static org.quickstep.GridBagToolKit.*;

public class HeaderCommand implements GridBagCommand
{
   private final CellSpec cellSpec = spec();
   private final String text;

   public HeaderCommand(String text)
   {
      this.text = text;
   }

   public HeaderCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         JComponent component = builder.getComponentFactory().createHeader(text, willBePlacedInFirstRow(builder));
         CellSpec spec = spec().withAnchorX(AX.BOTH);
         GridBagCommand command;

         if (builder.isHorizontal())
         {
            command = line().add(component(component).withSpec(spec.withGridWidthRemainder().overrideWith(cellSpec)));
         }
         else
         {
            command = component(component).withSpec(spec.overrideWith(cellSpec));
         }

         command.apply(builder);
      }
      catch (GridBagException e)
      {
         logger.log(Level.WARNING, "No place for header component.", e);
      }
   }

   private boolean willBePlacedInFirstRow(GridBagBuilder builder) throws GridBagException
   {
      if (builder.isHorizontal())
      {
         return builder.isEmpty();
      }
      else
      {
         builder.moveToFreeCell();
         return builder.getCurrentRowIndex() == 0;
      }
   }
}
