package org.quickstep;

public interface LineSpecBuilder<T extends LineSpecBuilder<T>>
{
   T specifyDefault(CellSpec spec);

   T specifyCell(int i, CellSpec spec);
}
