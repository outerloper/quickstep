package org.quickstep;

import java.util.*;

import static org.quickstep.GridBagToolKit.spec;

public class LineSpec
{
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> cellsSpecs = new TreeMap<Integer, CellSpec>();

   public LineSpec()
   {
   }

   public LineSpec specifyDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   public LineSpec specifyCell(int i, CellSpec spec)
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
      specifyDefault(that.defaultSpec);
      for (Map.Entry<Integer, CellSpec> entry : that.cellsSpecs.entrySet())
      {
         specifyCell(entry.getKey(), entry.getValue());
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
