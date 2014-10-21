package org.quickstep.command;

import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.ComponentFactory;
import org.quickstep.GridBagException;
import org.quickstep.spec.CellSpec;
import org.quickstep.support.DebugSupport;

import static org.quickstep.GridBagToolKit.*;

public abstract class AbstractComponentCommand<T extends AbstractComponentCommand<T>> implements GridBagCommand
{
   private final CellSpec spec = spec();

   public abstract JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory);

   public final JComponent getComponent()
   {
      return getComponent(Direction.LEFT_TO_RIGHT, getDefaultComponentFactory());
   }

   public final T withSpec(CellSpec spec)
   {
      this.spec.overrideWith(spec);
      return self();
   }

   protected CellSpec getDefaultSpec(Direction parentDirection)
   {
      return spec();
   }

   public final CellSpec getSpec(Direction parentDirection)
   {
      return getDefaultSpec(parentDirection).overrideWith(spec);
   }

   @Override
   public final void apply(GridBagBuilder builder)
   {
      Direction direction = builder.isHorizontal() ? Direction.LEFT_TO_RIGHT : Direction.TOP_TO_BOTTOM;
      JComponent component = getComponent(direction, builder.getComponentFactory());
      if (component != null)
      {
         try
         {
            builder.moveToFreeCell();
            builder.placeComponent(component, getSpec(direction));
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
