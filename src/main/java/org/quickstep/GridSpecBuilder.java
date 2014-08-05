package org.quickstep;

import static org.quickstep.GridBagToolKit.*;

public interface GridSpecBuilder<T extends GridSpecBuilder<T>>
{
   T specifyDefault(CellSpec spec);

   T specifyColumn(int x, CellSpec spec);

   T specifyRow(int y, CellSpec spec);

   T specifyCell(int x, int y, CellSpec spec);

   T withOrientation(Orientation orientation);

   T withLineLength(Integer maxLineLength);
}
