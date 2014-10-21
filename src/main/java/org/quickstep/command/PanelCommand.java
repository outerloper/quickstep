package org.quickstep.command;

import javax.swing.*;

import org.quickstep.ComponentFactory;

public class PanelCommand extends GridContainerCommand<JPanel, PanelCommand>
{
   public PanelCommand()
   {
   }

   @Override
   protected JPanel createDefaultContainer(ComponentFactory factory)
   {
      return factory.createPanel();
   }
}
