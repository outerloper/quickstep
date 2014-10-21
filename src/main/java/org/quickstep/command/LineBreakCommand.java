package org.quickstep.command;

import java.util.logging.Level;

import org.quickstep.GridBagException;
import org.quickstep.GridBagToolKit;

public class LineBreakCommand implements GridBagCommand
{
   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         builder.moveToNextLine();
      }
      catch (GridBagException e)
      {
         GridBagToolKit.logger.log(Level.WARNING, "Could not move to next line.", e);
      }
   }
}
