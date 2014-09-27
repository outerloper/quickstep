package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class ComponentCommand extends AbstractComponentCommand<ComponentCommand>
{
   private final JComponent component;

   protected ComponentCommand(JComponent component)
   {
      this.component = component;
   }

   @Override
   public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
   {
      return component;
   }
}
