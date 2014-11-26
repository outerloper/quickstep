package org.quickstep.spec;

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


import java.util.*;

import static org.quickstep.GridBagToolKit.spec;

public class LineSpec
{
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> cellsSpecs = new TreeMap<Integer, CellSpec>();

   public LineSpec()
   {
   }

   public LineSpec withDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   public LineSpec withCell(int i, CellSpec spec)
   {
      CellSpec cellSpec = cellsSpecs.get(i);
      if (cellSpec == null)
      {
         cellSpec = spec();
      }
      cellsSpecs.put(i, cellSpec.overrideWith(spec));
      return this;
   }

   public CellSpec getDefaultSpec()
   {
      return defaultSpec.derive();
   }

   public Map<Integer, CellSpec> getSpecifiedCells()
   {
      Map<Integer, CellSpec> result = new TreeMap<Integer, CellSpec>();
      for (Map.Entry<Integer, CellSpec> entry : cellsSpecs.entrySet())
      {
         result.put(entry.getKey(), entry.getValue().derive());
      }
      return result;
   }

   public LineSpec overrideWith(LineSpec that)
   {
      if (that == null)
      {
         return this;
      }
      withDefault(that.defaultSpec);
      for (Map.Entry<Integer, CellSpec> entry : that.cellsSpecs.entrySet())
      {
         withCell(entry.getKey(), entry.getValue());
      }
      return this;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof LineSpec)) return false;

      LineSpec spec = (LineSpec) o;

      if (!cellsSpecs.equals(spec.cellsSpecs)) return false;
      if (!defaultSpec.equals(spec.defaultSpec)) return false;
      //noinspection SimplifiableIfStatement
      return true;
   }

   @Override
   public int hashCode()
   {
      int result = defaultSpec.hashCode();
      result = 31 * result + cellsSpecs.hashCode();
      return result;
   }

   @Override
   public String toString()
   {
      String result = "LineSpec{\n " + defaultSpec + "\n";
      for (Map.Entry<Integer, CellSpec> entry : cellsSpecs.entrySet())
      {
         result += " " + entry.getKey() + ": " + entry.getValue() + "\n";
      }
      return result + "}";
   }
}
