package org.quickstep;

import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.util.DebugSupport;

import static org.quickstep.GridBagToolKit.logger;
import static org.quickstep.GridBagToolKit.spec;

public abstract class CellCommand<T extends CellCommand<T>> implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public abstract JComponent getComponent();

   public T withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return self();
   }

   public CellSpec getSpec()
   {
      return cellSpec.derive();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JComponent component = getComponent();
      if (component != null)
      {
         if (builder.moveToFreeCell())
         {
            builder.placeComponent(component, getSpec());
         }
         else
         {
            logger.log(Level.WARNING, "No place for " + DebugSupport.objectId(component));
         }
      }
   }

   @SuppressWarnings("unchecked")
   protected T self()
   {
      return (T) this;
   }
}
