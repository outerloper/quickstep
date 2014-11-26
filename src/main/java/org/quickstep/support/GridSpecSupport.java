package org.quickstep.support;

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


import org.quickstep.spec.*;

import static org.quickstep.GridBagToolKit.*;

public class GridSpecSupport<T>
{
   private final T owner;
   private final GridSpec gridSpec = new GridSpec();

   public GridSpecSupport(T owner)
   {
      this.owner = owner;
   }

   public T withDefault(CellSpec spec)
   {
      gridSpec.withDefault(spec);
      return owner;
   }

   public T withColumn(int columnIndex, CellSpec spec)
   {
      gridSpec.withColumn(columnIndex, spec);
      return owner;
   }

   public T withColumn(int columnIndex, LineSpec spec)
   {
      gridSpec.withColumn(columnIndex, spec);
      return owner;
   }

   public T withRow(int rowIndex, CellSpec spec)
   {
      gridSpec.withRow(rowIndex, spec);
      return owner;
   }

   public T withRow(int rowIndex, LineSpec spec)
   {
      gridSpec.withRow(rowIndex, spec);
      return owner;
   }

   public T withCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpec.withCell(columnIndex, rowIndex, spec);
      return owner;
   }

   public T withDirection(Direction direction)
   {
      gridSpec.withDirection(direction);
      return owner;
   }

   public T withLineLength(Integer lineLength)
   {
      gridSpec.withLineLength(lineLength);
      return owner;
   }

   public T withGrid(GridSpec spec)
   {
      gridSpec.overrideWith(spec);
      return owner;
   }

   public GridSpec getGridSpec()
   {
      return gridSpec;
   }
}
