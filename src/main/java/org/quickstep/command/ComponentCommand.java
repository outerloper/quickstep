package org.quickstep.command;

import javax.swing.*;

import org.quickstep.ComponentFactory;

import static org.quickstep.GridBagToolKit.*;

public class ComponentCommand extends AbstractComponentCommand<ComponentCommand>
{
   private final JComponent component;

   public ComponentCommand(JComponent component)
   {
      this.component = component;
   }

   @Override
   public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
   {
      return component;
   }
}
