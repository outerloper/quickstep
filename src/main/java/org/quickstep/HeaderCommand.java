package org.quickstep;

import java.util.logging.Level;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class HeaderCommand implements GridBagCommand
{
   private final CellSpec cellSpec = spec();
   private final String text;

   public HeaderCommand(String text)
   {
      this.text = text;
   }

   public HeaderCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      try
      {
         JComponent component = builder.createDefaultHeader(text, willBePlacedInFirstRow(builder));
         CellSpec spec = spec().withAnchorX(AX.BOTH);
         GridBagCommand command;

         if (builder.isHorizontal())
         {
            command = line().add(component(component).withSpec(spec.withGridWidthRemaining().overrideWith(cellSpec)));
         }
         else
         {
            command = component(component).withSpec(spec.overrideWith(cellSpec));
         }

         command.apply(builder);
      }
      catch (GridBagException e)
      {
         logger.log(Level.WARNING, "No place for header component.", e);
      }
   }

   private boolean willBePlacedInFirstRow(GridBagBuilder builder) throws GridBagException
   {
      if (builder.isHorizontal())
      {
         return builder.isEmpty();
      }
      else
      {
         builder.moveToFreeCell();
         return builder.getCurrentRowNumber() == 0;
      }
   }
}
