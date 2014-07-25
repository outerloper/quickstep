package org.quickstep;

public interface GridBagBuilderSpec
{
   GridBagSpec getSpecAt(int x, int y);

   Integer getMaxLineLength();

   boolean isHorizontal();
}
