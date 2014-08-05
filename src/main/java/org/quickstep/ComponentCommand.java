package org.quickstep;

import java.util.logging.Level;
import javax.swing.*;

import org.quickstep.util.DebugSupport;

import static org.quickstep.GridBagToolKit.logger;
import static org.quickstep.GridBagToolKit.spec;

public class ComponentCommand implements CellCommand<ComponentCommand>
{
   private final JComponent wrappedComponent;
   private final CellSpec cellSpec = spec();

   ComponentCommand(JComponent wrappedComponent)
   {
      this.wrappedComponent = wrappedComponent;
   }

   @Override
   public JComponent getComponent()
   {
      return wrappedComponent;
   }

   public ComponentCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
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
}
