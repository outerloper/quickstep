package org.quickstep;

public interface GridBagBuilderSpec
{
   CellSpec getSpecAt(int x, int y);

   Integer getMaxLineLength();

   boolean isHorizontal();
}
