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


import javax.swing.*;

import org.quickstep.spec.CellSpec;

import static org.quickstep.GridBagToolKit.*;

public class LineSeparatorCommand implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public LineSeparatorCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      boolean horizontal = builder.isHorizontal();
      JComponent separator = builder.getComponentFactory().createSeparator(horizontal ? Direction.LEFT_TO_RIGHT : Direction.TOP_TO_BOTTOM);
      CellSpec spec = spec();

      if (horizontal)
      {
         spec.withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder();
      }
      else
      {
         spec.withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder();
      }

      line().add(separator, spec.overrideWith(cellSpec)).apply(builder);
   }
}
