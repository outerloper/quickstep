package org.quickstep;

import javax.swing.*;

public class ComponentCommand extends CellCommand<ComponentCommand>
{
   private final JComponent component;

   ComponentCommand(JComponent component)
   {
      this.component = component;
   }

   @Override
   public JComponent getComponent(GridBagToolKit.Orientation orientation)
   {
      return component;
   }
}
