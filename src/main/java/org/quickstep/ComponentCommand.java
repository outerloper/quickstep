package org.quickstep;

import javax.swing.*;

public class ComponentCommand implements CellCommand
{
   private JComponent component;
   private CellSpec spec;

   protected ComponentCommand(JComponent component, CellSpec spec)
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
   public CellSpec getSpec()
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
