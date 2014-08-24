package org.quickstep;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static org.quickstep.GridBagToolKit.*;

public class GridSpec
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

   public final GridSpec specifyDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

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

   public final GridSpec specifyColumn(int columnIndex, LineSpec lineSpec)
   {
      specifyColumn(columnIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         specifyCell(columnIndex, entry.getKey(), entry.getValue());
      }
      return this;
   }

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

   public final GridSpec specifyRow(int rowIndex, LineSpec lineSpec)
   {
      specifyRow(rowIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         specifyCell(entry.getKey(), rowIndex, entry.getValue());
      }
      return this;
   }

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
      if (that == null)
      {
         return this;
      }
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
      for (Table.Cell<Integer, Integer, CellSpec> cell : that.cellSpecs.cellSet())
      {
         specifyCell(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
      }
      for (Table.Cell<Integer, Integer, CellSpec> cell : that.rowSpecsOverridingColumnSpecs.cellSet())
      {
         rowSpecsOverridingColumnSpecs.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
      }
      return this;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof GridSpec)) return false;

      GridSpec spec = (GridSpec) o;

      if (!cellSpecs.equals(spec.cellSpecs)) return false;
      if (!columnSpecs.equals(spec.columnSpecs)) return false;
      if (!defaultSpec.equals(spec.defaultSpec)) return false;
      if (lineLength != null ? !lineLength.equals(spec.lineLength) : spec.lineLength != null) return false;
      if (orientation != spec.orientation) return false;
      if (!rowSpecs.equals(spec.rowSpecs)) return false;
      if (!rowSpecsOverridingColumnSpecs.equals(spec.rowSpecsOverridingColumnSpecs)) return false;
      //noinspection SimplifiableIfStatement
      return true;
   }

   @Override
   public int hashCode()
   {
      int result = lineLength != null ? lineLength.hashCode() : 0;
      result = 31 * result + orientation.hashCode();
      result = 31 * result + defaultSpec.hashCode();
      result = 31 * result + columnSpecs.hashCode();
      result = 31 * result + rowSpecs.hashCode();
      result = 31 * result + cellSpecs.hashCode();
      result = 31 * result + rowSpecsOverridingColumnSpecs.hashCode();
      return result;
   }

   @Override
   public String toString()
   {
      String result = "GridSpec{orientation=" + orientation + ", lineLength=" + lineLength + "\n " +
         defaultSpec + "\n";
      for (Map.Entry<Integer, CellSpec> entry : columnSpecs.entrySet())
      {
         result += " col " + entry.getKey() + ": " + entry.getValue() + "\n";
      }
      for (Map.Entry<Integer, CellSpec> entry : rowSpecs.entrySet())
      {
         result += " row " + entry.getKey() + ": " + entry.getValue() + "\n";
      }
      for (Table.Cell<Integer, Integer, CellSpec> cell : cellSpecs.cellSet())
      {
         result += " cell " + cell.getRowKey() + "," + cell.getColumnKey() + ": " + cell.getValue() + "\n";
      }
      return result + "}";
   }
}
