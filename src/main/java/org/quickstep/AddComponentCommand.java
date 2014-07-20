package org.quickstep;

import javax.swing.*;

class AddComponentCommand implements GridBagCommand
{
   private ComponentBuilder componentBuilder;
   private GridBagSpec spec;

   AddComponentCommand(ComponentBuilder componentBuilder, GridBagSpec spec)
   {
      this.spec = spec.overrideWith(componentBuilder.getSpec());
      this.componentBuilder = componentBuilder;
   }

   public JComponent getComponent()
   {
      return componentBuilder.build();
   }

   public GridBagSpec getSpec()
   {
      return spec;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.moveToFreeCell();
      builder.placeComponent(getComponent(), getSpec());
   }
}
