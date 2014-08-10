package org.quickstep;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static org.quickstep.GridBagToolKit.*;

public class GridSpec implements GridSpecBuilder<GridSpec>
{
   private Integer lineLength;
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

   public final GridSpec withLineLength(Integer lineLength)
   {
      this.lineLength = lineLength;
      return this;
   }

   public Integer getLineLength()
   {
      return lineLength;
   }

   @Override
   public final GridSpec specifyDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   @Override
   public final GridSpec specifyColumn(int columnIndex, CellSpec spec)
   {
      for (Integer y : rowSpecsOverridingColumnSpecs.column(columnIndex).keySet())
      {
         rowSpecsOverridingColumnSpecs.remove(columnIndex, y);
      }
      CellSpec columnSpec = columnSpecs.get(columnIndex);
      if (columnSpec == null)
      {
         columnSpec = spec();
      }
      columnSpecs.put(columnIndex, columnSpec.overrideWith(spec));
      return this;
   }

   @Override
   public final GridSpec specifyColumn(int columnIndex, LineSpec lineSpec)
   {
      specifyColumn(columnIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         specifyCell(columnIndex, entry.getKey(), entry.getValue());
      }
      return this;
   }

   @Override
   public final GridSpec specifyRow(int rowIndex, CellSpec spec)
   {
      for (Integer x : columnSpecs.keySet())
      {
         rowSpecsOverridingColumnSpecs.put(x, rowIndex, spec);
      }
      CellSpec rowSpec = rowSpecs.get(rowIndex);
      if (rowSpec == null)
      {
         rowSpec = spec();
      }
      rowSpecs.put(rowIndex, rowSpec.overrideWith(spec));
      return this;
   }

   @Override
   public final GridSpec specifyRow(int rowIndex, LineSpec lineSpec)
   {
      specifyRow(rowIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         specifyCell(entry.getKey(), rowIndex, entry.getValue());
      }
      return this;
   }

   @Override
   public final GridSpec specifyCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      CellSpec cellSpec = cellSpecs.get(columnIndex, rowIndex);
      if (cellSpec == null)
      {
         cellSpec = spec();
      }
      cellSpecs.put(columnIndex, rowIndex, cellSpec.overrideWith(spec));
      return this;
   }

   public CellSpec getSpecAt(int columnIndex, int rowIndex)
   {
      CellSpec[] lineSpecs = new CellSpec[]{columnSpecs.get(columnIndex), rowSpecs.get(rowIndex)};
      int i = rowSpecsOverridingColumnSpecs.get(columnIndex, rowIndex) != null ? 0 : 1;
      return spec().
         overrideWith(defaultSpec).
         overrideWith(lineSpecs[i]).
         overrideWith(lineSpecs[(i + 1) % 2]).
         overrideWith(cellSpecs.get(columnIndex, rowIndex));
   }

   public GridSpec overrideWith(GridSpec that)
   {
      if (that.lineLength != null)
      {
         lineLength = that.lineLength;
      }
      if (that.orientation != null)
      {
         orientation = that.orientation;
      }
      specifyDefault(that.defaultSpec);
      for (Map.Entry<Integer, CellSpec> entry : that.rowSpecs.entrySet())
      {
         specifyRow(entry.getKey(), entry.getValue());
      }
      for (Map.Entry<Integer, CellSpec> entry : that.columnSpecs.entrySet())
      {
         specifyColumn(entry.getKey(), entry.getValue());
      }
      for (Table.Cell<Integer, Integer, CellSpec> cell : cellSpecs.cellSet())
      {
         specifyCell(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
      }
      return this;
   }
}
