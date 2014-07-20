package org.quickstep;

import java.util.logging.Level;

import static org.quickstep.GridBagToolKit.logger;

class AddLineCommand implements GridBagCommand
{
   private GridBagLine line;

   AddLineCommand(GridBagLine line)
   {
      this.line = line;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.setEndOfLine(!builder.isEmpty());
      builder.moveToNextFreeCell();
      int lineNumber = builder.getCurrentLineNumber();
      builder.moveToPreviousCell();

      for (GridBagCommand command : line)
      {
         builder.moveToFreeCell();
         int currentLineNumber = builder.getCurrentLineNumber();
         if (lineNumber != currentLineNumber)
         {
            logger.log(Level.WARNING, "Components from GridBagLine #" + lineNumber + " cannot be placed at line " + currentLineNumber + ".");
            builder.moveToPreviousCell();
            break;
         }
         command.apply(builder);
      }
      builder.setEndOfLine(true);
   }
}
