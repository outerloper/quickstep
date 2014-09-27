package org.quickstep;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import static org.quickstep.GridBagToolKit.*;

public class GridSpec
{
   private Integer lineLength;
   private Direction direction = Direction.LEFT_TO_RIGHT;
   private final CellSpec defaultSpec = spec();
   private final Map<Integer, CellSpec> columnSpecs = new TreeMap<Integer, CellSpec>();
   private final Map<Integer, CellSpec> rowSpecs = new TreeMap<Integer, CellSpec>();
   private final Table<Integer, Integer, CellSpec> cellSpecs = TreeBasedTable.create();
   private final Table<Integer, Integer, CellSpec> rowSpecsOverridingColumnSpecs = TreeBasedTable.create();

   public GridSpec()
   {
   }

   public GridSpec withDirection(Direction value)
   {
      direction = value;
      return this;
   }

   public Direction getDirection()
   {
      return direction;
   }

   public GridSpec withLineLength(Integer lineLength)
   {
      this.lineLength = lineLength;
      return this;
   }

   public Integer getLineLength()
   {
      return lineLength;
   }

   public GridSpec withDefault(CellSpec spec)
   {
      defaultSpec.overrideWith(spec);
      return this;
   }

   public GridSpec withColumn(int columnIndex, CellSpec spec)
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

   public GridSpec withColumn(int columnIndex, LineSpec lineSpec)
   {
      withColumn(columnIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         withCell(columnIndex, entry.getKey(), entry.getValue());
      }
      return this;
   }

   public GridSpec withRow(int rowIndex, CellSpec spec)
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

   public GridSpec withRow(int rowIndex, LineSpec lineSpec)
   {
      withRow(rowIndex, lineSpec.getDefaultSpec());
      for (Map.Entry<Integer, CellSpec> entry : lineSpec.getSpecifiedCells().entrySet())
      {
         withCell(entry.getKey(), rowIndex, entry.getValue());
      }
      return this;
   }

   public GridSpec withCell(int columnIndex, int rowIndex, CellSpec spec)
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
      if (that.direction != null)
      {
         direction = that.direction;
      }
      withDefault(that.defaultSpec);
      for (Map.Entry<Integer, CellSpec> entry : that.rowSpecs.entrySet())
      {
         withRow(entry.getKey(), entry.getValue());
      }
      for (Map.Entry<Integer, CellSpec> entry : that.columnSpecs.entrySet())
      {
         withColumn(entry.getKey(), entry.getValue());
      }
      for (Table.Cell<Integer, Integer, CellSpec> cell : that.cellSpecs.cellSet())
      {
         withCell(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
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
      if (direction != spec.direction) return false;
      if (!rowSpecs.equals(spec.rowSpecs)) return false;
      if (!rowSpecsOverridingColumnSpecs.equals(spec.rowSpecsOverridingColumnSpecs)) return false;
      //noinspection SimplifiableIfStatement
      return true;
   }

   @Override
   public int hashCode()
   {
      int result = lineLength != null ? lineLength.hashCode() : 0;
      result = 31 * result + direction.hashCode();
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
      String result = "GridSpec{direction=" + direction + ", lineLength=" + lineLength + "\n " +
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
