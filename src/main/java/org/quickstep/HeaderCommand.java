package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.line;
import static org.quickstep.GridBagToolKit.spec;

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
      JComponent component = header == null ? builder.createDefaultHeader(text) : header;
      GridBagSpec spec = spec().withFillX();
      GridBagCommand command;

      if (builder.isHorizontal())
      {
         spec.withGridWidthRemaining();
         command = line().add(component, spec);
      }
      else
      {
         command = new ComponentCommand(component, spec);
      }

      command.apply(builder);
   }
}
