package org.quickstep;

import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.support.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public abstract class CellCommand<T extends CellCommand<T>> implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public abstract JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory);

   public final JComponent getComponent()
   {
      return getComponent(Orientation.HORIZONTAL, getDefaultComponentFactory());
   }

   public final T withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return self();
   }

   protected CellSpec getSpec(Orientation parentOrientation)
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
      Orientation orientation = builder.isHorizontal() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
      JComponent component = getComponent(orientation, builder.getComponentFactory());
      if (component != null)
      {
         try
         {
            builder.moveToFreeCell();
            builder.placeComponent(component, getSpec(orientation));
         }
         catch (GridBagException e)
         {
            logger.log(Level.WARNING, "No place for " + DebugSupport.objectId(component) + ".", e);
         }
      }
   }

   @SuppressWarnings("unchecked")
   protected final T self()
   {
      return (T) this;
   }
}
