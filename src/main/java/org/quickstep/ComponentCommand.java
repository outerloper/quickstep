package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.spec;

public class ComponentCommand implements CellCommand<ComponentCommand>
{
   private final JComponent component;
   private final CellSpec cellSpec = spec();

   ComponentCommand(JComponent component)
   {
      this.component = component;
   }

   @Override
   public JComponent getComponent()
   {
      return component;
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
      if (getComponent() != null)
      {
         builder.moveToFreeCell();
         builder.placeComponent(getComponent(), getSpec());
      }
   }
}
