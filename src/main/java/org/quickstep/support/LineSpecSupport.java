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


import org.quickstep.spec.CellSpec;
import org.quickstep.spec.LineSpec;

import static org.quickstep.GridBagToolKit.*;

public class LineSpecSupport<T>
{
   private final T owner;
   private final LineSpec lineSpec = lineSpec();

   public LineSpecSupport(T owner)
   {
      this.owner = owner;
   }

   public T withDefault(CellSpec spec)
   {
      lineSpec.withDefault(spec);
      return owner;
   }

   public T withCell(int i, CellSpec spec)
   {
      lineSpec.withCell(i, spec);
      return owner;
   }

   public T withLine(LineSpec spec)
   {
      lineSpec.overrideWith(spec);
      return owner;
   }

   public LineSpec getLineSpec()
   {
      return lineSpec;
   }
}
