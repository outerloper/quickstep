package org.quickstep;

import java.util.*;

import static org.quickstep.GridBagToolKit.spec;

public class LineSpec implements LineSpecBuilder<LineSpec>
{
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> cellsSpecs = new TreeMap<Integer, CellSpec>();

   public LineSpec()
   {
   }

   @Override
   public LineSpec specifyDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   @Override
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
      Map<Integer,CellSpec> result = new TreeMap<Integer, CellSpec>();
      for (Map.Entry<Integer, CellSpec> entry : cellsSpecs.entrySet())
      {
         result.put(entry.getKey(), entry.getValue().derive());
      }
      return result;
   }
}
