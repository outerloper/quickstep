package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class HeaderCommand implements GridBagCommand
{
   private final JComponent header;
   private final String text;

   public HeaderCommand(String text)
   {
      this(null, text);
   }

   public HeaderCommand(JComponent customHeader, String text)
   {
      header = customHeader;
      this.text = text;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JComponent component = header == null ? builder.createDefaultHeader(text, willBePlacedInFirstRow(builder)) : header; // TODO test first row behavior
      CellSpec spec = spec().withAnchorX(AX.BOTH);
      GridBagCommand command;

      if (builder.isHorizontal())
      {
         spec.withGridWidthRemaining();
         command = line().add(component, spec);
      }
      else
      {
         command = component(component).withSpec(spec);
      }

      command.apply(builder);
   }

   private boolean willBePlacedInFirstRow(GridBagBuilder builder)
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
