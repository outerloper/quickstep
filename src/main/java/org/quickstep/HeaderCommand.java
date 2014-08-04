package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.component;
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
      CellSpec spec = spec().withAnchorX(GridBagToolKit.AX.BOTH);
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
}
