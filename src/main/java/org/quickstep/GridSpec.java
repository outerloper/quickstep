package org.quickstep;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static org.quickstep.GridBagToolKit.*;

public class GridSpec implements GridSpecBuilder<GridSpec>
{
   private Integer maxLineLength;
   private Orientation orientation = Orientation.HORIZONTAL;
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> columnSpecs = new TreeMap<Integer, CellSpec>();
   private final Map<Integer, CellSpec> rowSpecs = new TreeMap<Integer, CellSpec>();
   private final Table<Integer, Integer, CellSpec> cellSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, CellSpec> rowSpecsOverridingColumnSpecs = TreeBasedTable.create();

   public GridSpec()
   {
   }

   public final GridSpec withOrientation(Orientation value)
   {
      orientation = value;
      return this;
   }

   public Orientation getOrientation()
   {
      return orientation;
   }

   public final GridSpec withMaxLineLength(Integer value)
   {
      maxLineLength = value;
      return this;
   }

   public Integer getMaxLineLength()
   {
      return maxLineLength;
   }

   @Override
   public final GridSpec specifyDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   @Override
   public final GridSpec specifyColumn(int x, CellSpec spec)
   {
      for (Integer y : rowSpecsOverridingColumnSpecs.column(x).keySet())
      {
         rowSpecsOverridingColumnSpecs.remove(x, y);
      }
      CellSpec columnSpec = columnSpecs.get(x);
      if (columnSpec == null)
      {
         columnSpec = spec();
      }
      columnSpecs.put(x, columnSpec.overrideWith(spec));
      return this;
   }

   @Override
   public final GridSpec specifyRow(int y, CellSpec spec)
   {
      for (Integer x : columnSpecs.keySet())
      {
         rowSpecsOverridingColumnSpecs.put(x, y, spec);
      }
      CellSpec rowSpec = rowSpecs.get(y);
      if (rowSpec == null)
      {
         rowSpec = spec();
      }
      rowSpecs.put(y, rowSpec.overrideWith(spec));
      return this;
   }

   @Override
   public final GridSpec specifyCell(int x, int y, CellSpec spec)
   {
      CellSpec cellSpec = cellSpecs.get(x, y);
      if (cellSpec == null)
      {
         cellSpec = spec();
      }
      cellSpecs.put(x, y, cellSpec.overrideWith(spec));
      return this;
   }

   public CellSpec getSpecAt(int x, int y)
   {
      CellSpec[] lineSpecs = new CellSpec[]{columnSpecs.get(x), rowSpecs.get(y)};
      int i = rowSpecsOverridingColumnSpecs.get(x, y) != null ? 0 : 1;
      return spec().
         overrideWith(defaultSpec).
         overrideWith(lineSpecs[i]).
         overrideWith(lineSpecs[(i + 1) % 2]).
         overrideWith(cellSpecs.get(x, y));
   }
}
