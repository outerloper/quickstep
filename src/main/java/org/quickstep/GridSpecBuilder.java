package org.quickstep;

import static org.quickstep.GridBagToolKit.*;

public interface GridSpecBuilder<T extends GridSpecBuilder<T>>
{
   T specifyDefault(CellSpec spec);

   T specifyColumn(int columnIndex, CellSpec spec);

   T specifyColumn(int columnIndex, LineSpec spec);

   T specifyRow(int rowIndex, CellSpec spec);

   T specifyRow(int rowIndex, LineSpec spec);

   T specifyCell(int columnIndex, int rowIndex, CellSpec spec);

   T withOrientation(Orientation orientation);

   T withLineLength(Integer lineLength);
}
