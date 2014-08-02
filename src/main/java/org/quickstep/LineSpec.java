package org.quickstep;

import java.util.*;

import static org.quickstep.GridBagToolKit.spec;

public class LineSpec implements LineSpecBuilder<LineSpec>
{
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> columnSpecs = new TreeMap<Integer, CellSpec>();

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
      columnSpecs.put(i, getSpecAt(i).overrideWith(spec));
      return this;
   }

   public CellSpec getSpecAt(int i) // TODO for public interface return copy
   {
      CellSpec columnSpec = columnSpecs.get(i);
      return defaultSpec.derive().overrideWith(columnSpec);
   }

   public Set<Map.Entry<Integer, CellSpec>> getSpecifiedCells()
   {
      return columnSpecs.entrySet();
   }
}
