package org.quickstep;

public class LineBreakCommand implements GridBagCommand
{
   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.moveToNextLine();
   }
}
