package org.quickstep;

public class NextLineCommand implements GridBagCommand
{
   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.moveToNextLine();
   }
}
