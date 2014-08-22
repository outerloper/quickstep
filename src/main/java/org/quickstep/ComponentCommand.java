package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class ComponentCommand extends CellCommand<ComponentCommand>
{
   private final JComponent component;

   ComponentCommand(JComponent component)
   {
      this.component = component;
   }

   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      return component;
   }
}
