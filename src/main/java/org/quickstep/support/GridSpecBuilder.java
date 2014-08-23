package org.quickstep.support;

import org.quickstep.*;

import static org.quickstep.GridBagToolKit.*;

public class GridSpecBuilder<T>
{
   private final T owner;
   private final GridSpec gridSpec = new GridSpec();

   public GridSpecBuilder(T owner)
   {
      this.owner = owner;
   }

   public T specifyDefault(CellSpec spec)
   {
      gridSpec.specifyDefault(spec);
      return owner;
   }

   public T specifyColumn(int columnIndex, CellSpec spec)
   {
      gridSpec.specifyColumn(columnIndex, spec);
      return owner;
   }

   public T specifyColumn(int columnIndex, LineSpec spec)
   {
      gridSpec.specifyColumn(columnIndex, spec);
      return owner;
   }

   public T specifyRow(int rowIndex, CellSpec spec)
   {
      gridSpec.specifyRow(rowIndex, spec);
      return owner;
   }

   public T specifyRow(int rowIndex, LineSpec spec)
   {
      gridSpec.specifyRow(rowIndex, spec);
      return owner;
   }

   public T specifyCell(int columnIndex, int rowIndex, CellSpec spec)
   {
      gridSpec.specifyCell(columnIndex, rowIndex, spec);
      return owner;
   }

   public T withOrientation(Orientation orientation)
   {
      gridSpec.withOrientation(orientation);
      return owner;
   }

   public T withLineLength(Integer lineLength)
   {
      gridSpec.withLineLength(lineLength);
      return owner;
   }

   public T overrideWith(GridSpec spec)
   {
      gridSpec.overrideWith(spec);
      return owner;
   }

   public GridSpec getGridSpec()
   {
      return gridSpec;
   }
}
