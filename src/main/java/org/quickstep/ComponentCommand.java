package org.quickstep;

import javax.swing.*;

public class ComponentCommand implements CellCommand
{
   private JComponent component;
   private GridBagSpec spec;

   protected ComponentCommand(JComponent component, GridBagSpec spec)
   {
      this.spec = spec.derive();
      this.component = component;
   }

   @Override
   public JComponent getComponent()
   {
      return component;
   }

   @Override
   public GridBagSpec getSpec()
   {
      return spec.derive();
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
