package org.quickstep;

import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.util.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public abstract class CellCommand<T extends CellCommand<T>> implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public abstract JComponent getComponent(Orientation orientation);

   public final JComponent getComponent()
   {
      return getComponent(Orientation.HORIZONTAL);
   }

   public final T withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return self();
   }

   protected CellSpec getSpec(Orientation orientation)
   {
      return cellSpec.derive();
   }

   public final CellSpec getSpec()
   {
      return getSpec(Orientation.HORIZONTAL);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JComponent component = getComponent();
      if (component != null)
      {
         if (builder.moveToFreeCell())
         {
            builder.placeComponent(component, getSpec(builder.getGridSpec().getOrientation()));
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
