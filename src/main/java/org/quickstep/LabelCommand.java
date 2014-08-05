package org.quickstep;

import java.util.logging.Level;

import javax.swing.*;

import org.quickstep.util.DebugSupport;

import static org.quickstep.GridBagToolKit.logger;

public class LabelCommand implements GridBagCommand
{
   private CellSpec spec;
   private String text;

   protected LabelCommand(String text, CellSpec spec)
   {
      this.text = text;
      this.spec = spec.derive();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JComponent component = builder.createDefaultLabel(text);
      if (builder.moveToFreeCell())
      {
         builder.placeComponent(component, spec);
      }
      else
      {
         logger.log(Level.WARNING, "No place for " + DebugSupport.objectId(component));
      }
   }
}
