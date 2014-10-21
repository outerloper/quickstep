package org.quickstep.support;

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
